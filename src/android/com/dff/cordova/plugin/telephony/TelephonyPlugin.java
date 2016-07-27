package com.dff.cordova.plugin.telephony;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCall;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyActionClearCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyActionTelephonyInfo;


/**
 * This plugin implements an interface for mocking gps position.
 *
 * @author dff solutions
 */
public class TelephonyPlugin extends CommonPlugin {
	private static final String LOG_TAG = "com.dff.cordova.plugin.telephony.TelephonyPlugin";
	
	private TelephonyPhoneStateListener phoneStateListener;
	
	public TelephonyPlugin() {
		super(LOG_TAG);
	}

   /**
	* Called after plugin construction and fields have been initialized.
	*/
    public void pluginInitialize() {
    	super.pluginInitialize();
    	this.phoneStateListener = new TelephonyPhoneStateListener(this.cordova);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	this.phoneStateListener.onDestroy();
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
		
    	CordovaPluginLog.i(LOG_TAG, "call for action: " + action + "; args: " + args);
    	
    	if (action.equals("onCallStateChanged")) {
    		this.phoneStateListener.setOnCallStateChangedCallback(callbackContext);
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
    	else if (action.equals("clearCalllog")) {
    		
    		cordovaAction = new TelephonyActionClearCallLog(
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
    	else if (action.equals("telephonyinfo")) {
    		
    		cordovaAction = new TelephonyActionTelephonyInfo(
    				action,
    				args,
    				callbackContext,
    				this.cordova
				);
    	}
    	
    	if (cordovaAction != null) {
    		cordova.getThreadPool().execute(cordovaAction);   		
            return true;
    	}    	

    	return super.execute(action, args, callbackContext);
    }
}
