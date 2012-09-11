package ch.rasc.reflection;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class Main {
	public static void main(String... args) throws Exception {
//		Field[] fields = User.class.getDeclaredFields();
//		for (Field field : fields) {
//
//			System.out.println(field.getName());
//			
//			 if (!Modifier.isStatic(field.getModifiers())) {
//			
//			 System.out.println(field);
//			 Model m = field.getAnnotation(Model.class);
//			 if (m != null) {
//			 System.out.println(m.value());
//			 } else {
//			 System.out.println("no annotation");
//			 }
//			
//			 if (!Modifier.isPrivate(field.getModifiers())) {
//			 // field.setAccessible(true);
//			 System.out.println("GET: " + field.getName() + "=" +
//			 field.get(new User()));
//			 } else {
//			 PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
//			 User.class);
//			 Method readMethod = pd.getReadMethod();
//			 if (readMethod != null) {
//			 System.out.println("GET via READMETHOD: " + field.getName() + "="
//			 + readMethod.invoke(new User()));
//			 }
//			
//			 }
//			
//			 } else {
//			 System.out.println("STATIC");
//			 System.out.println(field);
//			 }
//		}

		User u = new User();
		BeanInfo bi = Introspector.getBeanInfo(User.class);
		List<Field> allFields = getAllFields(User.class);
		
		for (Field f : allFields) {
			System.out.println(f.getName());
		}
		
		for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
			// Ignore class property
			if (pd.getName().equals("class")) {
				continue;
			}

			Method readMethod = pd.getReadMethod();

			Class<?> type = pd.getPropertyType();
			
			Field field = null;
			for (Field f : allFields) {
				if (f.getName().equals(pd.getName())) {
					field = f;
					break;
				}
			}

			ModelType mt = null;
			for (ModelType t : ModelType.values()) {
				if (t.supports(type)) {
					mt = t;
					break;
				}
			}
			
			System.out.println(mt);
			
			if (type.equals(Integer.class)) {

			} else if (type.equals(String.class)) {

			}

			if (readMethod != null) {
				System.out.println(pd + ":" + readMethod.invoke(u));
			}
		}

		ModelBean mo = new ModelBean("User");
		mo.addField(new ch.rasc.reflection.ModelFieldBean("name", "string"));
		mo.addField(new ch.rasc.reflection.ModelFieldBean("id", "int"));

		Generator g = new Generator();
		String js = g.generateJs(mo);
		System.out.println(js);

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(new User()));
	}

	public static List<Field> getAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}
}
