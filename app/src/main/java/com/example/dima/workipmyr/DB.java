package com.example.dima.workipmyr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB {
    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;


    //

    private static final String DB_NAME = "MyrDB";
    private static final int DB_VERSION = 1;

    // <Driver`s table>
    private static final String DB_TABLE_DRIVERS = "DriverTable";

    public static final String COLUMN_ID_DRIVER = "_id";
    public static final String COLUMN_NAME_DRIVER = "nameDrivers";
    public static final String COLUMN_SURNAME_DRIVER = "surnameDrivers";
    public static final String COLUMN_PATRONYMIC_DRIVER = "patronymicDrivers";
    public static final String COLUMN_CAR_TONNAGE_DRIVER = "carTonnage";
    public static final String COLUMN_CAR_CUBAGE_DRIVER = "carCubage";
    public static final String COLUMN_CAR_PAL_DRIVER = "carPal";
    public static final String COLUMN_PHONE_NUMBER_DRIVER = "phoneNumber";


    private static final String CREATE_TABLE_DRIVERS = "create table " + DB_TABLE_DRIVERS + "("
            + COLUMN_ID_DRIVER + " integer primary key autoincrement, "
            + COLUMN_NAME_DRIVER + " text, "
            + COLUMN_SURNAME_DRIVER + " text, "
            + COLUMN_PATRONYMIC_DRIVER + " text, "
            + COLUMN_CAR_TONNAGE_DRIVER + " text, "
            + COLUMN_CAR_CUBAGE_DRIVER + " text, "
            + COLUMN_CAR_PAL_DRIVER + " text, "
            + COLUMN_PHONE_NUMBER_DRIVER + " text" + ");";
    // </Driver`s table>
    //
    //<Customer`s table>
    private static final String DB_TABLE_CUSTOMER = "CustomerTable";

    public static final String COLUMN_ID_CUSTOMER = "_id";
    public static final String COLUMN_NAME_CUSTOMER = "nameCustomer";


    private static final String CREATE_TABLE_CUSTOMER = "create table "+ DB_TABLE_CUSTOMER + "("
            + COLUMN_ID_CUSTOMER + " integer primary key autoincrement, "
            + COLUMN_NAME_CUSTOMER + " text" + ");";

            //<Customer`s address table>
            private static final String DB_TABLE_ADDRESS_CUSTOMER = "CustomerAddressTable";

            public static final String COLUMN_ID_ADDRESS_CUSTOMER = "_id";
            public static final String COLUMN_CNAME_CUSTOMER = "cname";
            public static final String COLUMN_ADDRESS_CUSTOMER = "address";

            private static final String CREATE_TABLE_ADDRESS_CUSTOMER = "create table "
                    +DB_TABLE_ADDRESS_CUSTOMER +"("
                    +COLUMN_ID_ADDRESS_CUSTOMER + " integer primary key autoincrement, "
                    +COLUMN_CNAME_CUSTOMER + " text, "
                    +COLUMN_ADDRESS_CUSTOMER + " text" + ");";
            //</Customer`s address table>
    //</Customer`s table>

    //<History table>
    public static final String DB_TABLE_HISTORY = "HistoryTable";
    public static final String COLUMN_ID_HISTORY = "_id";
    public static final String COLUMN_DATA_HISTORY = "dataHistory";
    public static final String COLUMN_TIME_HISTORY = "timeHistory";
    public static final String COLUMN_NAME_DRIVER_HISTORY = "nameDriverHistory";
    public static final String COLUMN_SURNAME_DRIVER_HISTORY = "surnameDriverHistory";
    public static final String COLUMN_PATRONYMIC_DRIVER_HISTORY = "patroDriverHistory";
    public static final String COLUMN_NAME_CUSTOMER_HISTORY = "nameCustomerHistory";
    public static final String COLUMN_ADDRESS_CUSTOMER_HISTORY = "addressCustomerHistory";

    private static final String CREATE_TABLE_HISTORY = "create table " + DB_TABLE_HISTORY + "("
            + COLUMN_ID_HISTORY + " integer primary key autoincrement, "
            + COLUMN_DATA_HISTORY + " text, "
            + COLUMN_TIME_HISTORY + " text, "
            + COLUMN_NAME_DRIVER_HISTORY + " text, "
            + COLUMN_SURNAME_DRIVER_HISTORY + " text, "
            + COLUMN_PATRONYMIC_DRIVER_HISTORY + " text, "
            + COLUMN_NAME_CUSTOMER_HISTORY + " text, "
            + COLUMN_ADDRESS_CUSTOMER_HISTORY + " text" + ");";
    //</History table>


    public DB(Context ctx) {
        mCtx = ctx;
    }



    //<Driver`s metods>

    public void openDB() {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    //

    public Cursor getAllDrivers() {
        return mDB.query(DB_TABLE_DRIVERS, null, null, null, null, null, "surnameDrivers ASC");
    }

    //

    public void addDriver(String txt_name, String txt_surname, String txt_patronymic,
                          String txt_car_tonnage, String txt_car_cubage, String txt_car_pal, String txt_phone_namber) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_DRIVER, txt_name);
        cv.put(COLUMN_SURNAME_DRIVER, txt_surname);
        cv.put(COLUMN_PATRONYMIC_DRIVER, txt_patronymic);
        cv.put(COLUMN_CAR_TONNAGE_DRIVER, txt_car_tonnage);
        cv.put(COLUMN_CAR_CUBAGE_DRIVER, txt_car_cubage);
        cv.put(COLUMN_CAR_PAL_DRIVER, txt_car_pal);
        cv.put(COLUMN_PHONE_NUMBER_DRIVER, txt_phone_namber);

        mDB.insert(DB_TABLE_DRIVERS, null, cv);
    }

    //

    public void delDriver(long id) {
        mDB.delete(DB_TABLE_DRIVERS, COLUMN_ID_DRIVER + " = " + id, null);
    }
    //</Driver`s metods>



    //<Customer`s metods

    public  void addCustomer(String castNameAdd){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_CUSTOMER, castNameAdd);
        mDB.insert(DB_TABLE_CUSTOMER, null, cv);
    }

    public Cursor getAllCustomer(){
        return mDB.query(DB_TABLE_CUSTOMER, null, null, null, null, null, "nameCustomer ASC");
    }

    public void delCustomer(long id){
        mDB.delete(DB_TABLE_CUSTOMER, COLUMN_ID_CUSTOMER + " = " + id, null );
    }

    //
    //

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }


    // <Customer`s address metods>

    public  Cursor getCustomerAddress(String castoName){
        return mDB.query(DB_TABLE_ADDRESS_CUSTOMER,
                null,
                COLUMN_CNAME_CUSTOMER+"=?",
                new String[]{castoName},
                null,
                null,null);



    }


    public  void addCustomerAddress( String castoName, String castAddressAdd){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CNAME_CUSTOMER, castoName);
        cv.put(COLUMN_ADDRESS_CUSTOMER, castAddressAdd);
        mDB.insert(DB_TABLE_ADDRESS_CUSTOMER, null, cv);
    }

    public void delAddressCustomer(long id){
        mDB.delete(DB_TABLE_ADDRESS_CUSTOMER, COLUMN_ID_ADDRESS_CUSTOMER + " = " + id, null );
    }


    //</Customer`s address metods>
    //</Customer`s metods>

    //<History`s metods

    public  Cursor getAllHistory(){
        return mDB.query(DB_TABLE_HISTORY, null, null, null, null, null,null);
    }

    public Cursor getHistoryById(String id){
        return mDB.query(DB_TABLE_HISTORY,
                null,
                COLUMN_ID_HISTORY+"=?",
                new String[]{id},
                null,
                null,null);
    }

    public void delHistory(long id){
        mDB.delete(DB_TABLE_HISTORY, COLUMN_ID_HISTORY + " = " + id, null );
    }

    public void addCustoHistory(String data, String time, String nameDriver,
                                String surNameDriver, String patroDrivers,
                                String nameCustomer, String addressCustomer){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA_HISTORY, data);
        values.put(COLUMN_TIME_HISTORY, time);
        values.put(COLUMN_NAME_DRIVER_HISTORY, nameDriver);
        values.put(COLUMN_SURNAME_DRIVER_HISTORY, surNameDriver);
        values.put(COLUMN_PATRONYMIC_DRIVER_HISTORY, patroDrivers);
        values.put(COLUMN_NAME_CUSTOMER_HISTORY, nameCustomer);
        values.put(COLUMN_ADDRESS_CUSTOMER_HISTORY, addressCustomer );
        mDB.insert(DB_TABLE_HISTORY, null, values);


    }
    //</History`s metod>

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }


        // Create database
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_DRIVERS);
            db.execSQL(CREATE_TABLE_CUSTOMER);
            db.execSQL(CREATE_TABLE_ADDRESS_CUSTOMER);
            db.execSQL(CREATE_TABLE_HISTORY);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}
