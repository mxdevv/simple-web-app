import json.*;

public class jsonTest {
  public static void main(String args[]) {
  	JsonGroup jsonGroup1 = new JsonGroup();
		jsonGroup1.setName("group1");
		jsonGroup1.addElement(new JsonElement("elem1", "val1"));
		jsonGroup1.addElement(new JsonElement("elem2", "val2"));
		JsonGroup jsonGroup2 = new JsonGroup("group2");
		jsonGroup2.addElement(new JsonElement("elem3", "val3"));
		jsonGroup2.addElement(new JsonElement("elem4", "val4"));
		jsonGroup2.addElement(new JsonElement("elem5", "val5"));
		jsonGroup1.addGroup(jsonGroup2);
		System.out.println(jsonGroup1.getJsonString());
	}
}
