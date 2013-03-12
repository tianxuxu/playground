package ch.rasc.glacier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DescribeJobRequest;
import com.amazonaws.services.glacier.model.DescribeJobResult;
import com.amazonaws.services.glacier.model.GetJobOutputRequest;
import com.amazonaws.services.glacier.model.GetJobOutputResult;

public class VaultInventory {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			// InitiateJobRequest initJobRequest = new
			// InitiateJobRequest().withVaultName("testvault").withJobParameters(
			// new JobParameters().withType("inventory-retrieval"));
			//
			// InitiateJobResult initJobResult =
			// client.initiateJob(initJobRequest);
			// String jobId = initJobResult.getJobId();
			// System.out.println(jobId);

			String jobId = "xwdLh_ViBRlcPeazzSlY_aCTmKYuGGlamz8zNLNjoHBy0tjZcAIsupm_t1WHJymX-2_0Ueh74DM6s5Gx4lDSTY2CPoHd";
			DescribeJobResult result = client.describeJob(new DescribeJobRequest("testvault", jobId));

			if (result.isCompleted()) {
				GetJobOutputRequest jobOutputRequest = new GetJobOutputRequest().withVaultName("testvault").withJobId(
						jobId);
				GetJobOutputResult jobOutputResult = client.getJobOutput(jobOutputRequest);

				try (BufferedReader in = new BufferedReader(new InputStreamReader(jobOutputResult.getBody()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						System.out.println(inputLine);
					}
				} catch (IOException e) {
					throw new AmazonClientException("Unable to save archive", e);
				}

			}

			System.out.println(result);
		}

	}

}
