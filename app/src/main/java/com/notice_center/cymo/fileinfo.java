package com.notice_center.cymo;


public class fileinfo {
    String filename;
    String fileurl;
    String date;

    String spinner;

    public fileinfo() {
    }

    public fileinfo(String filename, String fileurl,String date,String spinner) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.date = date;
        this.spinner = spinner;
    }



    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {

    }

    public  String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public  String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public  String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
