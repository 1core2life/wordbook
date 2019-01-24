package com.fastword.wordbook.wordbook;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LockScreenActivity extends AppCompatActivity {


    TextView date, time, meridiem;

    ConstraintLayout lay;

    ProgressHandler handler;
    final WordDatabaseHelper db = new WordDatabaseHelper(this);

    SimpleDateFormat dateForm = new SimpleDateFormat("MM 월 dd일");
    SimpleDateFormat timeForm = new SimpleDateFormat("hh:mm");
    SimpleDateFormat meridiemForm = new SimpleDateFormat("aaa");
    String dateFormSt, timeFormSt, meridiemSt;

    ImageView power;

    ViewPager vp;
    int pageNum = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_lock_screen);


        lay = findViewById(R.id.layout);

        lay.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                finish();
            }
            public void onSwipeRight() {
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                finish();
            }

        });

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        meridiem = findViewById(R.id.meridiem);

        handler = new ProgressHandler();

        pageNum = db.getColumnNum();
        vp = findViewById(R.id.main_pager);

        vp.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        power = findViewById(R.id.power);
        power.setOnClickListener(listener);
        runTime();


    }



    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

            finish();
        }

    };


    @Override
    public void onResume(){
        super.onResume();

        switch(MainActivity.colorNum){
            case 0:
                lay.setBackground(getResources().getDrawable( R.drawable.background_gray ));
                break;

            case 1:
                lay.setBackground(getResources().getDrawable( R.drawable.background_deepblue ));
                break;

            case 2:
                lay.setBackground(getResources().getDrawable( R.drawable.background_coldevening ));
                break;

            case 3:
                lay.setBackground(getResources().getDrawable( R.drawable.background_newyork ));
                break;

            default :


        }
    }

    public void runTime(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        dateFormSt = dateForm.format(new Date(System.currentTimeMillis()));
                        timeFormSt = timeForm.format(new Date(System.currentTimeMillis()));
                        meridiemSt = meridiemForm.format(new Date(System.currentTimeMillis()));
                        Message mess = handler.obtainMessage();
                        handler.sendMessage(mess);

                        Thread.sleep(1000);
                    }catch (InterruptedException ex){

                    }
                }
            }
        });

        th.start();

    }

    class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            date.setText(dateFormSt);
            time.setText(timeFormSt);
            meridiem.setText(meridiemSt);
        }
    }


    private class PagerAdapter extends FragmentStatePagerAdapter
    {
        public PagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {

            switch(position)
            {
                default:

                    Word ww =  db.getOne(position);

                    Bundle bundle = new Bundle();
                    String frage_target = ww.getWordTarget();
                    String frage_meaning = ww.getWordMeaning();

                    bundle.putString("target", frage_target );
                    bundle.putString("meaning", frage_meaning );

                    CardFragment cf = new CardFragment();
                    cf.setArguments(bundle);

                    return cf;
            }
        }
        @Override
        public int getCount()
        {
            return pageNum;
        }
    }


}

