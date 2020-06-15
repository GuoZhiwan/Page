package com.swufe.page;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB2Helper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    public static final String DB_NAME="fgword.db";
    public static final String TB_NAME="fg_words";

    public DB2Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURWORD TEXT,CURMEAN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
