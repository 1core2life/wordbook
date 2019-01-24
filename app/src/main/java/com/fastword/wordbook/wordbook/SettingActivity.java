package com.fastword.wordbook.wordbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    Switch lockscreen;
    ConstraintLayout lay;
    Button cold,deep,gray,newyork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        lay = findViewById(R.id.lay);
        cold = findViewById(R.id.coldevening);
        deep = findViewById(R.id.deepblue);
        gray = findViewById(R.id.gray);
        newyork = findViewById(R.id.newyork);

        View.OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){

                    case R.id.coldevening:
                        MainActivity.colorNum = 2;
                        break;

                    case R.id.deepblue:
                        MainActivity.colorNum = 1;
                        break;

                    case R.id.gray:
                        MainActivity.colorNum = 0;
                        break;

                    case R.id.newyork:
                        MainActivity.colorNum = 3;
                        break;

                }

                changeColor();
            }
        };

        cold.setOnClickListener(mClickListener);
        deep.setOnClickListener(mClickListener);
        gray.setOnClickListener(mClickListener);
        newyork.setOnClickListener(mClickListener);

        lockscreen = findViewById(R.id.lockscreen);

        if(MainActivity.isServiceOn){
            lockscreen.setChecked(true);
        }
        else{
            lockscreen.setChecked(false);
        }

        lockscreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                if(isChecked){
                    Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
                    startService(intent);
                    MainActivity.isServiceOn = true;

                }
                else{
                    Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
                    stopService(intent);
                    MainActivity.isServiceOn = false;


                }
            }
        });



    }

    @Override
    public void onResume(){
        super.onResume();

        changeColor();
    }


    void changeColor(){
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

        savePreferences();
    }

    // 값 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("backgroundColor", MainActivity.colorNum);
        editor.commit();
    }



}
