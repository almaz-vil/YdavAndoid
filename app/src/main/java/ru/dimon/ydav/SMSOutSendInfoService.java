package ru.dimon.ydav;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * Обработка информацию об отправки смс.
 */

public class SMSOutSendInfoService extends Service {
    public SMSOutSendInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {return null; }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String error = intent.getExtras().getString("Error");
        String nomerspk = intent.getExtras().getString("SMSNomerSpc");
        SMSOut smsOut = new SMSOut(SMSOutSendInfoService.this);
        smsOut.UpdateSendInfo(nomerspk,error);
        stopSelf();
        return Service.START_NOT_STICKY;
    }
}
