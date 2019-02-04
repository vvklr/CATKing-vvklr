package in.catking.catking;

import android.app.Activity;

public class MenuModel {

    public String menuName;
    public Activity activity;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, Activity activity) {

        this.menuName = menuName;
        this.activity = activity;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
