package com.example.jlpt_vocabulary_memorize;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Map;

public class CVocabularyDBHelper {

    private static final String m_dbName = "vocabulary_db";
    private static final String m_dbTable = "vocabulary_jlpt";
    private static final int m_dbVersion = 1;

    public int m_jlptLevel = 0;
    public int m_jlptNo = 0;

    private COpenHelper m_openHelper;
    private SQLiteDatabase m_db;
    private Context m_context;

    public int getM_jlptLevel() {
        return m_jlptLevel;
    }

    public void setM_jlptLevel(int m_jlptLevel) {
        this.m_jlptLevel = m_jlptLevel;
    }

    public int getM_jlptNo() {
        return m_jlptNo;
    }

    public void setM_jlptNo(int m_jlptNo) {
        this.m_jlptNo = m_jlptNo;
    }

    public void requestInsertVocabulary(Map<String, String> vocabulary_map)
    {
        // Insert DB Function 호출.
        m_openHelper.insertVocabulary(vocabulary_map);
    }

    public ArrayList<CVocabulary> requestCheckAllVocabulary()
    {
        return m_openHelper.checkAllVocabulary();
    }

    public void requestExportDBFile()
    {
    }

    public CVocabularyDBHelper(Context context)
    {
        this.m_context = context;
        this.m_openHelper = new COpenHelper(m_context, m_dbName, null, m_dbVersion);
         m_db= m_openHelper.getWritableDatabase();
    }

    public class COpenHelper extends SQLiteOpenHelper {

        public COpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public COpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        public COpenHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
            super(context, name, version, openParams);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String sql = "CREATE TABLE " + m_dbTable + " (" +
                        "tango_level integer, " +
                        "tango_no integer, " +
                        "kanji varchar(30), " +
                        "kana varchar(30), " +
                        "english varchar(30), " +
                        "romaji varchar(30), " +
                        "katakana varchar(30), " +
                        "hiragana varchar(30));";

                db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // delete and reCreate DB Table.
            db.execSQL("DROP TABLE IF EXISTS " + m_dbTable);
            onCreate(db);
        }

        // insert vocabulary DB Table.
        public void insertVocabulary(Map<String, String> vocabulary_map)
        {
                int tango_level = m_jlptLevel;
                int tango_no = m_jlptNo;
                String kanji = vocabulary_map.get("Kanji");
                String kana = vocabulary_map.get("Kana");
                String english = vocabulary_map.get("English");
                String romaji = vocabulary_map.get("Romaji");
                String katakana = vocabulary_map.get("Katakana");
                String hiragana = vocabulary_map.get("Hiragana");

                String sql = "insert into " + m_dbTable + " values" + "(" + tango_level + "," + tango_no  + ",'" + kanji + "','" + kana + "','" + english + "','" + romaji + "','" + katakana + "','" + hiragana + "');";
                m_jlptNo++;
                m_db.execSQL(sql);
        }

        // check all vocabulary DB Table.
        public ArrayList<CVocabulary> checkAllVocabulary()
        {
            String sql = "SELECT * FROM " + m_dbTable;

            ArrayList<CVocabulary> vocabularyList = new ArrayList<> ();

            Cursor result = m_db.rawQuery(sql, null);
            result.moveToFirst();

            while (!result.isAfterLast())
            {
                int column_count = result.getColumnCount();

                int tango_level = result.getInt(0);
                int tango_no = result.getInt(1);
                String kanji = result.getString(2);
                String kana = result.getString(3);
                String english = result.getString(4);
                String romaji = result.getString(5);
                String katakana = result.getString(6);
                String hiragana = result.getString(7);

                CVocabulary vocabulary = new CVocabulary(tango_level, tango_no, kanji, kana, english, romaji, katakana, hiragana);
                vocabularyList.add(vocabulary);
                result.moveToNext();
            }

            result.close();
            return vocabularyList;
        }
    }
}
