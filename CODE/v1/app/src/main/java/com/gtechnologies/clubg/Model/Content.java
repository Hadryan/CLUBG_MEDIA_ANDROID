package com.gtechnologies.clubg.Model;

import java.io.Serializable;

/**
 * Created by Hp on 4/15/2018.
 */

public class Content implements Serializable {

    String brief;
    String filename;
    String sticky;
    int id;
    String tag;
    String title;
    String free;
    String idHide;
    int idCategory;

    public Content() {
    }

    public Content(String brief, String filename, String sticky, int id, String tag, String title, String free, String idHide, int idCategory) {
        this.brief = brief;
        this.filename = filename;
        this.sticky = sticky;
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.free = free;
        this.idHide = idHide;
        this.idCategory = idCategory;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getIdHide() {
        return idHide;
    }

    public void setIdHide(String idHide) {
        this.idHide = idHide;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
