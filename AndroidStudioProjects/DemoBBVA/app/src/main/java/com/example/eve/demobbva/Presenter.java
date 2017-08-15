package com.example.eve.demobbva;

import java.util.ArrayList;

/**
 * Created by Eve on 8/15/17.
 */

public class Presenter {

    private static Presenter mInstance;
    private ArrayList<BankInfo> list = null;

    public static Presenter getInstance() {
        if(mInstance == null)
            mInstance = new Presenter();

        return mInstance;
    }

    private Presenter() {
        list = new ArrayList<BankInfo>();
    }

    public ArrayList<BankInfo> getArray() {
        return this.list;
    }

    public void addToArray(BankInfo value) {
        list.add(value);
    }


}
