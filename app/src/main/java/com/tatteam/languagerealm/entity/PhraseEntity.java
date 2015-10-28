package com.tatteam.languagerealm.entity;

import com.tatteam.languagerealm.R;

/**
 * Created by Shu on 10/6/2015.
 */
public class PhraseEntity {
    public int kind_ID;
    public String phrase;
    public String explanation;
    public String letter;
    public int isFavorite = 0;
    public int isRecent = 0;
    public boolean isHeader = false;
    public float index;
    public String sqlTableName="";
    public void setKind_ID(int i) {
        if (i == 1) kind_ID = R.string.slang;
        else if (i == 2) kind_ID = R.string.idiom;
        else kind_ID = R.string.proverb;
    }



    public void setSQLTable(int i) {
        if (i == 1) sqlTableName = "slang";
        else if (i == 2) sqlTableName = "idioms";
        else sqlTableName = "proverbs";
    }
}
