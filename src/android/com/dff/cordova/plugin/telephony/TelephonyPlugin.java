package com.dff.cordova.plugin.telephony;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.log.CordovaPluginLog;
import com.dff.cordova.plugin.log.LogListener;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCall;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCallLog;

/**
 * This plugin implements an interface for mocking gps position.
 *
 * @author dff solutions
 */
public class TelephonyPlugin extends CordovaPlugin {
	private TelephonyPhoneStateListener phoneStateListener;
	private LogListener logListener;

   /**
	* Called after plugin construction and fields have been initialized.
	*/
    public void pluginInitialize() {
    	super.pluginInitialize();
    	this.phoneStateListener = new TelephonyPhoneStateListener(this.cordova);
    	this.logListener = new LogListener();
    }
    
    @Override
    public void onDestroy() {
    	this.phoneStateListener.onDestroy();
    	this.logListener.onDestroy();
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
    	CordovaPluginLog.i(this.getClass().getName(), "onActivityResult - requestCode: " + requestCode + "; resultCode: " + resultCode + "; intent: " + intent.toString());
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
		
    	CordovaPluginLog.i(this.getClass().getName(), "call for action: " + action + "; args: " + args);
    	
    	if (action.equals("onCallStateChanged")) {
    		this.phoneStateListener.setOnCallStateChangedCallback(callbackContext);
    		return true;
    	}
    	if (action.equals("onLog")) {
    		this.logListener.setOnLogCallBack(callbackContext);
    		return true;
    	}
    	else if (action.equals("calllog")) {
    		
    		cordovaAction = new TelephonyActionCallLog(
    				action,
    				args,
    				callbackContext,
    				this.cordova
				);
    	}
    	else if (action.equals("call")) {
    		
    		cordovaAction = new TelephonyActionCall(
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
