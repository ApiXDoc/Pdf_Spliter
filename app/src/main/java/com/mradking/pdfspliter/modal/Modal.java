package com.mradking.pdfspliter.modal;

public class Modal {

    String Name,paths,Date;
    int _id;
    public Modal(){   }

    public Modal(String name, String paths, String date) {
        Name = name;
        this.paths = paths;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
