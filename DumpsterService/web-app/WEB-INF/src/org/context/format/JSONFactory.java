package org.context.format;

import java.util.*;
import java.io.*;
import org.json.*;

public class JSONFactory {

	public static JSONArray createArray() {
		return new JSONArray();
	}

	public static JSONObject createObject(String jsondata) {
		return new JSONObject(jsondata);
	}

	public static JSONObject createObject() {
		return new JSONObject();
	}

	public static JSONArray createArray(String jsondata) {
		return new JSONArray(jsondata);
	}
}
