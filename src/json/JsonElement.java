package json;

import java.lang.StringBuilder;

import json.*;

public class JsonElement {
	private String name;
	private String value;

	public JsonElement() { }

	public JsonElement(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getJsonString() {
		return getJsonStringBuilder().toString();
	}

	protected StringBuilder getJsonStringBuilder() {
		StringBuilder ret = new StringBuilder();
		ret.append('\"');
		ret.append(name);
		ret.append("\": \"");
		ret.append(value);
		ret.append('\"');
		return ret;
	}
}
