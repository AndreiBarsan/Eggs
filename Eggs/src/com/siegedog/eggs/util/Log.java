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
	}
	
	
	public static void D(String message, Exception exception) {
		debugLog.debug(message, exception);
	}
}
