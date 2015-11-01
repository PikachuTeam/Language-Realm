package com.tatteam.languagerealm.entity;

/**
 * Created by Shu on 10/9/2015.
 */
public class NavEntity {
    public int tittle_id;
    public int icon_id;
    public boolean selected = false;
    public NavEntity(int tittle_id, int icon_id, boolean selected) {
        this.tittle_id = tittle_id;
        this.icon_id = icon_id;
        this.selected = selected;
    }
}
