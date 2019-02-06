package com.fastword.wordbook.wordbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;

    static boolean isServiceOn = true;
    final WordDatabaseHelper db = new WordDatabaseHelper(this);

    ListView listview ;
    final WordListViewAdapter adapter = new WordListViewAdapter();

    static int colorNum = 0;
    ConstraintLayout cont;

    private Animation fabOpen, fabClose;
    private Boolean isFabOpen = false;
    FloatingActionButton fab,fabSetting,fabAdd;

    private boolean isTutorialOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cont = findViewById(R.id.cont);


        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);


        fab = findViewById(R.id.fab);
        fabAdd =  findViewById(R.id.fab_add);
        fabSetting =  findViewById(R.id.fab_setting);


        fab.setOnClickListener(listener);
        fabAdd.setOnClickListener(listener);
        fabSetting.setOnClickListener(listener);

        // 리스트뷰 참조 및 Adapter달기
        listview =  findViewById(R.id.listview);
        listview.setAdapter(adapter);

        loadWordList();

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(listview,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Word item = (Word) listView.getItemAtPosition(position) ;

                                    db.deleteOne(item.getWordTarget());
                                    adapter.remove(adapter.getItem(position));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        listview.setOnTouchListener(touchListener);
        listview.setOnScrollListener(touchListener.makeScrollListener());

        setFullAd();

        startLockScreen();

        if(isTutorialOn){
            Intent intent = new Intent(this,TutorialActivity.class);
            startActivity(intent);

            isTutorialOn = false;
        }

    }


    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.fab_add:
                    Intent intent = new Intent(getApplicationContext(),NewWordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab_setting:
                    Intent intent2 = new Intent(getApplicationContext(),SettingActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.fab:
                    fabAnimation();
                    break;
                default:
            }

        }

    };

    public void fabAnimation() {

        if (isFabOpen) {
            fabSetting.startAnimation(fabClose);
            fabAdd.startAnimation(fabClose);
            fabSetting.hide();
            fabSetting.setClickable(false);
            fabAdd.hide();
            fabAdd.setClickable(false);

            isFabOpen = false;
        }
        else {
            fabSetting.startAnimation(fabOpen);
            fabAdd.startAnimation(fabOpen);
            fabSetting.show();
            fabSetting.setClickable(true);
            fabAdd.show();
            fabAdd.setClickable(true);

            isFabOpen = true;
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        List<Word> wordList =  db.getAll();
        if ( adapter.getCount() < wordList.size() ) {
            Word ww = wordList.get(wordList.size() - 1);

            adapter.addItem(ww.getWordTarget(), ww.getWordMeaning());
            adapter.notifyDataSetChanged();
        }

        switch(colorNum){
            case 0:
                cont.setBackground(getResources().getDrawable( R.drawable.background_gray ));
                break;

            case 1:
                cont.setBackground(getResources().getDrawable( R.drawable.background_deepblue ));
                break;

            case 2:
                cont.setBackground(getResources().getDrawable( R.drawable.background_coldevening ));
                break;

            case 3:
                cont.setBackground(getResources().getDrawable( R.drawable.background_newyork ));
                break;

            default :


        }
    }

    private void startLockScreen(){

        getPreferences();

        if(isServiceOn){
            Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
            startService(intent);

        }
        else{
            Intent intent = new Intent(getApplicationContext(), LockScreenService.class);
            stopService(intent);

        }

    }

    private void loadWordList(){
        List<Word> wordList =  db.getAll();

        for (Word ww : wordList) {
            adapter.addItem(ww.getWordTarget(), ww.getWordMeaning());
        }

        Toast.makeText(MainActivity.this, "나만의 단어장, 패스트워드", Toast.LENGTH_LONG).show();

        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();


        savePreferences();
    }

    void changeColor(){
        switch(MainActivity.colorNum){
            case 0:
                cont.setBackground(getResources().getDrawable( R.drawable.background_gray ));
                break;

            case 1:
                cont.setBackground(getResources().getDrawable( R.drawable.background_deepblue ));
                break;

            case 2:
                cont.setBackground(getResources().getDrawable( R.drawable.background_coldevening ));
                break;

            case 3:
                cont.setBackground(getResources().getDrawable( R.drawable.background_newyork ));
                break;

            default :


        }
    }


    // 값 불러오기
    private void getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        isServiceOn = pref.getBoolean("service",false);
        colorNum = pref.getInt("backgroundColor",0);
        isTutorialOn = pref.getBoolean("isTutorialOn", true);
        changeColor();
    }

    // 값 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("service", isServiceOn);
        editor.commit();
    }


    private void setFullAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.ad));
        AdRequest adRequest1 = new AdRequest.Builder()
                .build();
        interstitialAd.loadAd(adRequest1);
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() {
                AdRequest adRequest1 = new AdRequest.Builder()
                        .build();
                interstitialAd.loadAd(adRequest1);
            }
        });

    }


    @Override
    public void onBackPressed() {

        if(interstitialAd.isLoaded())
            interstitialAd.show();
        else
            interstitialAd.loadAd(new AdRequest.Builder().build());

        super.onBackPressed();
    }
}
