package com.example.sofewarefinal.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 立淳 on 2016/6/22.
 */
public class StockContract {

    public static final String CONTENT_AUTHORITY = "com.example.sofewarefinal";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STOCK = "stock";
    public static final String PATH_PERSON = "person";
    public static final String PATH_OWN = "own";

    public static final class StockEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STOCK).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI + "/" + PATH_STOCK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STOCK;

        //table name
        public static final String TABLE_NAME = "Stock";

        public static final String COLUMN_STOCK_NAME = "stock_name";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DEAL = "deal";
        public static final String COLUMN_BUY_IN = "buy_in";
        public static final String COLUMN_SELL_OUT = "sell_out";
        public static final String COLUMN_UP_DOWN = "up_down";
        public static final String COLUMN_NUMBER_OF_STOCK = "number_of_stock";
        public static final String COLUMN_YESTERDAY_CLOSE = "yesterday_close";
        public static final String COLUMN_OPEN = "open";
        public static final String COLUMN_MAX = "max";
        public static final String COLUMN_MIN = "min";

        public static Uri buildLocationUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    public static final class PersonEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSON).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI + "/" + PATH_PERSON;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        public static final String TABLE_NAME = "person";
        //public static final String COLUMN_STOCK_NAME = "stock_name";
        public static final String COLUMN_OWN_MONEY = "own_money";//own howmany money

        public static Uri buildLocationUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    public static final class OwnEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_OWN).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI + "/" + PATH_OWN;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OWN;

        public static final String TABLE_NAME = "own";

        public static final String COLUMN_STOCK_ID = "stock_id";
        public static final String COLUMN_NUMBER_YOU_OWN = "number_you_own";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
