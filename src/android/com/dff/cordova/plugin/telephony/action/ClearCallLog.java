package com.dff.cordova.plugin.telephony.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.CallLog;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

public class ClearCallLog extends CordovaAction {
	public static final String ACTION_NAME = "clearCalllog";
	
	public ClearCallLog(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject jsonArgs = this.args.getJSONObject(0);
			JSONArray jsonSelectionArgs;
			String where = "";
			
			String[] selectionArgs = new String[] {};
			
			if (jsonArgs != null) {
				where = jsonArgs.optString("where", where);
				jsonSelectionArgs = jsonArgs.optJSONArray("selectionArgs");
				if (jsonSelectionArgs != null) {
					selectionArgs = new String[jsonSelectionArgs.length()];
					
					for (int i = 0; i < jsonSelectionArgs.length(); i++) {
						selectionArgs[i] = jsonSelectionArgs.getString(i);
					}
				}
			}
			
			int deleted = this.cordova
					.getActivity()
					.getApplicationContext()
					.getContentResolver()
					.delete(CallLog.Calls.CONTENT_URI, where, selectionArgs);
			this.callbackContext.success(deleted);
		}
		catch(JSONException e) {
			CordovaPluginLog.e(this.getClass().getName(), e.getMessage(), e);
			this.callbackContext.error(e.getMessage());
		}
		catch(Exception ex) {
			CordovaPluginLog.e(this.getClass().getName(), ex.getMessage(), ex);
			this.callbackContext.error(ex.getMessage());
		}
	}

}
