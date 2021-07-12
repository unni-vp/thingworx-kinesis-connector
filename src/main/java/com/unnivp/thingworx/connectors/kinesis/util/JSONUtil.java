package com.unnivp.thingworx.connectors.kinesis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for JSON object/string manipulation.
 * 
 * @author unnivp
 *
 */
public class JSONUtil {

	/**
	 * Get JSON string for the input object.
	 * @param object the object for which JSON string is to be generated
	 * @return JSON string representation of the object
	 */
	public static String getJsonString(Object object) {

		String jsonStr = "";
		ObjectMapper mapperObj = new ObjectMapper();

		try {
			jsonStr = mapperObj.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonStr;
	}
}
