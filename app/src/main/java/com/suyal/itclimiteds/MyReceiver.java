package com.suyal.itclimiteds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {

//    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
//    private static final String TAG="SmsBroadcastReceiver";
//    String msg,phoneNo="";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.e(TAG,"Intent Received : "+intent.getAction());
//        if(intent.getAction() == SMS_RECEIVED){
//            Bundle dataBundle = intent.getExtras();
//            if(dataBundle!=null){
//                Object[] mypdu =(Object[])dataBundle.get("mypdu");
//                final SmsMessage[] message = new SmsMessage[mypdu.length];
//
//                for(int i=0;i< message.length;i++){
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//                        String format = dataBundle.getString("format");
//                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i],format);
//                    }else{
//                        message[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
//                    }
//                    msg = message[i].getMessageBody();
//                    phoneNo = message[i].getOriginatingAddress();
//                }
//                Toast.makeText(context, "Message : "+msg+"\nNumber"+phoneNo, Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}