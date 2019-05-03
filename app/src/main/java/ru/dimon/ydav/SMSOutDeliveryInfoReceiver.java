package ru.dimon.ydav;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Формируем информацию о доставке смс.
 */
public class SMSOutDeliveryInfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String sms_nomer = intent.getExtras().getString("SMSNomerSpc");
        String sms_result = "-1";
        switch (getResultCode())
        {
            case RESULT_OK:{
                // успешная дставки СМС
                sms_result = "OK";
                break;}
            case RESULT_CANCELED:{
                // не доставлено СМС
                sms_result = "CANCELED";
                break;}
        }

        Intent ServicOUSMC2 = new Intent(context,SMSOutDeliveryInfoService.class);
        ServicOUSMC2.putExtra("SMSNomerSpc", sms_nomer);
        ServicOUSMC2.putExtra("Result", sms_result);
        context.startService(ServicOUSMC2);
    }
}
