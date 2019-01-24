package com.fastword.wordbook.wordbook;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.grantland.widget.AutofitHelper;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class CardFragment extends Fragment {

    int TARGET_TEXT_SIZE_DP = 50;

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
