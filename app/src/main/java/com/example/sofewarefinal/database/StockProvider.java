package com.example.sofewarefinal.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by 立淳 on 2016/6/22.
 *
 */
public class StockProvider extends ContentProvider {

    private StockDBHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int PERSON = 100;
    static final int STOCK = 200;
    static final int OWN = 300;





    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = StockContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, StockContract.PATH_PERSON, PERSON);
        matcher.addURI(authority, StockContract.PATH_STOCK, STOCK);
        matcher.addURI(authority, StockContract.PATH_OWN, OWN);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        mOpenHelper = new StockDBHelper((getContext()));
        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case PERSON:
                return StockContract.PersonEntry.CONTENT_TYPE;
            case STOCK:
                return StockContract.StockEntry.CONTENT_TYPE;
            case OWN :
                return StockContract.OwnEntry.CONTENT_TYPE;
            default :
                throw new UnsupportedOperationException("Unknow uri:" + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case PERSON :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        StockContract.PersonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STOCK :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        StockContract.StockEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case OWN :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        StockContract.OwnEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default :
                throw new UnsupportedOperationException("Unknow uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case PERSON :
                long _id = db.insert(StockContract.PersonEntry.TABLE_NAME, null, values);
                if(_id > 0)
                    returnUri = StockContract.PersonEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Fail ot insert row into " + uri);
                break;
            case STOCK :
                long __id = db.insert(StockContract.StockEntry.TABLE_NAME, null, values);
                if(__id > 0)
                    returnUri = StockContract.StockEntry.buildLocationUri(__id);
                else
                    throw new android.database.SQLException("Fail ot insert row into " + uri);
                break;
            case OWN :
                long ___id = db.insert(StockContract.OwnEntry.TABLE_NAME, null, values);
                if(___id > 0)
                    returnUri = StockContract.OwnEntry.buildLocationUri(___id);
                else
                    throw new android.database.SQLException("Fail ot insert row into " + uri);
                break;
            default :
                throw new UnsupportedOperationException("Unknow uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case PERSON :
                rowsUpdated = db.update(StockContract.PersonEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case STOCK :
                rowsUpdated = db.update(StockContract.StockEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case OWN :
                rowsUpdated = db.update(StockContract.OwnEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default :
                throw new UnsupportedOperationException("Unknow uri:" + uri);

        }

        if(rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match){
            case PERSON :
                rowsDeleted = db.delete(StockContract.PersonEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STOCK :
                rowsDeleted = db.delete(StockContract.StockEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case OWN :
                rowsDeleted = db.delete(StockContract.OwnEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default :
                throw new UnsupportedOperationException("Unknow uri:" + uri);

        }

        if(rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }


}
