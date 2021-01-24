package com.appgate.iplocator.test;

import com.appgate.iplocator.utils.LocatorUtils;


/**
 * Clase de prueba de las utilidades de LocatorUtils
 * 
 * @author accusor
 *
 */
public class UtilsTest {

	private static void testDecToIpV4() {		
		Long ipdec = 3232236045L;
		System.out.println("Prueba metododo LocatorUtils.decToIpv4");
		System.out.println(ipdec + " -> " + LocatorUtils.decToIpv4(ipdec));
	}
	
	private static void testIpv4ToDec() {		
		String ipv4 = "201.184.37.255";
		System.out.println("Prueba metododo LocatorUtils.ipv4ToDec");		
		System.out.println(ipv4 + " -> " + LocatorUtils.ipv4ToDec(ipv4));
	}
	
	public static void main(String[] args) {
		
		testDecToIpV4();
		testIpv4ToDec();
		
		System.exit(0);
		
	}

}
