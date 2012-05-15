package ch.rasc.reflection;

import java.lang.reflect.Field;

public class Main {
	public static void main(String[] args) throws Exception {
		Field[] fields = User.class.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field);
			Model m = field.getAnnotation(Model.class);
			if (m != null) {
				System.out.println(m.value());
			}
			else {
				System.out.println("no annotation");
			}
		}

		ModelObject mo = new ModelObject("User");
		mo.addField(new ch.rasc.reflection.Field("name", "string"));
		mo.addField(new ch.rasc.reflection.Field("id", "int"));

		Generator g = new Generator();
		String js = g.generateJs(mo);
		System.out.println(js);
	}
}
