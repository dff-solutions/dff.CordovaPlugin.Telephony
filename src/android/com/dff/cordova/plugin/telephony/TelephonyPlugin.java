package com.dff.cordova.plugin.telephony;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.dff.cordova.plugin.telephony.action.Call;
import com.dff.cordova.plugin.telephony.action.ClearCallLog;
import com.dff.cordova.plugin.telephony.action.GetCallLog;
import com.dff.cordova.plugin.telephony.action.TelephonyInfo;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;


/**
 * This plugin implements an interface for mocking gps position.
 *
 * @author dff solutions
 */
public class TelephonyPlugin extends CommonPlugin {

    private static final String LOG_TAG = "com.dff.cordova.plugin.telephony.TelephonyPlugin";
    private static final String[] PERMISSIONS =
        {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        };

    private TelephonyPhoneStateListener phoneStateListener;

    public TelephonyPlugin() {
        super(LOG_TAG);
        this.actions.put(TelephonyInfo.ACTION_NAME, TelephonyInfo.class);
        this.actions.put(ClearCallLog.ACTION_NAME, ClearCallLog.class);
        this.actions.put(GetCallLog.ACTION_NAME, GetCallLog.class);
        this.actions.put(Call.ACTION_NAME, Call.class);
    }


    /**
     * request permissions if they are not granted by forwarding them to
     * the common plugin
     */
    private void requestPermissions() {
        for (String permission : PERMISSIONS) {
            CommonPlugin.addPermission(permission);
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                CordovaPluginLog.e(LOG_TAG, "PERMISSIONS DENIED");
                return;
            }
        }
    }

    /**
     * Called after plugin construction and fields have been initialized.
     */
    public void pluginInitialize() {
        super.pluginInitialize();
        requestPermissions();
        this.phoneStateListener = new TelephonyPhoneStateListener(this.cordova);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.phoneStateListener.onDestroy();
    }

    /**
     * Executes the request.
     * <p>
     * This method is called from the WebView thread.
     * To do a non-trivial amount of work, use:
     * cordova.getThreadPool().execute(runnable);
     * <p>
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
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
        } else if (this.actions.containsKey(action)) {
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
            } catch (InstantiationException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            } catch (InvocationTargetException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            } catch (NoSuchMethodException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            } catch (SecurityException e) {
                CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
            }
        }

        if (cordovaAction != null) {
            this.cordova.getThreadPool().execute(cordovaAction);
            return true;
        }

        return super.execute(action, args, callbackContext);
    }
}
