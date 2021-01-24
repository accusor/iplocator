package com.appgate.iplocator.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Clase para mantener metodos relacionados con GSON
 * 
 * @author accusor
 *
 */
public class GsonUtils {

	private static Gson gson;
	
	private static Gson getGson() {
		if (gson == null) {
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gson = gsonBuilder.disableHtmlEscaping()
					.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
					.setPrettyPrinting()
					.create();
		}
		return gson;
	}
	
	public static String toJson(Object obj) {
		return getGson().toJson(obj);
	}
	
}
