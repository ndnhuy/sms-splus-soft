package vn.com.splussoftware.sms.utils.service.jsonhandler;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DataAdapter implements JsonDeserializer<ElementData> {
	@Override
	public ElementData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("dataType").getAsString();
		switch (type) {
		case "individual":
			type = "Individual";
			break;
		case "table":
			type = "Table";
			break;
		case "matrix":
			type = "Matrix";
			break;
		}
		try {
			return context.deserialize(json, Class.forName("vn.com.splussoftware.sms.utils.service.jsonhandler." + type));
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException("Unknown element type:" + type, cnfe);
		}

	}
}
