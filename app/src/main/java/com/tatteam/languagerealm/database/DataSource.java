package com.tatteam.languagerealm.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.entity.LetterEntity;
import com.tatteam.languagerealm.entity.PhraseEntity;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.app_common.sqlite.BaseDataSource;


/**
 * Created by ThanhNH on 2/1/2015.
 */
public class DataSource extends BaseDataSource {


    public static List<LetterEntity> getLetters(String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT distinct(substr(letter,1,1)) FROM " + table + " ORDER by letter", null);
        List<LetterEntity> listLetter = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LetterEntity letterEntity = new LetterEntity();
            letterEntity.letter = cursor.getString(0);
            listLetter.add(letterEntity);
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return listLetter;
    }

    public static List<LetterEntity> getAllLetters() {
        SQLiteDatabase sqLiteDatabase = openConnection();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  distinct(substr(letter,1,1))  FROM  idioms  " +
                " UNION ALL\n" +
                " SELECT distinct(substr(letter,1,1)) FROM  slang \n" +
                " UNION ALL  " +
                " SELECT distinct(substr(letter,1,1))  FROM  proverbs;", null);
        List<LetterEntity> listLetter = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LetterEntity letterEntity = new LetterEntity();
            letterEntity.letter = cursor.getString(0);
            if (!isHadLetterInList(listLetter, letterEntity.letter)) {
                listLetter.add(letterEntity);
            }
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return listLetter;
    }

    public static boolean isHadLetterInList(List<LetterEntity> listLetter, String letter) {
        for (int i = 0; i < listLetter.size(); i++) {
            if (letter == listLetter.get(i).letter) return true;
        }
        return false;
    }

    public static List<PhraseEntity> getPhraseByLetter(String letter, String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        String s = letter + "%";
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + table + " where letter LIKE ? ", new String[]{s});
        List<PhraseEntity> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhraseEntity phrase = new PhraseEntity();
            phrase.kind_ID = R.string.slang;
            phrase.letter = cursor.getString(1);
            phrase.phrase = cursor.getString(2);
            phrase.explanation = cursor.getString(3);
            phrase.isFavorite = cursor.getInt(4);
            phrase.isRecent = cursor.getInt(5);
            list.add(phrase);
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return list;
    }


    public static PhraseEntity getOnePhrase(String pharse, String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + table + " where phrase = ? limit 1", new String[]{pharse});
        PhraseEntity phrase = new PhraseEntity();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            phrase.kind_ID = R.string.slang;
            phrase.letter = cursor.getString(1);
            phrase.phrase = cursor.getString(2);
            phrase.explanation = cursor.getString(3);
            phrase.isFavorite = cursor.getInt(4);
            phrase.isRecent = cursor.getInt(5);
            cursor.close();
        }
        closeConnection();
        return phrase;
    }


    public static void changeFavoritePhrase(String phrase, String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        PhraseEntity entity = getOnePhrase(phrase, table);
        int favorite = entity.isFavorite;
        if (favorite == 1) {
            Cursor cursor = sqLiteDatabase.rawQuery("UPDATE " + table + " SET isFavorite= 0 WHERE phrase = ?", new String[]{phrase});
            cursor.moveToFirst();
            cursor.close();
        } else {
            Cursor cursor = sqLiteDatabase.rawQuery("UPDATE " + table + " SET isFavorite= 1 WHERE phrase = ?", new String[]{phrase});
            cursor.moveToFirst();
            cursor.close();
        }
        closeConnection();
    }


    public static List<PhraseEntity> getListFavorite() {
        SQLiteDatabase sqLiteDatabase = openConnection();
        Cursor cursor = sqLiteDatabase.rawQuery("    SELECT  *\n" +
                "    FROM  slang\n" +
                "    WHERE isFavorite>0\n" +
                "    UNION ALL\n" +
                "    SELECT *\n" +
                "    FROM  idioms\n" +
                "    WHERE isFavorite>0\n" +
                "    UNION ALL\n" +
                "    SELECT *\n" +
                "    FROM  proverbs\n" +
                "    WHERE isFavorite>0\n" +
                "    order by letter", null);
        List<PhraseEntity> list = new ArrayList<>();
        if (cursor.getCount() == 0) return list;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhraseEntity phrase = new PhraseEntity();
            phrase.letter = cursor.getString(1);
            phrase.phrase = cursor.getString(2);
            phrase.explanation = cursor.getString(3);
            phrase.isFavorite = cursor.getInt(4);
            phrase.isRecent = cursor.getInt(5);
            phrase.setKind_ID(cursor.getInt(6));
            list.add(phrase);
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return list;
    }

    public static List<PhraseEntity> getListSearchResutl(String query, String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        String sql = "";
        switch (table) {

            case "slang":
                sql = "    SELECT  *\n" +
                        "    FROM  slang\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  idioms\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  proverbs\n" +
                        "    WHERE phrase like ? " +
                        "    order by letter";
                break;
            case "idioms":
                sql = "    SELECT  *\n" +
                        "    FROM  idioms\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  proverbs\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  slang\n" +
                        "    WHERE phrase like ? " +
                        "    order by letter";
                break;
            case "proverbs":
                sql = "    SELECT  *\n" +
                        "    FROM  proverbs\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  slang\n" +
                        "    WHERE phrase like ? " +
                        "    UNION ALL\n" +
                        "    SELECT *\n" +
                        "    FROM  idioms\n" +
                        "    WHERE phrase like ? " +
                        "    order by letter";
                break;
        }
        sql = sql + "";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{query + "%", query + "%", query + "%"});
        List<PhraseEntity> list = new ArrayList<>();
        if (cursor.getCount() == 0) return list;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhraseEntity phrase = new PhraseEntity();
            phrase.letter = cursor.getString(1);
            phrase.phrase = cursor.getString(2);
            phrase.explanation = cursor.getString(3);
            phrase.isFavorite = cursor.getInt(4);
            phrase.isRecent = cursor.getInt(5);
            phrase.setKind_ID(cursor.getInt(6));
            phrase.setSQLTable(cursor.getInt(6));
            list.add(phrase);
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return list;
    }

    public static List<PhraseEntity> getListRecent(int MAX_COUNT) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  *\n" +
                " FROM  slang WHERE isRecent>0\n" +
                " UNION ALL\n" +
                " SELECT *\n" +
                " FROM  idioms\n" +
                " WHERE isRecent>0\n" +
                " UNION ALL\n" +
                " SELECT *\n" +
                " FROM  proverbs\n" +
                " WHERE isRecent>0\n" +
                "order by isRecent desc limit ?", new String[]{MAX_COUNT + ""});
        List<PhraseEntity> list = new ArrayList<>();
        if (cursor.getCount() == 0) return list;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhraseEntity phrase = new PhraseEntity();
            phrase.letter = cursor.getString(1);
            phrase.phrase = cursor.getString(2);
            phrase.explanation = cursor.getString(3);
            phrase.isFavorite = cursor.getInt(4);
            phrase.isRecent = cursor.getInt(5);
            phrase.setKind_ID(cursor.getInt(6));
            list.add(phrase);
            cursor.moveToNext();
        }
        cursor.close();
        closeConnection();
        return list;
    }

    public static void updateRecent(String phrase, String table) {
        SQLiteDatabase sqLiteDatabase = openConnection();
        int newRecent = getMaxRecent() + 1;
        String value = newRecent + "";
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE " + table + " SET isRecent= ? WHERE phrase =?", new String[]{value, phrase});
        cursor.moveToFirst();
        cursor.close();
        closeConnection();

    }

    public static int getMaxRecent() {
        SQLiteDatabase sqLiteDatabase = openConnection();
        int max;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT  *\n" +
                " FROM  slang WHERE isRecent>0\n" +
                " UNION ALL\n" +
                " SELECT *\n" +
                " FROM  idioms\n" +
                " WHERE isRecent>0\n" +
                " UNION ALL\n" +
                " SELECT *\n" +
                " FROM  proverbs\n" +
                " WHERE isRecent>0\n" +
                "order by isRecent desc limit 1\n", null);
        if (cursor.getCount() == 0) return 0;
        cursor.moveToFirst();

        max = cursor.getInt(5);

        cursor.close();
        closeConnection();
        return max;

    }
}
