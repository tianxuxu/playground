package ch.rasc.reflection;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Generator {

	public String generateJs(ModelBean model) throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		Map<String, Object> configObject = new LinkedHashMap<>();

		configObject.put("extend", "Ext.data.Model");
		configObject.put("fields", model.getFields());
		
		if (StringUtils.hasText(model.getIdProperty()) && !model.getIdProperty().equals("id")) {
			configObject.put("idProperty", model.getIdProperty());			
		}

		Map<String, Object> proxyObject = new LinkedHashMap<>();
		proxyObject.put("type", "direct");

		Map<String, Object> apiObject = new LinkedHashMap<>();

		if (StringUtils.hasText(model.getReadMethod()) && !StringUtils.hasText(model.getCreateMethod())
				&& !StringUtils.hasText(model.getUpdateMethod()) && !StringUtils.hasText(model.getDestroyMethod())) {
			proxyObject.put("directFn", model.getReadMethod());
		} else {
			if (StringUtils.hasText(model.getReadMethod())) {
				apiObject.put("read", model.getReadMethod());
			}

			if (StringUtils.hasText(model.getCreateMethod())) {
				apiObject.put("create", model.getCreateMethod());
			}

			if (StringUtils.hasText(model.getUpdateMethod())) {
				apiObject.put("update", model.getUpdateMethod());
			}

			if (StringUtils.hasText(model.getDestroyMethod())) {
				apiObject.put("destroy", model.getDestroyMethod());
			}

			if (!apiObject.isEmpty()) {
				proxyObject.put("api", apiObject);
			}
		}

		if (model.isPageing()) {
			Map<String, Object> readerObject = new LinkedHashMap<>();
			readerObject.put("root", "records");
			proxyObject.put("reader", readerObject);
		}

		if (!apiObject.isEmpty() || proxyObject.containsKey("directFn")) {
			configObject.put("proxy", proxyObject);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Ext.define('").append(model.getName()).append("',\n");
		String configObjectString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configObject);
		configObjectString = configObjectString.replace("\"", "'");
		configObjectString = configObjectString.replaceAll("directFn : '([^']+)'", "directFn : $1");
		configObjectString = configObjectString.replaceAll("read : '([^']+)'", "read : $1");
		configObjectString = configObjectString.replaceAll("create : '([^']+)'", "create : $1");
		configObjectString = configObjectString.replaceAll("update : '([^']+)'", "update : $1");
		configObjectString = configObjectString.replaceAll("destroy : '([^']+)'", "destroy : $1");
		sb.append(configObjectString);
		sb.append(");");
		return sb.toString();

	}

}
