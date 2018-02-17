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

import com.example.dima.workipmyr.CustomerDetail;
import com.example.dima.workipmyr.DB;
import com.example.dima.workipmyr.R;

public class ActivityCustomer extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    ListView lvCustomer;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    String nameCU;
    String addressCU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers);

        db = new DB(this);
        db.openDB();

        cursor = db.getAllCustomer();

        startManagingCursor(cursor);

        String[] from = new String[] { DB.COLUMN_NAME_CUSTOMER};
        int[] to = new int[] { R.id.tvCustomerName};

        scAdapter = new SimpleCursorAdapter(this, R.layout.for_list_customer, cursor, from, to);
        //
        lvCustomer = (ListView) findViewById(R.id.LVCustomers);
        lvCustomer.setAdapter(scAdapter);

        //
        registerForContextMenu(lvCustomer);

       lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               cursor.moveToPosition(position);
               String tx = cursor.getString(cursor.getColumnIndex("nameCustomer"));
               Intent intent = new Intent(ActivityCustomer.this , CustomerDetail.class) ;
               intent.putExtra("nameCusto", tx );
               startActivityForResult(intent, 1);
               db.close();


           }
       });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (resultCode == RESULT_OK){
             String nameCU1    = data.getStringExtra("nameCu");
             String addressCU1 = data.getStringExtra("addressCu");

            Intent intentForWork = new Intent();
            intentForWork.putExtra("nameCU1", nameCU1);
            intentForWork.putExtra("addressCU1", addressCU1);
            setResult(RESULT_OK, intentForWork);
            finish();

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == CM_DELETE_ID) {
            //
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            //  id
            db.delCustomer(acmi.id);
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
                addCustomer();
                break;
        }
        return true;
    }

    private void addCustomer() {

        final View dialog = getLayoutInflater().inflate(R.layout.add_customer, null);

        final EditText etCustomerName = (EditText) dialog.findViewById(R.id.edNameCustomer);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog)
                .setTitle("Добавить заказчика")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                }).setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String customerName = etCustomerName.getText().toString();

                if(customerName != null) {db.addCustomer(customerName);

                    dialogInterface.dismiss();
                    cursor.requery();
                }
            }
        }).create().show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        db.openDB();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        db.openDB();
        cursor = db.getAllCustomer();

        startManagingCursor(cursor);
    }



    protected void onDestroy() {
        super.onDestroy();
        //
        db.close();
    }

}
