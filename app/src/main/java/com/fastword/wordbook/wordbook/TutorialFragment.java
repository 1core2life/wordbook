package com.fastword.wordbook.wordbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class TutorialFragment extends android.support.v4.app.Fragment {

    //0 - tutorial first image
    int view_id;

    public TutorialFragment(){

    }

    @Override
    public void setArguments(Bundle bundle){

        view_id = bundle.getInt("R_id");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.tutorial_page, container, false);

        ImageView iv = (ImageView)layout.findViewById(R.id.tutorial_imageview);
        Button bt = (Button) layout.findViewById(R.id.donotshow);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isTutorialOn", false);
                editor.commit();

                getActivity().finish();
            }
        };


        bt.setOnClickListener(listener);


        if(view_id == 0)
            iv.setImageDrawable(getResources().getDrawable( R.drawable.tutorial00) );

        else if(view_id == 1)
            iv.setImageDrawable(getResources().getDrawable( R.drawable.tutorial01) );

        else if(view_id == 2) {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.tutorial02));
            bt.setVisibility(View.VISIBLE);

        }

        return layout;
    }
}


