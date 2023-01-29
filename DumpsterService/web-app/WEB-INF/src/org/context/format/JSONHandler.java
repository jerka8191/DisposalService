package org.context.format;

import org.context.format.JSONFactory;
import org.context.app.object.*;

import java.util.*;
import java.io.*;
import org.json.*;

public class JSONHandler {

	public static String jsonformat(List<? extends Object> d) {
		JSONArray json = JSONFactory.createArray();
		for (Object obj : d) {
			if (obj instanceof Dumpster) {
				json.put(((Dumpster) obj).asJSON());
			} else if (obj instanceof User) {
				json.put(((User) obj).asJSON());
			}
		}
		return json.toString();
	}

	public static String keyValuePairToJson(String key, String value) {
		return JSONFactory.createObject()
			.put("key", key)
			.put("value", value)
			.toString();
	}

	public static String[] extractAuthCredentials(String data) {
		final int START_INDEX = 0;
		JSONArray list = JSONFactory.createArray(data);
		JSONObject item = list.getJSONObject(START_INDEX);
		String[] val = new String[] { item.getString("user"), item.getString("password") };
		return val;
	}

	public static Map<String, String> extractSQL(String data) {
		JSONArray arr = JSONFactory.createArray(data);
		Map<String, String> tmp = JSONHandler.getDataMap();
		int count = 0;
		do {
			JSONObject jo = arr.getJSONObject(count++);
			tmp.put(jo.getString("path"), jo.getString("code"));
		} while (arr.length() > count);
		return Collections.unmodifiableMap(tmp);
	}

	public static Map<String, String> getDataMap() {
		return new LinkedHashMap<String, String>();
	}

}
