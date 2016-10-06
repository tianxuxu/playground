package ch.rasc.httpcopy.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;

import ch.rasc.httpcopy.server.ChunkInfoOuterClass.ChunkInfo;
import ch.rasc.httpcopy.server.ChunkOuterClass.Chunk;
import ch.rasc.httpcopy.server.FilesInfoOuterClass.FileInfo;
import ch.rasc.httpcopy.server.FilesInfoOuterClass.FilesInfo;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Uploader {

	private final static int CHUNK_SIZE = 1 * 1024 * 1024;

	private final OkHttpClient httpClient;

	private final Config config;

	private final WorkQueue workQueue;

	public Uploader(Config config, WorkQueue workQueue) {
		this.httpClient = new OkHttpClient();
		this.config = config;
		this.workQueue = workQueue;
	}

	public void handlePaths(Set<WorkJob> paths) {
		try {
			FilesInfo filesInfoRequest = buildFilesInfo(paths);
			FilesInfo filesInfoResponse = sendFilesInfo(filesInfoRequest);
			if (filesInfoResponse != null) {
				Map<String, WorkJob> jobs = paths.stream()
						.collect(Collectors.toMap(WorkJob::getId, Function.identity()));

				for (FileInfo fileInfo : filesInfoResponse.getFileList()) {
					WorkJob job = jobs.get(fileInfo.getId());
					if (fileInfo.getStatus() == FileInfo.Status.FILE_DOES_NOT_EXISTS) {
						uploadFile(job);
					}
					else if (fileInfo.getStatus() == FileInfo.Status.FILENAME_EXISTS) {
						if (this.config.isOverwrite()) {
							uploadFile(job);
						}
						else {
							job.setAlternativeFilename(generateUniqueFilename(
									job.getFilenameOrAlternative()));
							this.workQueue.add(job);
						}
					}
				}
			}
			else {
				// request unsuccessful, re-add all paths to the queue
				this.workQueue.add(paths);
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(Application.class).error("handleMessage", e);
			this.workQueue.add(paths);
		}
	}

	private void uploadFile(WorkJob job) throws IOException {
		ChunkInfo chunkInfo = ChunkInfo.newBuilder()
				.setClientId(this.config.getClientId())
				.setFilename(job.getFilenameOrAlternative()).setSize(CHUNK_SIZE)
				.setTotalSize(Files.size(job.getFile())).build();

		ChunkInfo chunkInfoResponse = sendChunkInfo(chunkInfo);
		if (chunkInfoResponse != null) {
			uploadChunks(job, chunkInfoResponse.getExistingChunksList());
		}
		else {
			// request unsuccessful, re-add path to the queue
			this.workQueue.add(job);
		}
	}

	private void uploadChunks(WorkJob job, List<Integer> existingChunks)
			throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(CHUNK_SIZE);

		try (SeekableByteChannel channel = Files.newByteChannel(job.getFile(),
				StandardOpenOption.READ)) {

			int chunkNo = 1;
			long totalSize = Files.size(job.getFile());
			String filename = job.getFilenameOrAlternative();

			while (channel.read(buffer) > 0) {
				if (!existingChunks.contains(chunkNo)) {
					buffer.flip();
					byte[] payload = new byte[buffer.remaining()];
					buffer.get(payload);

					Chunk chunk = Chunk.newBuilder()
							.setClientId(this.config.getClientId()).setFilename(filename)
							.setNo(chunkNo).setSize(CHUNK_SIZE).setTotalSize(totalSize)
							.setPayload(ByteString.copyFrom(payload)).build();

					if (!uploadChunk(chunk)) {
						// unsuccesful. let's stop here and re-add the path to the queue
						this.workQueue.add(job);
						break;
					}
				}

				buffer.clear();
				chunkNo++;
			}
		}
	}

	private boolean uploadChunk(Chunk chunk) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl.parse(this.config.getServerEndpoint() + "/chunk");

		RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"),
				chunk.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute()) {
			return response.isSuccessful();
		}
	}

	private ChunkInfo sendChunkInfo(ChunkInfo chunkInfo) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl
				.parse(this.config.getServerEndpoint() + "/chunkinfo");

		RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"),
				chunkInfo.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return ChunkInfo.parseFrom(response.body().bytes());
			}

			LoggerFactory.getLogger(Application.class).info("chunkinfo unsuccessful: {}",
					response.body().string());
		}
		return null;
	}

	private FilesInfo sendFilesInfo(FilesInfo filesInfo) throws IOException {
		HttpUrl filesinfoUrl = HttpUrl
				.parse(this.config.getServerEndpoint() + "/filesinfo");

		RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"),
				filesInfo.toByteArray());
		Request request = new Request.Builder().url(filesinfoUrl).post(body).build();
		try (Response response = this.httpClient.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return FilesInfo.parseFrom(response.body().bytes());
			}

			LoggerFactory.getLogger(Application.class).info("filesinfo unsuccessful: {}",
					response.body().string());
		}
		return null;
	}

	private static FilesInfo buildFilesInfo(Set<WorkJob> paths) throws IOException {
		FilesInfo.Builder builder = FilesInfo.newBuilder();

		for (WorkJob job : paths) {
			Path path = job.getFile();
			FileInfo fileInfo = FileInfo.newBuilder().setId(job.getId())
					.setHash(calcMd5(path)).setName(job.getFilenameOrAlternative())
					.setSize(Files.size(path)).build();

			builder.addFile(fileInfo);
		}
		return builder.build();
	}

	private static String calcMd5(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
			byte[] digest = digest("MD5", is);
			return Base64.getEncoder().encodeToString(digest);
		}
	}

	private static byte[] digest(String algorithm, InputStream inputStream)
			throws IOException {
		MessageDigest messageDigest = getDigest(algorithm);

		final byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			messageDigest.update(buffer, 0, bytesRead);
		}
		return messageDigest.digest();
	}

	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		}
		catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException(
					"Could not find MessageDigest with algorithm \"" + algorithm + "\"",
					ex);
		}
	}

	private static String generateUniqueFilename(String filename) {
		int lastdot = filename.lastIndexOf(".");
		int lastus = filename.lastIndexOf("_");

		String base;
		String suffix;
		if (lastdot != -1) {
			base = filename.substring(0, lastdot);
			suffix = filename.substring(lastdot);
		}
		else {
			base = filename;
			suffix = "";
		}

		if (lastus != -1) {
			String noPart = base.substring(lastus + 1);
			if (isNumeric(noPart)) {
				int next = Integer.parseInt(noPart) + 1;
				base = base.substring(0, lastus + 1) + next;
			}
			else {
				base = base + "_1";
			}
		}
		else {
			base = base + "_1";
		}

		return base + suffix;
	}

	private static boolean isNumeric(String str) {
		if (str.isEmpty()) {
			return false;
		}

		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
