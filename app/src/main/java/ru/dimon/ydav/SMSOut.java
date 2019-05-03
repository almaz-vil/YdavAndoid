package ru.dimon.ydav;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;

import org.json.JSONObject;

/**
 * Исходящие смс
 */

public class SMSOut {
    private String SMSID="0";
    private String SMStext="";
    private String SMSnomer="";
    private String SMSNomerSpc="";
    private String SMSError="";
    private String SMSResult="";
    private Context context;
    String SEND_SMS_ACTION ="ru.dimon.ydav.SEND_SMS_ACTION";
    String DELIVERED_SMS_ACTION ="ru.dimon.ydav.DELIVERED_SMS_ACTION";

    public SMSOut(Context context)
    {
        this.context=context;
    }
    public void Send(String smsnomerSpc, String smstext, String smsnoner)
    {
     this.SMStext=smstext;
     this.SMSNomerSpc=smsnomerSpc;
     this.SMSnomer=smsnoner;
     WriteDataBase();
    }

    private void CreateReceiver()
    {
        Intent sentIntent = new Intent(SEND_SMS_ACTION);
        sentIntent.putExtra("SMSNomerSpc", this.SMSNomerSpc);
        PendingIntent sentPI = PendingIntent.getBroadcast(context,Integer.valueOf(this.SMSNomerSpc),sentIntent,0);
        Intent deliveryIntent = new Intent(DELIVERED_SMS_ACTION);
        deliveryIntent.putExtra("SMSNomerSpc",this.SMSNomerSpc);
        PendingIntent deliverPI = PendingIntent.getBroadcast(context,Integer.valueOf(this.SMSNomerSpc),deliveryIntent,0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(this.SMSnomer,null,this.SMStext,sentPI,deliverPI);

    }
    /**
     * Запись в базу данных
     * @return
     */
    private int WriteDataBase()
    {
        DBHelper dbHelper = new DBHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nomerfona", this.SMSnomer);
        cv.put("textmsg", this.SMStext);
        cv.put("nomerspk", this.SMSNomerSpc);
        cv.put("error", "-1");
        cv.put("result", "-1");
        Long d=db.insert("outsms", null, cv);
        this.SMSID=d.toString();
        dbHelper.close();
        this.CreateReceiver();
        return 1;
    }
    /**
     * Обновить информацию о доставке
     * @param nomerspk индификатор смс
     * @param result информация о доставке
     */
    public void UpdateDeliveryInfo(String nomerspk, String result)
    {
        this.SMSResult=result;
        DBHelper dbHelper = new DBHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("result", this.SMSResult);
        db.update("outsms",cv,"nomerspk="+nomerspk, null);
        dbHelper.close();
    }

    public String getDeliveryInfo(String nomerspk)
    {
        DBHelper dbHelper = new DBHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cur1=db.query("outsms",new String[] {"result"},"nomerspk = ?",new String[] {nomerspk},null,null,null);
        if (cur1 != null) {
            if (cur1.moveToFirst()) do {
                this.SMSResult=cur1.getString(0);
            } while (cur1.moveToNext());
        }
        cur1.close();
        dbHelper.close();
        return this.SMSResult;
    }
    /**
     * Обновить информацию об отправке
     * @param nomerspk индификатор смс
     * @param error информация об отправке
     */
    public void UpdateSendInfo(String nomerspk, String error)
    {
        this.SMSError=error;
        DBHelper dbHelper = new DBHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("error", this.SMSError);
        db.update("outsms",cv,"nomerspk="+nomerspk, null);
        dbHelper.close();
    }


    public String getSendInfo(String nomerspk)
    {
        DBHelper dbHelper = new DBHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cur1=db.query("outsms",new String[] {"error"},"nomerspk = ?",new String[] {nomerspk},null,null,null);
        if (cur1 != null) {
            if (cur1.moveToFirst()) do {
                this.SMSError=cur1.getString(0);
            } while (cur1.moveToNext());
        }
        cur1.close();
        dbHelper.close();
        return this.SMSError;
    }
}
