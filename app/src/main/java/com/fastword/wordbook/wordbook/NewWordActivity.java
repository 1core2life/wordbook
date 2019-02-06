package com.fastword.wordbook.wordbook;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity {

    EditText inputText,inputText2;
    Button comm;

    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        inputText =  findViewById(R.id.editText3);
        inputText2 =  findViewById(R.id.editText4);
        comm = findViewById(R.id.commit);

        lay = findViewById(R.id.lay);
        final WordDatabaseHelper db = new WordDatabaseHelper(this);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputText.getText().toString().isEmpty() || inputText2.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "단어와 뜻을 입력 하세요!", Toast.LENGTH_LONG).show();
                }
                else {
                    Word wordDB = new Word(inputText.getText().toString(), inputText2.getText().toString());
                    db.add(wordDB);
                    finish();
                }
            }
        };


        comm.setOnClickListener(listener);

    }

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


}
