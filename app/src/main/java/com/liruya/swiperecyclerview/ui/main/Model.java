package com.liruya.swiperecyclerview.ui.main;

import androidx.annotation.DrawableRes;

public class Model
{
    private @DrawableRes int mDrawableResID;
    private String mText;

    public Model()
    {
    }

    public Model( int drawableResID, String text )
    {
        mDrawableResID = drawableResID;
        mText = text;
    }

    public int getDrawableResID()
    {
        return mDrawableResID;
    }

    public void setDrawableResID( @DrawableRes int drawableResID )
    {
        mDrawableResID = drawableResID;
    }

    public String getText()
    {
        return mText;
    }

    public void setText( String text )
    {
        mText = text;
    }
}
