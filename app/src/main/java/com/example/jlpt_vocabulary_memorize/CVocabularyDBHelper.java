package com.example.jlpt_vocabulary_memorize;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class CVocabularyDBHelper {

    private static final String m_dbName = "vocabulary_db";
    private static final String m_dbTable = "vocabulary_jlpt1";
    private static final int m_dbVersion = 1;

    private COpenHelper m_openHelper;
    private SQLiteDatabase m_db;
    private Context m_context;

    public void requestInsertVocabulary(Map<String, String> vocabulary_map)
    {
        // Insert DB Function 호출.
        m_openHelper.insertVocabulary(vocabulary_map);
    }

    public ArrayList<CVocabulary> requestCheckAllVocabulary()
    {
        return m_openHelper.checkAllVocabulary();
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
            // create DB Table.
            String sql = "CREATE TABLE " + m_dbTable + " (" +
                         //"indexId integer primary key autoincrement, " +
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

        public void createDB1()
        {
            int a  = 1;

        }
        // insert vocabulary DB Table.
        public void insertVocabulary(Map<String, String> vocabulary_map)
        {
                String kanji = vocabulary_map.get("Kanji");
                String kana = vocabulary_map.get("Kana");
                String english = vocabulary_map.get("English");
                String romaji = vocabulary_map.get("Romaji");
                String katakana = vocabulary_map.get("Katakana");
                String hiragana = vocabulary_map.get("Hiragana");

                String sql = "insert into " + m_dbTable + " values" + "('" + kanji + "','" + kana + "','" + english + "','" + romaji + "','" + katakana + "','" + hiragana + "');";
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

                String kanji = result.getString(0);
                String kana = result.getString(1);
                String english = result.getString(2);
                String romaji = result.getString(3);
                String katakana = result.getString(4);
                String hiragana = result.getString(5);

                CVocabulary vocabulary = new CVocabulary(0, kanji, kana, english, romaji, katakana, hiragana);
                vocabularyList.add(vocabulary);
                result.moveToNext();
            }

            result.close();
            return vocabularyList;
        }
    }
}
