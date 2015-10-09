package com.dff.cordova.plugin.common.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

public abstract class CordovaAction implements Runnable {

	protected String action;
	protected JSONArray args;
	protected CallbackContext callbackContext;
	protected CordovaInterface cordova;
	
	public CordovaAction(String action, JSONArray args, CallbackContext callbackContext, CordovaInterface cordova) {
		this.action = action;
		this.args = args;
		this.callbackContext = callbackContext;
		this.cordova = cordova;
	}
	
	@Override
	public void run() {
		Log.i(this.getClass().getName(), "running action: " + this.action + "; args: " + this.args);
	}

}
