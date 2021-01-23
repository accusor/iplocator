package com.appgate.iplocator.utils;

public class LocatorUtils {

	private static final String IP_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
	
	private static final Integer BASE = 256;
	
	private static final Long MIN_IP = 0L;
	private static final Long MAX_IP = 4294967295L;
	
	/**
	 * Convierte una ip en formato IPv4 a formato decimal
	 * 
	 * @param ip ip a convertir en formato IPV4
	 * @return ip en formato decimal
	 */
	public static Long ipv4ToDec(String ip) {
		
		// Validamos que venga el parámetro ip
		if (ip == null || ip.trim().length() == 0) {
			throw new IllegalArgumentException("El parametro ip no puede ser nulo o vacio");
		}
		
		// Validamos que el parámetro ip tenga un formato IPv4 válido
		if (!isIpv4Valid(ip)) {
			throw new IllegalArgumentException("El parametro ip no tiene un formato IPv4 valido");
		}
		
		String[] octets = ip.split("\\.");
		
		Double result = 0.0;
		int pow = 3;
		for (String octet : octets) {
			result = result + (Integer.parseInt(octet, 10) * Math.pow(BASE, pow));
			pow--;
		}
		
		return Math.round(result);
		
	}
	
	/**
	 * Convierte una ip en formato decimal a formato ipv4
	 * 
	 * @param ip ip a convertir en formato decimal
	 * @return ip en formato IPv4
	 */
	public static String decToIpv4 (Long ip) {
		
		// Validamos que venga el parámetro ip
		if (ip == null) {
			throw new IllegalArgumentException("El parametro ip no puede ser nulo");
		}
		
		// Validamos que el parametro ip haga parte del rango de ips validas
		if (!isIpDecValid(ip)) {
			throw new IllegalArgumentException("El parametro ip no se encuentra en un rago valido");
		}
		
		String octets = "";
		String prefix = "";
		Long quotient = ip;
		for (int i = 0 ; i < 4 ; i++) {
			Long remainder = quotient % BASE;
			quotient = quotient / BASE;
			octets = remainder + prefix + octets;
			prefix = ".";
		}
		
		return octets;
		
	}
	
	
	/**
	 * Valida si la ip en formato ipv4 tiene un formato valido
	 * 
	 * @param ip ip a validar en formato ipv4
	 * @return <i>true</i> si la ip es valida<br/>
	 * 		   <i>false</i> en caso contrario
	 */
	public static Boolean isIpv4Valid(String ip) {
		return ip != null && ip.matches(IP_PATTERN);
	}
	
	/**
	 * Valida si la ip en formato decimal se encuentra dentro del rango valido
	 * 
	 * @param ip ip a validar en formato decimal
	 * @return <i>true</i> si la ip es valida<br/>
	 * 		   <i>false</i> en caso contrario
	 */
	public static Boolean isIpDecValid(Long ip) {
		return ip != null && (ip >= MIN_IP && MAX_IP >= ip);
	}
	
}
