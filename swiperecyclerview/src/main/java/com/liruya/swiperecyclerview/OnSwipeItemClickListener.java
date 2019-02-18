package com.liruya.swiperecyclerview;

import android.support.annotation.IdRes;

public interface OnSwipeItemClickListener
{
    void onContentClick( int position );

    void onActionClick( int position, @IdRes int actionid);
}
