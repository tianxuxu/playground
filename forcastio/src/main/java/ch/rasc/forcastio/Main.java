package ch.rasc.forcastio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class Main {

	public static HttpClient getTestHttpClient() {
		try {
			SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}, new AllowAllHostnameVerifier());

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, sf));
			registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
			ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);
			return new DecompressingHttpClient(new DefaultHttpClient(ccm));
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultHttpClient();
		}
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient backend = new DefaultHttpClient();
		DecompressingHttpClient httpClient = new DecompressingHttpClient(backend);

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
		HttpResponse response = httpClient.execute(httpget);
		System.out.println(response.getFirstHeader("Content-Encoding"));
		HeaderIterator it = response.headerIterator();
		while (it.hasNext()) {
			Object header = it.next();
			System.out.println(header);
		}

		HttpEntity entity = response.getEntity();
		String jsonData = EntityUtils.toString(entity, StandardCharsets.UTF_8);

		System.out.println(jsonData);

		httpClient.getConnectionManager().shutdown();

	}

}
