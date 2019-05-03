package ru.dimon.ydav;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import static android.app.Activity.RESULT_OK;

/**
 * Формируем информацию об отправлении СМС
 */
public class SMSOutSendInfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String sms_nomer = intent.getExtras().getString("SMSNomerSpc");
        String sms_error = "-1";
        switch (getResultCode()){
            case RESULT_OK:{
                sms_error="OK";
                break;}
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:{
                sms_error="GENERIC_FAILURE";
                break;}
            case SmsManager.RESULT_ERROR_RADIO_OFF:{
                sms_error="RADIO_OFF";
                break;}
            case SmsManager.RESULT_ERROR_NULL_PDU:{
                sms_error="NUUL_PDU";
                break;}
        }
        Intent intentSend = new Intent(context,SMSOutSendInfoService.class);
        intentSend.putExtra("SMSNomerSpc", sms_nomer);
        intentSend.putExtra("Error", sms_error);
        context.startService(intentSend);
    }
}
