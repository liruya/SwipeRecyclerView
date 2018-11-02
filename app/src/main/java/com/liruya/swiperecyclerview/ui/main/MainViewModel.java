package com.liruya.swiperecyclerview.ui.main;

import com.liruya.swiperecyclerview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

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
