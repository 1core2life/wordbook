package com.fastword.wordbook.wordbook;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardFragment extends Fragment {

    TextView target , meaning;

    String targetSt ;
    String meaningSt ;

    public CardFragment()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setArguments(Bundle bundle){

        targetSt = bundle.getString("target");
        meaningSt = bundle.getString("meaning");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.card, container, false);

        target = (TextView) layout.getViewById(R.id.target);
        meaning = (TextView) layout.getViewById(R.id.meaning);

        target.setText(targetSt);
        meaning.setText(meaningSt);



        return layout;
    }



}
