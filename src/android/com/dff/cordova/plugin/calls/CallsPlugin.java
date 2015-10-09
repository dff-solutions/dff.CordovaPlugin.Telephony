package com.dff.cordova.plugin.calls;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dff.cordova.plugin.calls.action.CallsActionCall;
import com.dff.cordova.plugin.calls.action.CallsActionCallLog;
import com.dff.cordova.plugin.common.action.CordovaAction;

/**
 * This plugin implements an interface for mocking gps position.
 *
 * @author dff solutions
 */
public class CallsPlugin extends CordovaPlugin {
	private TelephonyManager telefonyManager;
	private PhoneStateListener phoneStateListener;
	private CallbackContext onCallStateChangedCallback;

   /**
	* Called after plugin construction and fields have been initialized.
	*/
    public void pluginInitialize() {
    	super.pluginInitialize();
    	this.telefonyManager = (TelephonyManager) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    	this.phoneStateListener = new PhoneStateListener() {
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
    			
    			if (onCallStateChangedCallback != null) {
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
    	};
    	
    	telefonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    
    @Override
    public void onDestroy() {
    	telefonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_NONE);
    	
    	if (this.onCallStateChangedCallback != null) {
    		onCallStateChangedCallback.success();
    	}
    }
    
    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode   The request code originally supplied to startActivityForResult(),
     *                      allowing you to identify who this result came from.
     * @param resultCode    The integer result code returned by the child activity through its setResult().
     * @param intent        An Intent, which can return result data to the caller (various data can be
     *                      attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	Log.i(this.getClass().getName(), "onActivityResult - requestCode: " + requestCode + "; resultCode: " + resultCode + "; intent: " + intent.toString());
    }
    
    /**
    * Executes the request.
    *
    * This method is called from the WebView thread.
    * To do a non-trivial amount of work, use:
    * cordova.getThreadPool().execute(runnable);
    *
    * To run on the UI thread, use:
    * cordova.getActivity().runOnUiThread(runnable);
    *
    * @param action The action to execute.
    * @param args The exec() arguments.
    * @param callbackContext The callback context used when calling back into JavaScript.
    * @return Whether the action was valid.
    */
	@Override
    public boolean execute(String action
    		, final JSONArray args
    		, final CallbackContext callbackContext)
        throws JSONException {
    	CordovaAction cordovaAction = null;
		
    	Log.i(this.getClass().getName(), "call for action: " + action + "; args: " + args);
    	
    	if (action.equals("onCallStateChanged")) {
    		this.onCallStateChangedCallback = callbackContext;
    		return true;
    	}
    	else if (action.equals("calllog")) {
    		
    		cordovaAction = new CallsActionCallLog(
    				action,
    				args,
    				callbackContext,
    				this.cordova
				);
    	}
    	else if (action.equals("call")) {
    		
    		cordovaAction = new CallsActionCall(
    				action,
    				args,
    				callbackContext,
    				this.cordova
				);
    	}
    	
    	if (cordovaAction != null) {
    		cordova.getThreadPool().execute(cordovaAction);
    		// cordova.getActivity().runOnUiThread(cordovaAction);    		
            return true;
    	}    	

        return false;
    }
}
