package com.tatteam.languagerealm.entity;

/**
 * Created by Shu on 10/6/2015.
 */
public class LetterEntity {
    public int id;
    public String letter;
    public boolean selected = false;
    public LetterEntity(int id, String letter) {
        this.id = id;
        this.letter = letter;
    }
    public LetterEntity() {
    }
}
