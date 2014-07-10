package ch.rasc.forcastio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Main {

	// public static HttpClient getTestHttpClient() {
	// try {
	// SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {
	// @Override
	// public boolean isTrusted(X509Certificate[] chain, String authType) throws
	// CertificateException {
	// return true;
	// }
	// }, new AllowAllHostnameVerifier());
	//
	// SchemeRegistry registry = new SchemeRegistry();
	// registry.register(new Scheme("https", 443, sf));
	// registry.register(new Scheme("http", 80,
	// PlainSocketFactory.getSocketFactory()));
	// ClientConnectionManager ccm = new
	// PoolingClientConnectionManager(registry);
	// return new DecompressingHttpClient(new DefaultHttpClient(ccm));
	// } catch (Exception e) {
	// e.printStackTrace();
	// return new DefaultHttpClient();
	// }
	// }

	public static void main(String[] args) throws ClientProtocolException, IOException {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

			// HttpClient httpClient = getTestHttpClient();
			// HttpHost proxy = new HttpHost("127.0.0.1", 8888);
			// httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// proxy);

			String apiKey = args[0];
			String latitude = "46.947922";
			String longitude = "7.444608";

			String urlTemplate = "https://api.forecast.io/forecast/%s/%s,%s";
			String url = String.format(urlTemplate, apiKey, latitude, longitude);
			System.out.println(url);

			HttpGet httpget = new HttpGet(url);
			try (CloseableHttpResponse response = httpClient.execute(httpget)) {
				System.out.println(response.getFirstHeader("Content-Encoding"));
				HeaderIterator it = response.headerIterator();
				while (it.hasNext()) {
					Object header = it.next();
					System.out.println(header);
				}

				HttpEntity entity = response.getEntity();
				String jsonData = EntityUtils.toString(entity, StandardCharsets.UTF_8);

				System.out.println(jsonData);
			}

		}

	}

}
