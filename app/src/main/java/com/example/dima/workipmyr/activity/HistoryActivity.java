package com.example.dima.workipmyr.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.dima.workipmyr.DB;
import com.example.dima.workipmyr.R;

public class HistoryActivity extends AppCompatActivity {

    ListView listViewHistory;
    DB db;
    Cursor cursor;
    SimpleCursorAdapter scAdapter;

    private static final int CM_DELETE_ID = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        listViewHistory = (ListView)findViewById(R.id.LVHistori);

        db = new DB(this);
        db.openDB();

        cursor = db.getAllHistory();

        startManagingCursor(cursor);

        String[] from = new String[] { DB.COLUMN_DATA_HISTORY, DB.COLUMN_SURNAME_DRIVER_HISTORY,
                DB.COLUMN_NAME_DRIVER_HISTORY, DB.COLUMN_PATRONYMIC_DRIVER_HISTORY,
                DB.COLUMN_NAME_CUSTOMER_HISTORY, DB.COLUMN_ADDRESS_CUSTOMER_HISTORY,
                DB.COLUMN_TIME_HISTORY};
        int[] to = new int[] { R.id.tvDataHistory, R.id.tvSurnameDrHistory,
                R.id.tvNameDrHistory, R.id.tvPatronymicDrHistory,
                R.id.tvCustomerHistory, R.id.tvCustomerAddressHistory, R.id.tvTimeHistory};

        scAdapter = new SimpleCursorAdapter(this, R.layout.for_list_history, cursor, from, to);
        //

        listViewHistory.setAdapter(scAdapter);

        registerForContextMenu(listViewHistory);


        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                db.getHistoryById(id);
            }
        });
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
            db.delHistory(acmi.id);
            //
            cursor.requery();
            return true;
        }

        return super.onContextItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
