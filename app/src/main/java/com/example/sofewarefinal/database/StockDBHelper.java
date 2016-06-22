package com.example.sofewarefinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 立淳 on 2016/6/22.
 */
public class StockDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "stock.db";

    public StockDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_STOCK_TABLE = "CREATE TABLE " + StockContract.StockEntry.TABLE_NAME + " (" +
                StockContract.StockEntry._ID + " INTEGER PRIMARY KEY, " +
                StockContract.StockEntry.COLUMN_STOCK_NAME + " TEXT UNIQUE NOT NULL, " +
                StockContract.StockEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                StockContract.StockEntry.COLUMN_DEAL + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_BUY_IN + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_SELL_OUT + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_UP_DOWN + " TEXT NOT NULL, " +
                StockContract.StockEntry.COLUMN_NUMBER_OF_STOCK + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_YESTERDAY_CLOSE + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_OPEN + " REAL NOT NULL, " +
                StockContract.StockEntry.COLUMN_MAX + " REAL NOT NUL L, " +
                StockContract.StockEntry.COLUMN_MIN + " REAL NOT NULL " +
                ";)" ;

        final String SQL_CREATE_PERSON_TABLE ="CREATE TABLE " + StockContract.PersonEntry.TABLE_NAME + " (" +
                //StockContract.PersonEntry.COLUMN_STOCK_NAME + "TEXT NOT NULL," +
                StockContract.PersonEntry.COLUMN_OWN_MONEY + " REAL NOT NULL );" ;
                //"FOREIGN KEY (" + StockContract.PersonEntry.COLUMN_STOCK_NAME + ") REFERENCES "+
                //StockContract.StockEntry.TABLE_NAME + " (" + StockContract.StockEntry.COLUMN_STOCK_NAME + ");" ;

        final String SQL_CREATE_OWN_TABLE = "CREATE TABLE " + StockContract.OwnEntry.TABLE_NAME + " (" +
                StockContract.OwnEntry.COLUMN_STOCK_ID + " TEXT NOT NULL, " +
                StockContract.OwnEntry.COLUMN_NUMBER_YOU_OWN + " REAL NOT NULL, "+
                "FOREIGN EKY (" + StockContract.OwnEntry.COLUMN_STOCK_ID + ") REFERENCES " +
                StockContract.StockEntry.TABLE_NAME + "  " + StockContract.StockEntry._ID + "); ";


        db.execSQL(SQL_CREATE_STOCK_TABLE);
        db.execSQL(SQL_CREATE_PERSON_TABLE);
        db.execSQL(SQL_CREATE_OWN_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + StockContract.StockEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + StockContract.PersonEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + StockContract.OwnEntry.TABLE_NAME);
        onCreate(db);
    }
}
