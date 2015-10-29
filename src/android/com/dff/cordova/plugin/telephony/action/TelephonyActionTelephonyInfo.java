package com.dff.cordova.plugin.telephony.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.log.CordovaPluginLog;

public class TelephonyActionTelephonyInfo extends CordovaAction {

	public TelephonyActionTelephonyInfo(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}

	@Override
	public void run() {
		super.run();
		
		try {
			JSONObject jsonSimInfo = new JSONObject();
			
			TelephonyManager telephonyManager = (TelephonyManager) cordova.getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
			
			jsonSimInfo.put("allCellInfo", telephonyManager.getAllCellInfo());
			jsonSimInfo.put("cellLocation", telephonyManager.getCellLocation());
			
			int callState = telephonyManager.getCallState();
			String callStateName = null;
			
			switch (callState) {
			case TelephonyManager.CALL_STATE_IDLE:
				callStateName = "CALL_STATE_IDLE";
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				callStateName = "CALL_STATE_OFFHOOK";
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				callStateName = "CALL_STATE_RINGING";
				break;
			default:
				break;
			}
			
			jsonSimInfo.put("callState", callState);
			jsonSimInfo.put("callStateName", callStateName);
			
			int dataActivity = telephonyManager.getDataActivity();
			String dataActivityName = null;
			
			switch (dataActivity) {
			case TelephonyManager.DATA_ACTIVITY_DORMANT:
				dataActivityName = "DATA_ACTIVITY_DORMANT";
				break;
			case TelephonyManager.DATA_ACTIVITY_IN:
				dataActivityName = "DATA_ACTIVITY_IN";
				break;
			case TelephonyManager.DATA_ACTIVITY_INOUT:
				dataActivityName = "DATA_ACTIVITY_INOUT";
				break;
			case TelephonyManager.DATA_ACTIVITY_NONE:
				dataActivityName = "DATA_ACTIVITY_NONE";
				break;
			case TelephonyManager.DATA_ACTIVITY_OUT:
				dataActivityName = "DATA_ACTIVITY_OUT";
				break;

			default:
				break;
			}
			
			jsonSimInfo.put("dataActivity", dataActivity);
			jsonSimInfo.put("dataActivityName", dataActivityName);
			
			int dataState = telephonyManager.getDataState();
			String dataStateName = null;
			
			switch (dataState) {
			case TelephonyManager.DATA_CONNECTED:
				dataStateName = "DATA_CONNECTED";
				break;
			case TelephonyManager.DATA_CONNECTING:
				dataStateName = "DATA_CONNECTING";
				break;
			case TelephonyManager.DATA_DISCONNECTED:
				dataStateName = "DATA_DISCONNECTED";
				break;
			case TelephonyManager.DATA_SUSPENDED:
				dataStateName = "DATA_SUSPENDED";
				break;

			default:
				break;
			}
			
			jsonSimInfo.put("dataState", dataState);
			jsonSimInfo.put("dataStateName", dataStateName);
			
			int networkType = telephonyManager.getNetworkType();
			String networkTypeName = null;
			
			switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				networkTypeName = "NETWORK_TYPE_1xRTT";
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				networkTypeName = "NETWORK_TYPE_CDMA";
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				networkTypeName = "NETWORK_TYPE_EDGE";
				break;
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				networkTypeName = "NETWORK_TYPE_EHRPD";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				networkTypeName = "NETWORK_TYPE_EVDO_0";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				networkTypeName = "NETWORK_TYPE_EVDO_A";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				networkTypeName = "NETWORK_TYPE_EVDO_B";
				break;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				networkTypeName = "NETWORK_TYPE_GPRS";
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				networkTypeName = "NETWORK_TYPE_HSDPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				networkTypeName = "NETWORK_TYPE_HSPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				networkTypeName = "NETWORK_TYPE_HSPAP";
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				networkTypeName = "NETWORK_TYPE_HSUPA";
				break;
			case TelephonyManager.NETWORK_TYPE_IDEN:
				networkTypeName = "NETWORK_TYPE_IDEN";
				break;
			case TelephonyManager.NETWORK_TYPE_LTE:
				networkTypeName = "NETWORK_TYPE_LTE";
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				networkTypeName = "NETWORK_TYPE_UMTS";
				break;
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				networkTypeName = "NETWORK_TYPE_UNKNOWN";
				break;				

			default:
				break;
			}
			
			jsonSimInfo.put("networkType", networkType);
			jsonSimInfo.put("networkTypeName", networkTypeName);
			
			int phoneType = telephonyManager.getPhoneType();
			String phoneTypeName = null;
					
			switch (phoneType) {
			case TelephonyManager.PHONE_TYPE_CDMA:
				phoneTypeName = "PHONE_TYPE_CDMA";
				break;
			case TelephonyManager.PHONE_TYPE_GSM:
				phoneTypeName = "PHONE_TYPE_GSM";
				break;
			case TelephonyManager.PHONE_TYPE_NONE:
				phoneTypeName = "PHONE_TYPE_NONE";
				break;
			case TelephonyManager.PHONE_TYPE_SIP:
				phoneTypeName = "PHONE_TYPE_SIP";
				break;

			default:
				break;
			}
			
			jsonSimInfo.put("phoneType", phoneType);
			jsonSimInfo.put("phoneTypeName", phoneTypeName);
			
			int simState = telephonyManager.getSimState();
			String simStateName = null;
			
			switch (simState) {
			case TelephonyManager.SIM_STATE_ABSENT:
				simStateName = "SIM_STATE_ABSENT";
				break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				simStateName = "SIM_STATE_NETWORK_LOCKED";
				break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				simStateName = "SIM_STATE_PIN_REQUIRED";
				break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				simStateName = "SIM_STATE_PUK_REQUIRED";
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				simStateName = "SIM_STATE_UNKNOWN";
				break;
			case TelephonyManager.SIM_STATE_READY:
				simStateName = "SIM_STATE_READY";
				break;

			default:
				break;
			}
			
			jsonSimInfo.put("simState", simState);
			jsonSimInfo.put("simStateName", simStateName);
			jsonSimInfo.put("deviceId", telephonyManager.getDeviceId());
			jsonSimInfo.put("deviceSoftwareVersion", telephonyManager.getDeviceSoftwareVersion());
			jsonSimInfo.put("groupIdLevel1", telephonyManager.getGroupIdLevel1());
			jsonSimInfo.put("line1Number", telephonyManager.getLine1Number());
			jsonSimInfo.put("mmsUAProfUrl", telephonyManager.getMmsUAProfUrl());
			jsonSimInfo.put("mmsUserAgent", telephonyManager.getMmsUserAgent());
			jsonSimInfo.put("neighboringCellInfo", telephonyManager.getNeighboringCellInfo());
			jsonSimInfo.put("networkCountryIso", telephonyManager.getNetworkCountryIso());
			jsonSimInfo.put("networkOperator", telephonyManager.getNetworkOperator());
			jsonSimInfo.put("networkOperatorName", telephonyManager.getNetworkOperatorName());
			jsonSimInfo.put("simCountryIso", telephonyManager.getSimCountryIso());
			jsonSimInfo.put("simOperator", telephonyManager.getSimOperator());
			jsonSimInfo.put("simOperatorName", telephonyManager.getSimOperatorName());
			jsonSimInfo.put("simSerialNumber", telephonyManager.getSimSerialNumber());
			jsonSimInfo.put("subscriberId", telephonyManager.getSubscriberId());
			jsonSimInfo.put("voiceMailAlphaTag", telephonyManager.getVoiceMailAlphaTag());
			jsonSimInfo.put("voiceMailNumber", telephonyManager.getVoiceMailNumber());
			
			this.callbackContext.success(jsonSimInfo);
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
