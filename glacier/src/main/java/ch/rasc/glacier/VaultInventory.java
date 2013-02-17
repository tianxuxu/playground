package ch.rasc.glacier;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.InitiateJobRequest;
import com.amazonaws.services.glacier.model.InitiateJobResult;
import com.amazonaws.services.glacier.model.JobParameters;

public class VaultInventory {

	public static void main(String[] args) {

		if (args.length == 2) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonGlacierClient client = new AmazonGlacierClient(credentials);
			client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

			InitiateJobRequest initJobRequest = new InitiateJobRequest().withVaultName("testvault").withJobParameters(
					new JobParameters().withType("inventory-retrieval"));

			InitiateJobResult initJobResult = client.initiateJob(initJobRequest);
			String jobId = initJobResult.getJobId();
			System.out.println(jobId);
		}

	}

}
