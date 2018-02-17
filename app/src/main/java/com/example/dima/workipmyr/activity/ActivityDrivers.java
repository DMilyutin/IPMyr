package com.example.dima.workipmyr.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.dima.workipmyr.DB;
import com.example.dima.workipmyr.R;

public class ActivityDrivers extends AppCompatActivity {
    private static final int CM_DELETE_ID = 1;
    ListView lvDrivers;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers);
        lvDrivers = (ListView) findViewById(R.id.lvDrivers);

        driver();

        lvDrivers.setAdapter(scAdapter);
            //
        registerForContextMenu(lvDrivers);

        lvDrivers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                String nameDriver       = cursor.getString(cursor.getColumnIndex("nameDrivers"));
                String surnameDriver    = cursor.getString(cursor.getColumnIndex("surnameDrivers"));
                String patronymicDriver = cursor.getString(cursor.getColumnIndex("patronymicDrivers"));
                String phoneDriver      = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                Intent intent = new Intent();
                intent.putExtra("nameDr", nameDriver);
                intent.putExtra("surnameDr", surnameDriver);
                intent.putExtra("patroDr", patronymicDriver);
                intent.putExtra("phoneDr", phoneDriver);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        }




    public SimpleCursorAdapter driver(){
        db = new DB(this);
        db.openDB();
        //
        cursor = db.getAllDrivers();
        startManagingCursor(cursor);
        //
        String[] from = new String[]{DB.COLUMN_NAME_DRIVER,
                DB.COLUMN_SURNAME_DRIVER,
                DB.COLUMN_PATRONYMIC_DRIVER,
                DB.COLUMN_CAR_TONNAGE_DRIVER,
                DB.COLUMN_CAR_CUBAGE_DRIVER,
                DB.COLUMN_CAR_PAL_DRIVER};

        int[] to = new int[]{R.id.tvName,
                R.id.tvSurname,
                R.id.tvPatronymic,
                R.id.tvCarTonag,
                R.id.tvCarCubage,
                R.id.tvCarPal};
        //
         scAdapter = new SimpleCursorAdapter(this, R.layout.for_list_drivers, cursor, from, to);
        //
        return scAdapter;


    }






    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }


    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            //
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            //  id
            db.delDriver(acmi.id);
            //
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add :
              AddDriver();
                break;

        }
        return true;
    }




    private void AddDriver() {
        final View dialog = getLayoutInflater().inflate(R.layout.add_driver, null);

        final EditText ETDriverName = (EditText) dialog.findViewById(R.id.ETDriverName);
        final EditText ETDriverSurname = (EditText) dialog.findViewById(R.id.ETDriverSurname);
        final EditText ETDriverPatro = (EditText) dialog.findViewById(R.id.ETDriverPatro);
        final EditText ETDriverTonnage = (EditText) dialog.findViewById(R.id.ETDriverTonnag);
        final EditText ETDriverCubage = (EditText) dialog.findViewById(R.id.ETDriverCubage);
        final EditText ETDriverPal = (EditText) dialog.findViewById(R.id.ETDriverPal);
        final EditText ETDriverPhone = (EditText) dialog.findViewById(R.id.ETDriverPhoneNumber);

        //

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog)
                .setTitle("Добавить водителя")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .setPositiveButton(R.string.alert_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sDriverName = ETDriverName.getText().toString();
                String sDriverSurname = ETDriverSurname.getText().toString();
                String sDriverPatro = ETDriverPatro.getText().toString();
                String sDriverTonnage = ETDriverTonnage.getText().toString();
                String sDriverCubage = ETDriverCubage.getText().toString();
                String sDriverPal = ETDriverPal.getText().toString();
                String sDriverPhone = ETDriverPhone.getText().toString();

                if (!sDriverName.equals("") && !sDriverSurname.equals("") && !sDriverPatro.equals("")
                        && !sDriverTonnage.equals("") && !sDriverCubage.equals("") && !sDriverPhone.equals(""))
                {
                    db.addDriver(sDriverName,sDriverSurname,sDriverPatro,
                             sDriverTonnage, sDriverCubage,sDriverPal ,sDriverPhone);
                    cursor.requery();
                    dialogInterface.dismiss();
            }
        }})
        .create().show();
    }

    //

    protected void onDestroy() {
        super.onDestroy();
        //
        db.close();
    }
}


