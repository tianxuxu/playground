package ch.rasc.reflection;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Generator {

	public String generateJs(ModelObject model) throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		Map<String, Object> configObject = new LinkedHashMap<>();

		configObject.put("extend", "Ext.data.Model");
		configObject.put("field", model.getFields());

		StringBuilder sb = new StringBuilder();

		sb.append("Ext.define('").append(model.getName()).append("',\n");

		sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configObject).replace("\"", "'"));

		sb.append(");");
		//		
		//		
		//		
		//		sb.append("\n");
		//		sb.append("\textend: 'Ext.data.Model',\n");
		//		sb.append("\tfields: [\n");
		//		
		//		int fieldsSize = model.getFields().size();
		//		for (int i = 0; i < fieldsSize; i++) {
		//			Field field = model.getFields().get(i);
		//			sb.append("\t\t{name: '").append(field.getName()).append("',  type: '").append(field.getType()).append("'}");
		//			
		//			if (i+1 < fieldsSize) {
		//				sb.append(",");
		//			}
		//			sb.append("\n");
		//			
		//		}
		//		
		//		sb.append("\t]\n");
		//		sb.append("});\n");

		return sb.toString();

	}

}
