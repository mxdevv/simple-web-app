package json;

import java.util.ArrayList;
import java.lang.StringBuilder;

import json.*;

public class JsonGroup {
	private String name = null;
	private ArrayList<JsonElement> elements = null;
	private ArrayList<JsonGroup> groups = null;

	public JsonGroup() {
		elements = new ArrayList<JsonElement>();
		groups = new ArrayList<JsonGroup>();
	}

	public JsonGroup(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addElement(JsonElement element) {
		elements.add(element);
	}

	public void addGroup(JsonGroup group) {
		groups.add(group);
	}

	public ArrayList<JsonElement> getElements() {
		return elements;
	}

	public void setElements(ArrayList<JsonElement> elements) {
		this.elements = elements;
	}

	public ArrayList<JsonGroup> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<JsonGroup> groups) {
		this.groups = groups;
	}

	public String getJsonString() {
		return getJsonStringBuilder(0).toString();
	}
	
	protected StringBuilder getJsonStringBuilder(int tab) {
		StringBuilder ret = new StringBuilder();
		for(int i = tab; i > 0; i--) {
			ret.append('\t');
		}
		if (name != null) {
			ret.append('\"');
			ret.append(name);
			ret.append("\": ");
		}
		ret.append("{\n");
		for(JsonElement element : elements) {
			for(int i = tab; i > 0; i--) {
				ret.append('\t');
			}
			ret.append('\t');
			ret.append(element.getJsonStringBuilder());
			ret.append(",\n");
		}
		for(JsonGroup group : groups) {
			ret.append(group.getJsonStringBuilder(tab + 1));
			ret.append(",\n");
		}
		for(int i = tab; i > 0; i--) {
			ret.append('\t');
		}
		ret.append('}');
		return ret;
	}
}
