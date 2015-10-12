/**
 * 
 */
package com.dff.cordova.plugin.log;

import java.util.Iterator;
import java.util.Vector;

import android.util.Log;

/**
 * @author fkoenigstein
 *
 */
public class CordovaPluginLog {

	private static Vector<LogListenerInterface> logListeners =
			new Vector<LogListenerInterface>();
	
	public static void addLogListner(LogListenerInterface li) {
		logListeners.add(li);
	}
	
	public static void removeLogListener(LogListenerInterface li) {
		logListeners.remove(li);
	}
	
	private static void fireLogEvent(int type, String tag, String msg) {
		Iterator<LogListenerInterface>lliterator = logListeners.iterator();
		LogListenerInterface logListener;
		
		while(lliterator.hasNext()){
			logListener = lliterator.next();
			
			logListener.onLog(type, tag, msg);
		}
	}
	
	private static void fireLogEvent(int type, String tag, String msg, Throwable tr) {
		Iterator<LogListenerInterface>lliterator = logListeners.iterator();
		LogListenerInterface logListener;
		
		while(lliterator.hasNext()){
			logListener = lliterator.next();
			
			logListener.onLog(type, tag, msg, tr);
		}
	}
	
	private static void fireLogEvent(int type, String tag, Throwable tr) {
		Iterator<LogListenerInterface>lliterator = logListeners.iterator();
		LogListenerInterface logListener;
		
		while(lliterator.hasNext()){
			logListener = lliterator.next();
			
			logListener.onLog(type, tag, tr);
		}
	}
	

	public static void d(String tag, String msg) {
		Log.d(tag, msg);
		fireLogEvent(Log.DEBUG, tag, msg);
	}
	
	public static void d(String tag, String msg, Throwable tr) {
		Log.d(tag, msg, tr);
		fireLogEvent(Log.DEBUG, tag, msg, tr);
	}
	
	public static void e(String tag, String msg) {
		Log.e(tag, msg);
		fireLogEvent(Log.ERROR, tag, msg);
	}
	
	public static void e(String tag, String msg, Throwable tr) {
		Log.e(tag, msg, tr);
		fireLogEvent(Log.ERROR, tag, msg, tr);
	}
	
	public static void i(String tag, String msg) {
		Log.i(tag, msg);
		fireLogEvent(Log.INFO, tag, msg);
	}
	
	public static void i(String tag, String msg, Throwable tr) {
		Log.i(tag, msg, tr);
		fireLogEvent(Log.INFO, tag, msg, tr);
	}
	
	public static void v(String tag, String msg) {
		Log.v(tag, msg);
		fireLogEvent(Log.VERBOSE, tag, msg);
	}
	
	public static void v(String tag, String msg, Throwable tr) {
		Log.v(tag, msg, tr);
		fireLogEvent(Log.VERBOSE, tag, msg, tr);
	}
	
	public static void w(String tag, String msg) {
		Log.w(tag, msg);
		fireLogEvent(Log.WARN, tag, msg);
	}

	public static void w(String tag, Throwable tr) {
		Log.w(tag, tr);
		fireLogEvent(Log.WARN, tag, tr);
	}	
	
	public static void w(String tag, String msg, Throwable tr) {
		Log.w(tag, msg, tr);
		fireLogEvent(Log.WARN, tag, msg, tr);
	}
}
