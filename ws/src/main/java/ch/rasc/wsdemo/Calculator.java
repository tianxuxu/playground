package ch.rasc.wsdemo;

import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@Named
public class Calculator {

	public int add(final int a, final int b) {
		return a + b;
	}

	public int subtract(final int a, final int b) {
		return a - b;
	}

	public int multiply(final int a, final int b) {
		return a * b;
	}

	public int divide(final int a, final int b) {
		return a / b;
	}

	@WebMethod(exclude = true)
	public int modulo(final int a, final int b) {
		return a % b;
	}
}
