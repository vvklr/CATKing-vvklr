package in.catking.gkapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentC();
            //fragment = new FragmentA();
        }
        else if (position == 1)
        {
            fragment = new FragmentA();
            //fragment = new FragmentB();
        }
        else if (position == 2)
        {
            fragment = new FragmentB();
            //fragment = new FragmentC();
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "CURRENT AFFAIRS";
            //title = "INDIA GK";
        }
        else if (position == 1)
        {
            title = "INDIA GK";
            //title = "World GK";
        }
        else if (position == 2)
        {
            title = "World GK";
            //title = "GK Quiz";
        }
        return title;
    }
}
