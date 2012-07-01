package ch.rasc.reflection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.codehaus.jackson.map.ObjectMapper;

public class Main {
	public static void main(String... args) throws Exception {
		Field[] fields = User.class.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {

				System.out.println(field);
				Model m = field.getAnnotation(Model.class);
				if (m != null) {
					System.out.println(m.value());
				} else {
					System.out.println("no annotation");
				}

				if (!Modifier.isPrivate(field.getModifiers())) {
					// field.setAccessible(true);
					System.out.println("GET: " + field.getName() + "=" + field.get(new User()));
				} else {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), User.class);
					Method readMethod = pd.getReadMethod();
					if (readMethod != null) {
						System.out.println("GET via READMETHOD: " + field.getName() + "="
								+ readMethod.invoke(new User()));
					}

				}

			} else {
				System.out.println("STATIC");
				System.out.println(field);
			}
		}

		User u = new User();
		BeanInfo bi = Introspector.getBeanInfo(User.class);
		for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
			Method readMethod = pd.getReadMethod();
			if (readMethod != null) {
				System.out.println(pd + ":" + readMethod.invoke(u));
			} else {
				System.out.println("NO READ: " + pd);
			}
		}

		ModelObject mo = new ModelObject("User");
		mo.addField(new ch.rasc.reflection.Field("name", "string"));
		mo.addField(new ch.rasc.reflection.Field("id", "int"));

		Generator g = new Generator();
		String js = g.generateJs(mo);
		System.out.println(js);

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(new User()));
	}
}
