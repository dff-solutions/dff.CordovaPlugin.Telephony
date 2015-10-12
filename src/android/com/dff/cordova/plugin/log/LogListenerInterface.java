package com.dff.cordova.plugin.log;

public interface LogListenerInterface {	
	public void onLog(int type, String tag, String msg);
	public void onLog(int type, String tag, Throwable tr);
	public void onLog(int type, String tag, String msg, Throwable tr);

}
