package com.fastword.wordbook.wordbook;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TutorialActivity extends AppCompatActivity {

    final int MAX_PAGE = 3;

    Fragment cur_fragment;

    final int TUTORIAL_FIRST_IMAGE_NUM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ViewPager pager = (ViewPager)findViewById(R.id.tutorial_viewpager);

        pager.setAdapter(new BottomDotAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);


    }

    private class BottomDotAdapter extends FragmentPagerAdapter {
        public BottomDotAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle;
            if(position<0 || MAX_PAGE<=position)
                return null;

            switch (position){
                case 0:
                    bundle = new Bundle();

                    bundle.putInt("R_id", TUTORIAL_FIRST_IMAGE_NUM );

                    cur_fragment = new TutorialFragment();
                    cur_fragment.setArguments(bundle);

                    break;
                case 1:
                    bundle = new Bundle();

                    bundle.putInt("R_id", TUTORIAL_FIRST_IMAGE_NUM + 1 );

                    cur_fragment = new TutorialFragment();
                    cur_fragment.setArguments(bundle);

                    break;
                case 2:

                    bundle = new Bundle();

                    bundle.putInt("R_id", TUTORIAL_FIRST_IMAGE_NUM + 2 );

                    cur_fragment = new TutorialFragment();
                    cur_fragment.setArguments(bundle);

                    break;
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }


}
