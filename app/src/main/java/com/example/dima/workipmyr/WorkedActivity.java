package com.example.dima.workipmyr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dima.workipmyr.activity.ActivityCustomer;
import com.example.dima.workipmyr.activity.ActivityDrivers;

public class WorkedActivity extends AppCompatActivity {

    DB db;


    private static final int PERMISSIONS_SEND_SMS = 60;

    private int REQUEST_CODE_DRIVER = 1;
    private int REQUEST_CODE_CUSTOMER = 2;

     TextView textWork;
     TextView tvName;
     TextView tvSername;
     TextView tvPatro;

    EditText edData;
    EditText edTime;

    CheckBox cbMoney;
    CheckBox cbKM;


    String nameDriver;
    String surnameDriver;
    String patronymicDriver;
    String phoneDriver;

    String nameCustomer;
    String addressCustomer;

    TextView tvNameCustomer;
    TextView tvAddressCustomer;

    Button sendSMS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.give_work);
        db = new DB(this);

        cbMoney = (CheckBox)findViewById(R.id.cbMoney);
        cbKM = (CheckBox)findViewById(R.id.cbKM);

        textWork = (TextView) findViewById(R.id.TXGiveWork);
        tvName    = (TextView) findViewById(R.id.nameWork);
        tvSername = (TextView) findViewById(R.id.surnameWork);
        tvPatro   = (TextView) findViewById(R.id.patroWork);

        tvNameCustomer = (TextView) findViewById(R.id.nameCustomerWork);
        tvAddressCustomer = (TextView) findViewById(R.id.addressCustomerWork);

        edData = (EditText) findViewById(R.id.EDDataWork);
        edTime = (EditText) findViewById(R.id.EDTimeWork);

        sendSMS = (Button) findViewById(R.id.sendSMS);




        tvSername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkedActivity.this, ActivityDrivers.class);
                startActivityForResult(intent, REQUEST_CODE_DRIVER);
            }
        });

        tvNameCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkedActivity.this, ActivityCustomer.class);
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }
        });

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.openDB();
                doSendSMSViaManager(phoneDriver);
                db.close();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_DRIVER) {
                nameDriver = data.getStringExtra("nameDr");
                surnameDriver = data.getStringExtra("surnameDr");
                patronymicDriver = data.getStringExtra("patroDr");
                phoneDriver = data.getStringExtra("phoneDr");

                tvName.setText(nameDriver);
                tvSername.setText(surnameDriver);
                tvPatro.setText(patronymicDriver);
            }
            if (requestCode == REQUEST_CODE_CUSTOMER){
                nameCustomer = data.getStringExtra("nameCU1");
                addressCustomer = data.getStringExtra("addressCU1");

                tvNameCustomer.setText(nameCustomer);
                tvAddressCustomer.setText(addressCustomer);
            }
        }


    }

    // Посылаем смс сами - смс нигде не фиксируются (только в счете от оператора)
    // Требуется разрешение SEND_SMS
    private void doSendSMSViaManager(String phoneDriver) {
        String  money="";
        String  km="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                )
        {
            requestPermissions(
                    new String[]{
                            Manifest.permission.SEND_SMS
                    },
                    PERMISSIONS_SEND_SMS);
        } else {



            if (cbMoney.isChecked()){money = ", наличные";}
            if (cbKM.isChecked()){km = ", километраж";}
            String nameCustomer = tvNameCustomer.getText().toString();
            String dataWork = edData.getText().toString();
            String timeWork = edTime.getText().toString();
            String addressCustomer = tvAddressCustomer.getText().toString();

            String nameDriver = tvName.getText().toString();
            String surNameDriver = tvSername.getText().toString();
            String patroDriver = tvPatro.getText().toString();

            String sms = nameCustomer+ ", " + dataWork+", "+ timeWork +", "+ addressCustomer
                    + money + km;
            if (!sms.equals("")) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(
                        phoneDriver,
                        null,
                        sms,
                        null,
                        null
                );
                Toast.makeText(this, "Заявка отправлена", Toast.LENGTH_SHORT).show();

                tvNameCustomer.setText("");
                tvNameCustomer.setHint("Заказчик");

                tvAddressCustomer.setText("");

                edData.setText("");
                edData.setHint("Дата");
                edTime.setText("");
                edTime.setHint("Время");
                tvName.setText("");

                tvSername.setText("");
                tvSername.setHint("Водитель");
                tvPatro.setText("");
                cbMoney.setChecked(false);
                cbKM.setChecked(false);
                phoneDriver ="";

                db.addCustoHistory(dataWork, timeWork, nameDriver , surNameDriver  , patroDriver  ,
                        nameCustomer, addressCustomer  );
            }

        }
    }





}
