package ths.spring;

public class Schema {
	public static final String XML_SCHEMA_EXTENSION = ".xsd";
	public static final String SCHEMA_LOCATION_PREFIX = "META-INF/schema/";
	
	public static String[] getServicesSchemaLoction(String namespace, String name) {
		String[] map = new String[2];
		map[0] = namespace + XML_SCHEMA_EXTENSION;
		map[1] = SCHEMA_LOCATION_PREFIX + name + XML_SCHEMA_EXTENSION;
		return map;
	}

	public static String[] getServicesSchemaLoction(String namespace, String name, String element) {
		String[] map = new String[2];
		map[0] = namespace +"/"+ element + XML_SCHEMA_EXTENSION;
		map[1] = SCHEMA_LOCATION_PREFIX + name +"/"+ element + XML_SCHEMA_EXTENSION;
		return map;
	}
}
