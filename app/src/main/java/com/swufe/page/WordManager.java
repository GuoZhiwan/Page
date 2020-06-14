package com.swufe.page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public WordManager(Context context) {
        dbHelper = new DBHelper(context,"myword.db",null,1);
        TBNAME = DBHelper.TB_NAME;

    }

    public void add(WordItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curword", item.getCurWord());
        values.put("curmean", item.getCurMean());
        db.insert(TBNAME, null, values);
        db.close();
    }
    public void delete(WordItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(TBNAME, null,null);
        db.close();
    }
    public void addAll(List<WordItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(WordItem item:list) {
            ContentValues values = new ContentValues();
            values.put("curword", item.getCurWord());
            values.put("curmean", item.getCurMean());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public WordItem findById(int id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,"ID=?",new String []{String.valueOf(id)},null,null,null);
        WordItem wordItem=null;
        if(cursor!=null&&cursor.moveToFirst()){
            wordItem=new WordItem();
            wordItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            wordItem.setCurWord(cursor.getString(cursor.getColumnIndex("CURWORD")));
            wordItem.setCurMean(cursor.getString(cursor.getColumnIndex("CURMEAN")));
            cursor.close();


        }
        db.close();
        return wordItem;
    }
    public void update(WordItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curword", item.getCurWord());
        values.put("curmean", item.getCurMean());
        db.update(TBNAME,values,"ID=?",new String[]{String.valueOf(item.getId())});
        db.close();

    }

    public List<WordItem> listall() {
        List<WordItem> wordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if (cursor != null) {
            wordList = new ArrayList<>();
            while (cursor.moveToNext()) {
                WordItem item = new WordItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurWord(cursor.getString(cursor.getColumnIndex("CURWORD")));
                item.setCurMean(cursor.getString(cursor.getColumnIndex("CURMEAN")));
                wordList.add(item);
            }
            cursor.close();
        }
        db.close();
        return  wordList;
    }

}

