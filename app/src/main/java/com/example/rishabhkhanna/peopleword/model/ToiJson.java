package com.example.rishabhkhanna.peopleword.model;

/**
 * Created by rishabhkhanna on 25/12/16.
 */

public class ToiJson {
    String st;
    String uid;
    String upd;
    String dl;
    String hl;
    String imageid;
    String syn;
    String id;
    String dm;
    String tn;
    String su;

    public ToiJson() {
    }

    public ToiJson(String st, String uid, String upd, String dl,
                   String hl, String imageid, String syn, String id, String dm, String tn, String su) {
        this.st = st;
        this.uid = uid;
        this.upd = upd;
        this.dl = dl;
        this.hl = hl;
        this.imageid = imageid;
        this.syn = syn;
        this.id = id;
        this.dm = dm;
        this.tn = tn;
        this.su = su;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getSyn() {
        return syn;
    }

    public void setSyn(String syn) {
        this.syn = syn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }


    /*
    if(view instanceof Button){
    }
     */
}
