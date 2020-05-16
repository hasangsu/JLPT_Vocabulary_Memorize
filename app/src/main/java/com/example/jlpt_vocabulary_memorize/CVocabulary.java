package com.example.jlpt_vocabulary_memorize;

public class CVocabulary {

    private int m_level = 0;
    private int m_tangNo = 0;
    private String m_kanji = "";
    private String m_kana = "";
    private String m_english = "";
    private String m_romaji = "";
    private String m_katakana = "";
    private String m_hiragana = "";

    public int getM_level() {
        return m_level;
    }

    public void setM_level(int m_level) {
        this.m_level = m_level;
    }

    public int getM_tangNo() {
        return m_tangNo;
    }

    public void setM_tangNo(int m_tangNo) {
        this.m_tangNo = m_tangNo;
    }

    public String getM_kanji() {
        return m_kanji;
    }

    public void setM_kanji(String m_kanji) {
        this.m_kanji = m_kanji;
    }

    public String getM_kana() {
        return m_kana;
    }

    public void setM_kana(String m_kana) {
        this.m_kana = m_kana;
    }

    public String getM_english() {
        return m_english;
    }

    public void setM_english(String m_english) {
        this.m_english = m_english;
    }

    public String getM_romaji() {
        return m_romaji;
    }

    public void setM_romaji(String m_romaji) {
        this.m_romaji = m_romaji;
    }

    public String getM_katakana() {
        return m_katakana;
    }

    public void setM_katakana(String m_katakana) {
        this.m_katakana = m_katakana;
    }

    public String getM_hiragana() {
        return m_hiragana;
    }

    public void setM_hiragana(String m_hiragana) {
        this.m_hiragana = m_hiragana;
    }

    public CVocabulary(int level, int no, String kanji, String kana, String english, String romaji, String katakana, String hiragana)
    {
        this.m_level = level;
        this.m_tangNo = no;
        this.m_kanji = kanji;
        this.m_kana = kana;
        this.m_english = english;
        this.m_romaji = romaji;
        this.m_katakana = katakana;
        this.m_hiragana = hiragana;
    }
}
