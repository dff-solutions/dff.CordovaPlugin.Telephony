package com.dff.cordova.plugin.telephony;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelephonyPhoneStateListener extends PhoneStateListener {

	private TelephonyManager telefonyManager;
	private CordovaInterface cordova;
	private CallbackContext onCallStateChangedCallback;
	
	public TelephonyPhoneStateListener(CordovaInterface cordova) {
		super();
		
		this.cordova = cordova;
		this.telefonyManager = (TelephonyManager) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		telefonyManager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void setOnCallStateChangedCallback(CallbackContext onCallStateChangedCallback) {
		this.onCallStateChangedCallback = onCallStateChangedCallback;
	}
	
	public void onDestroy() {
    	telefonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
    	
    	if (this.onCallStateChangedCallback != null) {
    		onCallStateChangedCallback.success();
    	}
	}

	@Override
	public void onCallStateChanged(int state, String number) {
		super.onCallStateChanged(state, number);
		
		String stateName = null;
		
		switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				stateName = "CALL_STATE_IDLE";
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				stateName = "CALL_STATE_OFFHOOK";
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				stateName = "CALL_STATE_RINGING";
				break;
			default:
				break;
		}
		
		Log.d(this.getClass().getName(), "onCallStateChanged - " + stateName + ": " + number);
		
		if (this.onCallStateChangedCallback != null) {
			JSONObject jsonEvent = new JSONObject();
			
			try {
				jsonEvent.put("state", state);
				jsonEvent.put("number", number);
				jsonEvent.put("stateName", stateName);
				
				PluginResult result = new PluginResult(PluginResult.Status.OK, jsonEvent);
	            result.setKeepCallback(true);
	            onCallStateChangedCallback.sendPluginResult(result);
			}
			catch (JSONException je) {
				PluginResult result = new PluginResult(PluginResult.Status.JSON_EXCEPTION, je.getMessage());
	            result.setKeepCallback(true);
	            onCallStateChangedCallback.sendPluginResult(result);
			}
		}
	}
}
