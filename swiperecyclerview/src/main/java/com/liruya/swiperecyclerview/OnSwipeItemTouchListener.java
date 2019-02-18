package com.liruya.swiperecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;


public class OnSwipeItemTouchListener implements RecyclerView.OnItemTouchListener
{
    private final String TAG = "SwipeItemTouchListener";

    //手势滑动速度检测
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private SwipeViewHolder mCurrHolder;
    private SwipeViewHolder mLastHolder;
    private boolean mClickAction;
    private boolean mMoving;
    private int mTouchX;

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e)
    {
        //获取触摸按下的childView
        View view = rv.findChildViewUnder( e.getX(), e.getY() );
        if ( !( view instanceof SwipeLayout ) || !( (SwipeLayout) view ).canSwipe() )
        {
            if ( mLastHolder != null )
            {
                ((SwipeLayout) mLastHolder.itemView).close();
                mLastHolder = null;
            }
            return false;
        }
        switch ( e.getAction() )
        {
            case MotionEvent.ACTION_DOWN:
                //状态初始化
                mTouchX = (int) e.getX();
                mMoving = false;
                mClickAction = false;
                mCurrHolder = (SwipeViewHolder) rv.getChildViewHolder( view );
                if ( mLastHolder != null )                  //存在已经打开的item
                {
                    if ( mCurrHolder != mLastHolder )       //点击的不是已经打开的item,关闭已经打开的item
                    {
                        ((SwipeLayout) mLastHolder.itemView).close();
                        mLastHolder = null;
                    }
                    else
                    {
                        if ( ( (SwipeLayout) view ).isTouchDownOnAction( mTouchX ) )
                        {
                            mClickAction = true;
                        }
                        else
                        {
                            mMoving = true;
//                            ( (SwipeLayout) view ).requestDisallowInterceptTouchEvent( true );
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ( mClickAction || mCurrHolder == null )
                {
                    return false;
                }
                if ( !mMoving )
                {
                    mVelocityTracker.addMovement( e );
                    mVelocityTracker.computeCurrentVelocity( 1000 );
                    int direction = ( (SwipeLayout) view ).getSwipeDirection();
                    if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
                    {
                        if ( mVelocityTracker.getXVelocity() > 100 && Math.abs( mVelocityTracker.getYVelocity() ) < Math.abs( mVelocityTracker.getXVelocity() ) )
                        {
                            mMoving = true;
                        }
                    }
                    else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
                    {
                        if ( mVelocityTracker.getXVelocity() < -100 && Math.abs( mVelocityTracker.getYVelocity() ) < Math.abs( mVelocityTracker.getXVelocity() ) )
                        {
                            mMoving = true;
                        }
                    }
                    if ( mMoving )
                    {
//                        ( (SwipeLayout) view ).requestDisallowInterceptTouchEvent( true );
                        return true;
                    }
                }
                else
                {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.clear();
                if ( !mClickAction && mMoving )
                {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void onTouchEvent( @NonNull RecyclerView rv, @NonNull MotionEvent e )
    {
        if ( mCurrHolder == null )
        {
            mVelocityTracker.clear();
            return;
        }
        if ( mCurrHolder.getContentView() == null || mCurrHolder.getActionView() == null )
        {
            return;
        }
        int mode = mCurrHolder.getSwipeMode();
        switch ( e.getAction() )
        {
            case MotionEvent.ACTION_MOVE:
                int dx = (int) ( e.getX() - mTouchX);
                if ( mode == SwipeLayout.SWIPE_MODE_COVER )
                {
                    touchMoveOfCover( dx );
                }
                else if ( mode == SwipeLayout.SWIPE_MODE_SCROLL )
                {
                    touchMoveOfScroll( dx );
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.clear();
                if ( mMoving )
                {
                    if ( mode == SwipeLayout.SWIPE_MODE_COVER )
                    {
                        touchUpOfCover();
                    }
                    else if ( mode == SwipeLayout.SWIPE_MODE_SCROLL )
                    {
                        touchUpOfScroll();
                    }
                }
                mCurrHolder = null;
                break;
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent( boolean disallowIntercept )
    {

    }

    private void touchMoveOfCover( int dx )
    {
        int maxwidth = mCurrHolder.getActionViewWidth();
        int direction = mCurrHolder.getSwipeDirection();
        if ( mCurrHolder != mLastHolder )
        {
            if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
            {
                if ( dx < 0 )
                {
                    dx = 0;
                }
                else if ( dx > maxwidth )
                {
                    dx = maxwidth;
                }
            }
            else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
            {
                if ( dx > 0 )
                {
                    dx = 0;
                }
                else if ( dx < 0 - maxwidth )
                {
                    dx = 0 - maxwidth;
                }
            }
            else
            {
                dx = 0;
            }
        }
        else
        {
            if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
            {
                if ( dx > 0 )
                {
                    dx = maxwidth;
                }
                else if ( dx < 0 - maxwidth )
                {
                    dx = 0;
                }
                else
                {
                    dx = maxwidth + dx;
                }
            }
            else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
            {
                if ( dx < 0 )
                {
                    dx = 0 - maxwidth;
                }
                else if ( dx > maxwidth )
                {
                    dx = 0;
                }
                else
                {
                    dx -= maxwidth;
                }
            }
            else
            {
                dx = 0;
            }
        }
        mCurrHolder.getContentView().setTranslationX( dx );
    }

    private void touchUpOfCover()
    {
        int tx = (int) mCurrHolder.getContentView().getTranslationX();
        int direction = mCurrHolder.getSwipeDirection();
        int maxwidth = mCurrHolder.getActionViewWidth();
        if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
        {
            if ( tx > maxwidth/2 )
            {
                mCurrHolder.getContentView().setTranslationX( maxwidth );
                mLastHolder = mCurrHolder;
            }
            else
            {
                mCurrHolder.getContentView().setTranslationX( 0 );
                mLastHolder = null;
            }
        }
        else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
        {
            if ( tx < (0 - maxwidth)/2 )
            {
                mCurrHolder.getContentView().setTranslationX( 0 - maxwidth );
                mLastHolder = mCurrHolder;
            }
            else
            {
                mCurrHolder.getContentView().setTranslationX( 0 );
                mLastHolder = null;
            }
        }
    }

    private void touchMoveOfScroll( int dx )
    {
        int maxwidth = mCurrHolder.getActionViewWidth();
        int direction = mCurrHolder.getSwipeDirection();
        if ( mCurrHolder != mLastHolder )
        {
            if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
            {
                if ( dx < 0 )
                {
                    dx = 0;
                }
                else if ( dx > maxwidth )
                {
                    dx = maxwidth;
                }
            }
            else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
            {
                if ( dx > 0 )
                {
                    dx = 0;
                }
                else if ( dx < 0 - maxwidth )
                {
                    dx = 0 - maxwidth;
                }
            }
            else
            {
                dx = 0;
            }
        }
        else
        {
            if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
            {
                if ( dx > 0 )
                {
                    dx = maxwidth;
                }
                else if ( dx < 0 - maxwidth )
                {
                    dx = 0;
                }
                else
                {
                    dx = maxwidth + dx;
                }
            }
            else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
            {
                if ( dx < 0 )
                {
                    dx = 0 - maxwidth;
                }
                else if ( dx > maxwidth )
                {
                    dx = 0;
                }
                else
                {
                    dx -= maxwidth;
                }
            }
            else
            {
                dx = 0;
            }
        }
        mCurrHolder.itemView.setScrollX( 0 - dx );
    }

    private void touchUpOfScroll()
    {
        int tx = mCurrHolder.itemView.getScrollX();
        int direction = mCurrHolder.getSwipeDirection();
        int maxwidth = mCurrHolder.getActionViewWidth();
        if ( direction == SwipeLayout.SWIPE_DIRECTION_RIGHT )
        {
            if ( tx < (0 - maxwidth)/2 )
            {
                mCurrHolder.itemView.setScrollX( 0 -maxwidth );
                mLastHolder = mCurrHolder;
            }
            else
            {
                mCurrHolder.itemView.setScrollX( 0 );
                mLastHolder = null;
            }
        }
        else if ( direction == SwipeLayout.SWIPE_DIRECTION_LEFT )
        {
            if ( tx > maxwidth/2 )
            {
                mCurrHolder.itemView.setScrollX( maxwidth );
                mLastHolder = mCurrHolder;
            }
            else
            {
                mCurrHolder.itemView.setScrollX( 0 );
                mLastHolder = null;
            }
        }
    }
}
