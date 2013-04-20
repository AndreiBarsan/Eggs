package com.siegedog.eggs.util;

import com.badlogic.gdx.utils.Logger;

public class Log {
	private static Logger debugLog = new Logger("Debug", Logger.DEBUG);
	private static Logger errorLog = new Logger("Error", Logger.ERROR);
	
	public static void D(String message) {
		debugLog.debug(message);
	}
	
	public static void E(String message) {
		errorLog.error(message);
		StackTraceElement[] el = Thread.currentThread().getStackTrace();
		for(int i = 2; i < el.length; ++i) {
			errorLog.error(el[i].toString());
		}
		System.exit(-1);
		
	}
	
	
	public static void D(String message, Exception exception) {
		debugLog.debug(message, exception);
	}
}
