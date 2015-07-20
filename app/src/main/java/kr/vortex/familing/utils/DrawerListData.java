package kr.vortex.familing.utils;

import android.content.Context;

/**
 * Created by kotohana5706 on 15. 7. 13.
 */
public class DrawerListData {
    private int icon;
    private String name;

    public DrawerListData(Context context, int icon_, String name_) {
        icon = icon_;
        name = name_;
    }
    public int getIcon(){
        return icon;
    }

    public String getname(){
        return name;
    }
}
