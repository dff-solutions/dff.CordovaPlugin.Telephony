package com.dff.cordova.plugin.telephony.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

public class Call extends CordovaAction {
	private static final String LOG_TAG = "TelephonyActionCall";
	public static final String ACTION_NAME = "call";
	
	public static final String JSON_ARG_NUMBER = "number";
	public static final String[] JSON_ARGS = new String[] { JSON_ARG_NUMBER };

	public Call(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}

	@Override
	public void run() {
		super.run();
		String number;
		Uri numberUri;
		
		try {
			JSONObject jsonArgs = super.checkJsonArgs(args, JSON_ARGS);
			
			number = jsonArgs.getString(JSON_ARG_NUMBER);
			numberUri = Uri.parse("tel:" + Uri.encode(number));
			
			CordovaPluginLog.i(LOG_TAG, "calling number: " + number + "; number uri: " +  numberUri.toString());
			
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(numberUri);
			this.cordova.getActivity().startActivity(callIntent);
			this.callbackContext.success();
		}
		catch(JSONException e) {
			CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			this.callbackContext.error(e.getMessage());
		}
		catch(Exception ex) {
			CordovaPluginLog.e(LOG_TAG, ex.getMessage(), ex);
			this.callbackContext.error(ex.getMessage());
		}
	}
}
