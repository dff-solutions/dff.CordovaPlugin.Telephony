package com.dff.cordova.plugin.telephony;

import java.lang.reflect.InvocationTargetException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.dff.cordova.plugin.telephony.action.Call;
import com.dff.cordova.plugin.telephony.action.GetCallLog;
import com.dff.cordova.plugin.telephony.action.ClearCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyInfo;

import android.util.Log;


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
		this.actions.put(TelephonyInfo.ACTION_NAME, TelephonyInfo.class);
		this.actions.put(ClearCallLog.ACTION_NAME, ClearCallLog.class);
		this.actions.put(GetCallLog.ACTION_NAME, GetCallLog.class);
		this.actions.put(Call.ACTION_NAME, Call.class);
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
		
    	Log.d(LOG_TAG, "call for action: " + action + "; args: " + args);
    	
    	if (action.equals("onCallStateChanged")) {
    		this.phoneStateListener.setOnCallStateChangedCallback(callbackContext);
    		return true;
    	}
		else if (this.actions.containsKey(action)) {
			Class<? extends CordovaAction> actionClass = this.actions.get(action);

			Log.d(LOG_TAG, "found action: " + actionClass.getName());

			try {
				cordovaAction = actionClass
				        .getConstructor(
				                String.class,
				                JSONArray.class,
				                CallbackContext.class,
				                CordovaInterface.class)
				        .newInstance(
				                action,
				                args,
				                callbackContext,
				                this.cordova);
			}
			catch (InstantiationException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
			catch (IllegalAccessException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
			catch (IllegalArgumentException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
			catch (InvocationTargetException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
			catch (NoSuchMethodException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
			catch (SecurityException e) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
			}
		}
    	
    	if (cordovaAction != null) {
    		super.actionHandler.post(cordovaAction);
            return true;
    	}    	

    	return super.execute(action, args, callbackContext);
    }
}
