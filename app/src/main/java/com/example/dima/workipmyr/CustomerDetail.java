package com.example.dima.workipmyr;

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
import android.widget.TextView;

public class CustomerDetail extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    TextView tv;
    ListView lvCustomerAddress;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    String custoName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);



        tv = (TextView) findViewById(R.id.tvCustName);

        final Intent intent = getIntent();
        if (intent != null) {
            custoName = intent.getStringExtra("nameCusto");
            tv.setText(custoName);
        }


   final String z = custoName;
   db = new DB(this);
   db.openDB();

   cursor = db.getCustomerAddress(z);
   startManagingCursor(cursor);

   String[] from = new String[] {DB.COLUMN_ADDRESS_CUSTOMER};
   int[] to = new int[]{R.id.tvCustomerAddress};

   scAdapter = new SimpleCursorAdapter(this, R.layout.for_list_address, cursor, from, to );

   lvCustomerAddress = (ListView ) findViewById(R.id.LVAddressCustomer) ;
   lvCustomerAddress.setAdapter(scAdapter);

    registerForContextMenu(lvCustomerAddress);


        lvCustomerAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                String address1 = cursor.getString(cursor.getColumnIndex("address"));
                Intent inte = new Intent();
                inte.putExtra("addressCu", address1);
                inte.putExtra("nameCu",custoName );
                setResult(RESULT_OK, inte);
                finish();
            }
        });
   }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить" );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == CM_DELETE_ID){
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        db.delAddressCustomer(acmi.id);
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
            case R.id.add:
                addCustomerAddress();
        }
        return true;
    }
//


    private void addCustomerAddress() {
        final View dialog = getLayoutInflater().inflate(R.layout.add_address_customer, null);
        final String z1 = custoName;
        final EditText etCustomerAddress = (EditText) dialog.findViewById(R.id.edAddressCustomer);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialog)
                .setTitle("Добавить адрес заказчика")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                }).setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String customerAddress = etCustomerAddress.getText().toString();

                if (customerAddress != null) {
                    db.addCustomerAddress(z1, customerAddress);
                    cursor.requery();
                    dialogInterface.dismiss();
                }
            }
        }).create().show();

    }



  protected void onDestroy() {
      super.onDestroy();
      //
      db.close();
  }
}
