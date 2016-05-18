package com.dff.cordova.plugin.telephony.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.telephony.log.CordovaPluginLog;

public class TelephonyActionCall extends CordovaAction {
	public static final String LOG_TAG = "TelephonyActionCall";

	public TelephonyActionCall(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}

	@Override
	public void run() {
		super.run();
		String number;
		Uri numberUri;
		
		try {
			JSONObject jsonArgs = this.args.getJSONObject(0);
			
			if (jsonArgs == null) {
				this.callbackContext.error("no args given");
			}
			else if (!jsonArgs.has("number")) {
				this.callbackContext.error("number missing");
			}
			else {
				number = jsonArgs.getString("number");
				numberUri = Uri.parse("tel:" + Uri.encode(number));
				
				CordovaPluginLog.i(LOG_TAG, "calling number: " + number + "; number uri: " +  numberUri.toString());
				
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(numberUri);
				this.cordova.getActivity().startActivity(callIntent);
				this.callbackContext.success();
			}
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
