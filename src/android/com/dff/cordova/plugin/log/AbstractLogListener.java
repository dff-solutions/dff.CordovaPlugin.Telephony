package com.dff.cordova.plugin.log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class AbstractLogListener implements LogListenerInterface {

	protected CallbackContext onLogCallback;
	
	public void setOnLogCallBack(CallbackContext onLogCallback) {
		this.onLogCallback = onLogCallback;		
	}
	
	public void onDestroy() {
		if (onLogCallback != null) {
			onLogCallback.success();
		}
	}
	
	protected String getLogType(int type) {
		String logType;
		
		switch (type) {
		case Log.DEBUG:
			logType = "DEBUG";
			break;
		case Log.ERROR:
			logType = "ERROR";
			break;
		case Log.INFO:
			logType = "INFO";
			break;
		case Log.VERBOSE:
			logType = "VERBOSE";
			break;
		case Log.WARN:
			logType = "WARN";
			break;

		default:
			logType = "UNKNOWN";
			break;
		}
		
		return logType;
	}
	
	protected void sendPluginResult(JSONObject logEvent) {
		if (this.onLogCallback != null) {
			PluginResult result = new PluginResult(PluginResult.Status.OK, logEvent);
            result.setKeepCallback(true);
            onLogCallback.sendPluginResult(result);
		}
	}
	
	protected void sendPluginResult(JSONException je) {
		if (this.onLogCallback != null) {
			PluginResult result = new PluginResult(PluginResult.Status.JSON_EXCEPTION, je.getMessage());
            result.setKeepCallback(true);
            onLogCallback.sendPluginResult(result);
		}
	}

	@Override
	public void onLog(int type, String tag, String msg) {}

	@Override
	public void onLog(int type, String tag, Throwable tr) {}

	@Override
	public void onLog(int type, String tag, String msg, Throwable tr) {}

}
