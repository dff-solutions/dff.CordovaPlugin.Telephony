package com.dff.cordova.plugin.calls.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.dff.cordova.plugin.common.action.CordovaAction;

public class CallsActionCall extends CordovaAction {

	public CallsActionCall(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}

	@Override
	public void run() {
		super.run();
		String number;
		
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
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + number));
				this.cordova.getActivity().startActivity(callIntent);
				this.callbackContext.success();
			}
		}
		catch(JSONException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			this.callbackContext.error(e.getMessage());
		}
		catch(Exception ex) {
			Log.e(this.getClass().getName(), ex.getMessage(), ex);
			this.callbackContext.error(ex.getMessage());
		}
	}
}
