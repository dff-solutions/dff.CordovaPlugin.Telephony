package com.dff.cordova.plugin.telephony.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.dff.cordova.plugin.common.action.CordovaAction;
import com.dff.cordova.plugin.telephony.log.CordovaPluginLog;

public class TelephonyActionCallLog extends CordovaAction {
	public TelephonyActionCallLog(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova) {
		super(action, args, callbackContext, cordova);
	}
	
	@Override
	public void run() {
		super.run();
		JSONArray jsonCallLog = new JSONArray();
		JSONObject jsonCall;
		Cursor managedCursor = this.cordova.getActivity().getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		
		String columnName;
		int columnIndex;
		Map<Integer, String> columnMap = new HashMap<Integer, String>();
		Iterator<Map.Entry<Integer, String>> icolumns;
		Map.Entry<Integer, String> me;
		
		String[] columnNames = managedCursor.getColumnNames();
		
		for (int i = 0; i < columnNames.length; i++) {
			columnName = columnNames[i];
			columnIndex = managedCursor.getColumnIndex(columnName);
			columnMap.put(columnIndex, columnName);
			// CordovaPluginLog.d(this.getClass().getName(), columnName + " " + columnIndex);
		}
				
		int chachedNumberType;
		String cachedNumberTypeLabel;
		int numberPresentation;
		String numberPresentationLabel; 
		int type;
		String typeLabel;	
		
		try {			
			while (managedCursor.moveToNext()) {
				jsonCall = new JSONObject();				
				icolumns = columnMap.entrySet().iterator();
				
				while(icolumns.hasNext()) {
					me = icolumns.next();
					columnIndex = me.getKey();
					columnName = me.getValue();
					
					if (columnName.equals(CallLog.Calls.CACHED_NUMBER_TYPE)) {
						chachedNumberType = managedCursor.getInt(columnIndex);
						
						switch (chachedNumberType) {
					    case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
					        cachedNumberTypeLabel = "CUSTOM";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
					        cachedNumberTypeLabel = "HOME";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
					        cachedNumberTypeLabel = "MOBILE";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
					        cachedNumberTypeLabel = "WORK";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
					        cachedNumberTypeLabel = "FAX_WORK";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
					        cachedNumberTypeLabel = "FAX_HOME";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
					        cachedNumberTypeLabel = "PAGER";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
					        cachedNumberTypeLabel = "OTHER";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
					        cachedNumberTypeLabel = "CALLBACK";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
					        cachedNumberTypeLabel = "CAR";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
					        cachedNumberTypeLabel = "COMPANY_MAIN";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
					        cachedNumberTypeLabel = "ISDN";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
					        cachedNumberTypeLabel = "MAIN";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
					        cachedNumberTypeLabel = "OTHER_FAX";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
					        cachedNumberTypeLabel = "RADIO";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
					        cachedNumberTypeLabel = "TELEX";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
					        cachedNumberTypeLabel = "TTY_TDD";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
					        cachedNumberTypeLabel = "WORK_MOBILE";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
					        cachedNumberTypeLabel = "WORK_PAGER";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
					        cachedNumberTypeLabel = "ASSISTANT";
					        break;
					    case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
					        cachedNumberTypeLabel = "MMS";
					        break;

						default:
							cachedNumberTypeLabel = null;
							break;
						}
						
						jsonCall.put(columnName, chachedNumberType);
						jsonCall.put("cachedNumberTypeLabel", cachedNumberTypeLabel);
					}
					else if (columnName.equals(CallLog.Calls.NUMBER_PRESENTATION)) {
						numberPresentation = managedCursor.getInt(columnIndex);
						switch (numberPresentation) {
						case CallLog.Calls.PRESENTATION_ALLOWED:
							numberPresentationLabel = "ALLOWED";
							break;
						case CallLog.Calls.PRESENTATION_PAYPHONE:
							numberPresentationLabel = "PAYPHONE";
							break;
						case CallLog.Calls.PRESENTATION_RESTRICTED:
							numberPresentationLabel = "RESTRICTED";
							break;					
						case CallLog.Calls.PRESENTATION_UNKNOWN:
							numberPresentationLabel = "UNKNOWN";
							break;					
						default:
							numberPresentationLabel = null;
							break;
						}
						
						jsonCall.put(columnName, numberPresentation);
						jsonCall.put("numberPresentationLabel", numberPresentationLabel);
					}
					else if (columnName.equals(CallLog.Calls.TYPE)) {
						type = managedCursor.getInt(columnIndex);
						
						switch (type) {
						case CallLog.Calls.INCOMING_TYPE:
							typeLabel = "INCOMING";
							break;
						case CallLog.Calls.MISSED_TYPE:
							typeLabel = "MISSED";
							break;

						case CallLog.Calls.OUTGOING_TYPE:
							typeLabel = "OUTGOING";
							break;
						default:
							typeLabel = null;
							break;
						}
						
						jsonCall.put(columnName, type);
						jsonCall.put("typeLabel", typeLabel);
		
					}
					else if (columnName.equals(CallLog.Calls.DATE)
							|| columnName.equals(CallLog.Calls.DURATION)) {
						jsonCall.put(columnName, managedCursor.getLong(columnIndex));						
					}
					else if (columnName.equals(CallLog.Calls.IS_READ)
							|| columnName.equals(CallLog.Calls.NEW)) {
						jsonCall.put(columnName, managedCursor.getInt(columnIndex));
					}
					else {
						jsonCall.put(columnName, managedCursor.getString(columnIndex));
					}
					
				}				
								
				jsonCallLog.put(jsonCall);
			}
			
			managedCursor.close();
			
			this.callbackContext.success(jsonCallLog);
			
		}
		catch(JSONException e) {
			CordovaPluginLog.e(this.getClass().getName(), e.getMessage(), e);
			callbackContext.error(e.getMessage());
		}
		catch(Exception ex) {
			CordovaPluginLog.e(this.getClass().getName(), ex.getMessage(), ex);
			callbackContext.error(ex.getMessage());
		}
		
		
		
	}

}
