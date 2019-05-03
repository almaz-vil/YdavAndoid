package ru.dimon.ydav;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * Обработка информацию о доставке смс.
 */

public class SMSOutDeliveryInfoService extends Service {
    public SMSOutDeliveryInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {return null; }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String result = intent.getExtras().getString("Result");
        String nomerspk = intent.getExtras().getString("SMSNomerSpc");
        SMSOut smsOut = new SMSOut(SMSOutDeliveryInfoService.this);
        smsOut.UpdateDeliveryInfo(nomerspk,result);
        stopSelf();
        return Service.START_NOT_STICKY;
    }
}
