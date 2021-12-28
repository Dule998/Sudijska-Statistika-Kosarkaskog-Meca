package Models.Helpers;

import androidx.fragment.app.Fragment;

public class FragmentTabItemModel {
    String title;
    Fragment fragment;

    public FragmentTabItemModel(String title,Fragment fragment){
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }
}//[Class]
