package com.example.jlpt_vocabulary_memorize;

import android.content.res.Resources;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CVocabularyParser {

    void vocabulary_file_parsing(Resources resources, CVocabularyDBHelper vocabularyDBHelper)
    {
        String TAG = "XML";

        Log.i(TAG, "parser()");

        InputStream inputStream = resources.openRawResource(R.raw.jlpt1);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        XmlPullParserFactory factory = null;
        XmlPullParser xmlParser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            xmlParser = factory.newPullParser();
            xmlParser.setInput(reader);

            int eventType = xmlParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String parser_test = xmlParser.getName();

                        // XML 파싱, XML Start Tag "Word"인지 판단.
                        if (parser_test.equals("Word"))
                        {
                            // XML 파싱, Tag "Word"의 속성태그 Count 검사.
                            int attribute_count = xmlParser.getAttributeCount();
                            if (attribute_count == 0)
                            {
                                break;
                            }

                            // XML 파싱, Tag "Word"의 속성태그 파싱.
                            Map<String, String> vocabulary_map = new HashMap<String, String>();
                            for (int index = 0; index < attribute_count; index++)
                            {
                                String attribute_name = xmlParser.getAttributeName(index);
                                String attribute_value = xmlParser.getAttributeValue(index);

                                vocabulary_map.put(attribute_name, attribute_value);
                            }

                            vocabulary_parsing(vocabularyDBHelper, vocabulary_map);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                try {
                    eventType = xmlParser.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally{
            try{
                if(reader !=null) reader.close();
                if(inputStreamReader !=null) inputStreamReader.close();
                if(inputStream !=null) inputStream.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
    }

    void vocabulary_parsing(CVocabularyDBHelper vocabularyDBHelper, Map<String, String> vocabulary_map)
    {
        // 파싱한 데이터 결과로 Insert DB.
        String kanji = vocabulary_map.get("Kanji");
        String kana = vocabulary_map.get("Kana");
        String english = vocabulary_map.get("English");
        String romaji = vocabulary_map.get("Romaji");
        String katakana = vocabulary_map.get("Katakana");
        String hiragana = vocabulary_map.get("Hiragana");

        vocabularyDBHelper.requestInsertVocabulary(vocabulary_map);
    }
}
