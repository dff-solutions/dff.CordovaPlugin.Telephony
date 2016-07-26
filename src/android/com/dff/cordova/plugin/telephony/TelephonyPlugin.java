package com.dff.cordova.plugin.telephony;

import java.util.Vector;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCall;
import com.dff.cordova.plugin.telephony.action.TelephonyActionCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyActionClearCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyActionTelephonyInfo;
import com.dff.cordova.plugin.telephony.log.CordovaPluginLog;
import com.dff.cordova.plugin.telephony.log.LogListener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * This plugin implements an interface for mocking gps position.
 *
 * @author dff solutions
 */
public class TelephonyPlugin extends CommonPlugin {
	public static final String LOG_TAG = "com.dff.cordova.plugin.telephony.TelephonyPlugin"; 
	
	private TelephonyPhoneStateListener phoneStateListener;
	private LogListener logListener;
	private Vector<CordovaAction> actions = new Vector<CordovaAction>();
	
	public String[] permissions = new String[] {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.READ_CONTACTS,
			Manifest.permission.READ_CALL_LOG,
			Manifest.permission.WRITE_CALL_LOG,
			Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE
	};

   /**
	* Called after plugin construction and fields have been initialized.
	*/
    public void pluginInitialize() {
    	super.pluginInitialize();
    	this.phoneStateListener = new TelephonyPhoneStateListener(this.cordova);
    	this.logListener = new LogListener();
    	CordovaPluginLog.addLogListner(this.logListener);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	CordovaPluginLog.removeLogListener(this.logListener);
    	this.phoneStateListener.onDestroy();
    	this.logListener.onDestroy();
    }
    
    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions,
            int[] grantResults) throws JSONException {
    	
    	super.onRequestPermissionResult(requestCode, permissions, grantResults);
    	
    	CordovaPluginLog.d(LOG_TAG, "onRequestPermissionResult: " + requestCode);
    
    	for (int i = 0; i < grantResults.length; i++) {
    		int r = grantResults[i];
    		String p = permissions[i];
    		
    		if (r == PackageManager.PERMISSION_DENIED) {
    			CordovaPluginLog.d(LOG_TAG, "permission denied for: " + p);
    		}
    		else if (r == PackageManager.PERMISSION_GRANTED) {
    			CordovaPluginLog.d(LOG_TAG, "permission granted for: " + p);
    		}   		    		
    	}
    	
    	this.execute();
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
    		actions.add(cordovaAction);
    		
    		if (this.hasPermissions(permissions)) {
    			this.execute();
    		}
    		else {
    			this.cordova.requestPermissions(this, 0, permissions);
    		}
    		    		
            return true;
    	}    	

        return super.execute(action, args, callbackContext);
    }
	
	private boolean hasPermissions(String[] permissions) {		
		for (String p : permissions) {
			if (!this.cordova.hasPermission(p)) {
				return false;
			}
		}
		
		return true;
	}
	
	private void execute() {
    	// execute all actions anyway. If permission is missing the error should be handled by action
    	for (CordovaAction cordovaAction : this.actions) {
			cordova.getThreadPool().execute(cordovaAction);
    		
    		if (!this.actions.remove(cordovaAction)) {
    			CordovaPluginLog.e(LOG_TAG, "could not remove action from list: " + cordovaAction.toString());
    		}
    	}
	}
}
