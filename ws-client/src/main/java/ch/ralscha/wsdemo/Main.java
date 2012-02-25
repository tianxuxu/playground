package ch.ralscha.wsdemo;

import ch.ralscha.wsdemo.calculator.Calculator;
import ch.ralscha.wsdemo.calculator.CalculatorService;
import ch.ralscha.wsdemo.helloworld.HelloWorldImpl;
import ch.ralscha.wsdemo.helloworld.HelloWorldImplService;

public class Main {

	public static void main(String[] args) {

		//<JDK_HOME>\bin\wsimport.exe -s main\java -Xnocompile -p ch.ralscha.wsdemo.calculator http://localhost:8080/cxf/Calculator?wsdl    
		HelloWorldImplService service = new HelloWorldImplService();
		HelloWorldImpl helloWorld = service.getHelloWorldImplPort();
		String msgFromServer = helloWorld.sayHi("John");
		System.out.println(msgFromServer);

		CalculatorService calcService = new CalculatorService();
		Calculator calc = calcService.getCalculatorPort();
		System.out.println(calc.add(10, 20)); //returns 30
		System.out.println(calc.divide(20, 10)); //return 2
		System.out.println(calc.multiply(2, 40)); //return 80
		System.out.println(calc.subtract(40, 10)); //return 30
	}

}
