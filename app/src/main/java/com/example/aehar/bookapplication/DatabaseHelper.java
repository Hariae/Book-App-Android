package com.example.aehar.bookapplication;

/**
 * Created by aehar on 2/28/2019.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // It's a good idea to always define a log tag like this.
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String BOOKS_TABLE = "books_table";
    private static final String DATABASE_NAME = "books";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String KEY_BOOK = "title";

    // ... and a string array of columns.
    private static final String[] COLUMNS = { KEY_ID, KEY_BOOK };

    // Build the SQL query that creates the table.
    private static final String BOOKS_TABLE_CREATE =
            "CREATE TABLE " + BOOKS_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    // id will auto-increment if no value passed
                    KEY_BOOK + " TEXT );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOKS_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    private void fillDatabaseWithData(SQLiteDatabase db) {
        String[] books = {"The Hobbit", "The Davinci Code", "To Kill a Mocking Bird", "The Lord of the Rings",
                "Eclipse", "Harry Potter", "The Return of the King",
                "The Two Towers", "Breaking Dawn"};
        // Create a container for the data.
        ContentValues values = new ContentValues();
        for (int i=0; i < books.length; i++) {
            // Put column/value pairs into the container.
            // put() overrides existing values.
            values.put(KEY_BOOK, books[i]);
            db.insert(BOOKS_TABLE, null, values);
        }
    }

    public List<Book> query(String searchTerm) {
        String query = "SELECT  * FROM " + BOOKS_TABLE +
                " WHERE " + KEY_BOOK +" LIKE '%" + searchTerm + "%'";
        Cursor cursor = null;
        List<Book> mArrayList = new ArrayList<Book>();

        try{


        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
            Log.d(TAG, "QUERY! " + query);
        cursor = mReadableDB.rawQuery(query, null);


            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                Book item = new Book();

                item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                item.setBook(cursor.getString(cursor.getColumnIndex(KEY_BOOK)));

                mArrayList.add(item);
            }
        cursor.moveToFirst();
        //item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        //item.setBook(cursor.getString(cursor.getColumnIndex(KEY_BOOK)));
        }
        catch(Exception e){
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        }
        finally{
            cursor.close();
            return mArrayList;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        Log.w(ContactsContract.Data.class.getName(),
                "Upgrading database from version " + oldversion + " to "
                        + newversion+ ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + BOOKS_TABLE);
        onCreate(db);
    }
}
