package com.liruya.swiperecyclerviewtest.ui.main;

import android.arch.lifecycle.ViewModel;

import com.liruya.swiperecyclerviewtest.R;

import java.util.ArrayList;
import java.util.List;


public class MainViewModel extends ViewModel
{
    public static List<Model> createModels()
    {
        List<Model> models = new ArrayList<>();
        for ( int i = 0; i < 100; i++ )
        {
            models.add( new Model( R.mipmap.ic_launcher, "item  " + ( i + 1) ) );
        }
        return models;
    }
}
