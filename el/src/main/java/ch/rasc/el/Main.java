package ch.rasc.el;

import javax.el.ELProcessor;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Main {

	public static void main(String[] args) {

		User u = new User();
		u.setAge(23);
		u.setUserName("admin");
		u.setPassword("mypassword");
		ELProcessor el = new ELProcessor();
		el.defineBean("user", u);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			el.eval("user.userName");
		}
		System.out.println((System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			u.getUserName();
		}
		System.out.println((System.currentTimeMillis() - start) + " ms");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("userName");
		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			exp.getValue(u);
		}
		System.out.println((System.currentTimeMillis() - start) + " ms");

		// Long x = (Long)el.eval("a = [1, 2, 3]; a[1]");
		// System.out.println(x);

	}

}
