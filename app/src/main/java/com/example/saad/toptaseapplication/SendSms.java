package com.example.saad.toptaseapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSms extends AppCompatActivity {

    Button btn_SendSMS;
    EditText txt_smsContent;
    EditText txt_smsNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        btn_SendSMS = (Button) findViewById(R.id.btn_SendSMS);
        txt_smsContent = (EditText) findViewById(R.id.txt_smsContent);
        txt_smsNumber = (EditText) findViewById(R.id.txt_smsNumber);

        btn_SendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = txt_smsNumber.getText().toString();
                String message = txt_smsContent.getText().toString();
                if (phoneNo.length()>0 && message.length()>0) {
                    sendSMS(phoneNo, message);
                    Toast.makeText(getBaseContext(),
                            "Message Sent",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendSMS(String phoneNumber, String message)
    {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
