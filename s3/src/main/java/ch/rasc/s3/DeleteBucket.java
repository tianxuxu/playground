package ch.rasc.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class DeleteBucket {

	public static void main(String[] args) {

		if (args.length == 3) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			client.deleteBucket(args[2]);
			System.out.println("Bucket deleted: " + args[2]);
		} else {
			System.out.println("java -jar s3backup.jar " + DeleteBucket.class.getName()
					+ " accessKey secretKey bucketName");
		}
	}

}
