package ch.rasc.dataformats;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

@RestController
public class AddressController {

	private final List<Address> testData;

	@Autowired
	public AddressController(@Value("#{testData}") List<Address> testData) {
		this.testData = Collections.unmodifiableList(testData);
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Address> getAddressesJson() {
		return testData;
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET,
			produces = "application/cbor")
	public void getAddressesCbor(HttpServletResponse response) throws IOException {
		// TODO create a HttpMessageConverter
		response.setContentType("application/cbor");

		CBORFactory f = new CBORFactory();
		ObjectMapper mapper = new ObjectMapper(f);
		byte[] cborData = mapper.writeValueAsBytes(testData);

		response.getOutputStream().write(cborData);
		response.getOutputStream().flush();
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_XML_VALUE)
	public Addresses getAddressesXml() {
		return new Addresses(testData);
	}

}
