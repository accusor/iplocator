package com.appgate.iplocator.test;

import com.appgate.iplocator.dto.Location;
import com.appgate.iplocator.utils.GsonUtils;

/**
 * Clase de prueba de las utilidades de GsonUtils
 * 
 * @author accusor
 *
 */
public class GsonTest {
	
	public static void main(String[] args) {

		Location loc = new Location("CO", "Colombia", "Colombia", "?", 4.0, -72.0, "PETERSBURG INTERNET NETWORK LTD.");
		
		System.out.println("Prueba metododo GsonUtils.toJson");
		System.out.println(GsonUtils.toJson(loc));
		loc.setCity("Bogota");
		System.out.println(loc.toString());

		
		System.exit(0);
		
	}
}
