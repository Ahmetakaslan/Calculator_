package com.asn.newcalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Gecmis implements Serializable {
    String  date;
    String islem;
public static ArrayList<Gecmis> gecmis=new ArrayList();
    public Gecmis(String date,String islem){
        this.islem=islem;
        this.date=date;
        gecmis.add(this);
    }
}
