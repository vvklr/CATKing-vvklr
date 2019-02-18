package in.catking.gkapp.fragC;

import android.app.Activity;

public class fragC_quiz_model {
        public String menuName, api;
        public Activity activity;
        public boolean hasChildren, isGroup;

        public fragC_quiz_model(String menuName, boolean isGroup, boolean hasChildren, String api) {

            this.menuName = menuName;
            this.api = api;
            this.isGroup = isGroup;
            this.hasChildren = hasChildren;
        }
}
