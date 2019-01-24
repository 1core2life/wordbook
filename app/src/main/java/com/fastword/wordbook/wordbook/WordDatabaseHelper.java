package com.fastword.wordbook.wordbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WordDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "Word22";

    private static final String TABLE_WORD = "table_word";
    private static final String KEY_ID = "id";
    private static final String KEY_wordtarget = "wordtarget";
    private static final String KEY_wordmeaning = "wordmeaning";

    public WordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_DRINK =
                "CREATE TABLE " + TABLE_WORD + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_wordtarget +" TEXT NOT NULL , "+
                        KEY_wordmeaning +" TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_DRINK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_DRINK =
                "DROP TABLE IF EXISTS " + TABLE_WORD;
        db.execSQL(DROP_TABLE_DRINK);

        onCreate(db);
    }

    public void add(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_wordtarget, word.getWordTarget());
        values.put(KEY_wordmeaning, word.getWordMeaning());

        db.insert(TABLE_WORD, null, values);
        db.close();
    }

    public int getColumnNum(){
        SQLiteDatabase db = this.getWritableDatabase();
        String SELECT_ALL = "SELECT * FROM " + TABLE_WORD;
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        return cursor.getCount();
    }

    public Word getOne(int idx) {
        Word ww = new Word();

        String SELECT_ALL = "SELECT * FROM " + TABLE_WORD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            for(int i = 0; i <= idx ; i++ , cursor.moveToNext()) {
                ww.setWordTarget(cursor.getString(1));
                ww.setWordMeaning(cursor.getString(2));
            }
        }


        return ww;
    }

    public List<Word> getAll() {
        List<Word> drinkList = new ArrayList<Word>();

        String SELECT_ALL = "SELECT * FROM " + TABLE_WORD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setWordTarget(cursor.getString(1));
                word.setWordMeaning(cursor.getString(2));
                drinkList.add(word);

            } while (cursor.moveToNext());
        }

        return drinkList;
    }

    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORD,null,null);
    }

    // Delete Column
    public void deleteOne(String targ) {
        String sql = "delete from " + TABLE_WORD + " where "+ KEY_wordtarget + " = '" + targ + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

}

