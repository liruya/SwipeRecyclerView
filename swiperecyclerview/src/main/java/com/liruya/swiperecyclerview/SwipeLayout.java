package com.liruya.swiperecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class SwipeLayout extends RelativeLayout
{
    public static final int SWIPE_MODE_DISALED = 0;
    public static final int SWIPE_MODE_COVER = 1;
    public static final int SWIPE_MODE_SCROLL = 2;
    public static final int SWIPE_DIRECTION_LEFT = 0;
    public static final int SWIPE_DIRECTION_RIGHT = 1;

    /**
     * swipeMode: 0-Disabled  1-Cover  2-Scroll
     */
    private int mSwipeMode;

    /**
     * swipeDirection: 0-Left  1-Right
     */
    private int mSwipeDirection;

    /**
     * contentLayoutResID: create custom layout as content
     */
    private @LayoutRes int mContentLayoutResID;

    /**
     * actionLayoutResID: create custom layout as action
     */
    private @LayoutRes int mActionLayoutResID;

    private View mContentView;
    private View mActionView;

    private OnSwipeItemClickListener mOnSwipeItemClickListener;

    public SwipeLayout( Context context )
    {
        this( context, null );
    }

    public SwipeLayout( Context context, AttributeSet attrs )
    {
        this( context, attrs, 0 );
    }

    public SwipeLayout( Context context, AttributeSet attrs, int defStyleAttr )
    {
        super( context, attrs, defStyleAttr );

        TypedArray a = context.obtainStyledAttributes( attrs, R.styleable.SwipeLayout );
        mSwipeMode = a.getInt( R.styleable.SwipeLayout_swipeMode, SWIPE_MODE_DISALED );
        mSwipeDirection = a.getInt( R.styleable.SwipeLayout_swipeDirection, SWIPE_DIRECTION_LEFT );
        mContentLayoutResID = a.getResourceId( R.styleable.SwipeLayout_contentLayout, 0 );
        mActionLayoutResID = a.getResourceId( R.styleable.SwipeLayout_actionLayout, 0 );
        a.recycle();

        initView( context );
    }

    private void initView( @NonNull Context context )
    {
        LayoutInflater inflater = LayoutInflater.from( context );

        //加载contentView
        mContentView = inflater.inflate( mContentLayoutResID, this, false );
        //如果contentView为空,视图异常 退出
        if ( mContentView == null )
        {
            return;
        }
        //触摸事件需要使能点击
        mContentView.setClickable( true );
        LayoutParams lpc = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT );
        lpc.addRule( ALIGN_PARENT_LEFT );
        lpc.addRule( ALIGN_PARENT_RIGHT );
        mContentView.setLayoutParams( lpc );

        //Swipe模式为Cover或Scroll 加载actionView 否则不加载actionView
        if ( mSwipeMode == SWIPE_MODE_COVER || mSwipeMode == SWIPE_MODE_SCROLL )
        {
            mActionView = inflater.inflate( mActionLayoutResID, this, false );
        }
        //如果actionView为空,Swipe模式Disabled 只加载contentView
        if ( mActionView == null )
        {
            addView( mContentView );
        }
        else
        {
            //触摸事件需要使能点击
            mActionView.setClickable( true );
            LayoutParams lpa = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
            //如果用户未设置id,则设置为默认id
            if ( mContentView.getId() == View.NO_ID )
            {
                mContentView.setId( R.id.default_content_id );
            }
            if ( mActionView.getId() == View.NO_ID )
            {
                mActionView.setId( R.id.default_action_id );
            }
            //Cover模式 contentView 覆盖在 actionView上方
            if ( mSwipeMode == SWIPE_MODE_COVER )
            {
                lpa.addRule( mSwipeDirection == SWIPE_DIRECTION_RIGHT ? ALIGN_PARENT_LEFT : ALIGN_PARENT_RIGHT );
                lpa.addRule( ALIGN_TOP, mContentView.getId() );
                lpa.addRule( ALIGN_BOTTOM, mContentView.getId() );
                lpa.setMargins( 0, 0, 0, 0 );
                mActionView.setLayoutParams( lpa );
                //先添加actionView 后添加contentView
                addView( mActionView );
                addView( mContentView );
            }   //Scoll模式  actionView toRightOf/toLeftOf contetnView,左右滚动时显示actionView
            else if ( mSwipeMode == SWIPE_MODE_SCROLL )
            {
                lpa.addRule( mSwipeDirection == SWIPE_DIRECTION_RIGHT ? LEFT_OF : RIGHT_OF, mContentView.getId() );
                lpa.addRule( ALIGN_TOP, mContentView.getId() );
                lpa.addRule( ALIGN_BOTTOM, mContentView.getId() );
                //marginLeft/marginRight不能为0 否则无法显示
                lpa.setMargins( 1, 0, 1, 0 );
                mActionView.setLayoutParams( lpa );
                //先添加contentView 后添加actionView
                addView( mContentView );
                addView( mActionView );
            }
        }
    }

    public int getContentViewWidth()
    {
        return mContentView == null ? 0 : mContentView.getWidth();
    }

    public int getActionViewWidth()
    {
        return mActionView == null ? 0 : mActionView.getWidth();
    }

    public View getContentView()
    {
        return mContentView;
    }

    public View getActionView()
    {
        return mActionView;
    }

    public int getSwipeMode()
    {
        return mSwipeMode;
    }

    public int getSwipeDirection()
    {
        return mSwipeDirection;
    }

    public boolean canSwipe()
    {
        return (getActionViewWidth() > 0);
    }

    public void open()
    {

    }

    public void close()
    {

    }

    public boolean isTouchDownOnAction( int x )
    {
//        if ( mSwipeMode == SWIPE_MODE_COVER )
//        {
//            if ( mSwipeDirection == SWIPE_DIRECTION_RIGHT )
//            {
//                if ( x < getActionViewWidth() )
//                {
//                    return true;
//                }
//            }
//            else if ( mSwipeDirection == SWIPE_DIRECTION_LEFT )
//            {
//                if ( x > getWidth() - getActionViewWidth() )
//                {
//                    return true;
//                }
//            }
//        }
//        else if ( mSwipeMode == SWIPE_MODE_SCROLL )
//        {
//            if ( mSwipeDirection == SWIPE_DIRECTION_RIGHT )
//            {
//
//            }
//            else if ( mSwipeDirection == SWIPE_DIRECTION_LEFT )
//            {
//
//            }
//        }
        if ( mSwipeDirection == SWIPE_DIRECTION_RIGHT )
        {
            if ( x < getActionViewWidth() )
            {
                return true;
            }
        }
        else if ( mSwipeDirection == SWIPE_DIRECTION_LEFT )
        {
            if ( x > getWidth() - getActionViewWidth() )
            {
                return true;
            }
        }
        return false;
    }

    public void setOnSwipeItemClickListener( OnSwipeItemClickListener listener )
    {
        mOnSwipeItemClickListener = listener;
        if ( mActionView != null )
        {
            if ( mActionView instanceof ViewGroup )
            {
                for ( int i = 0; i < ( (ViewGroup) mActionView ).getChildCount(); i++ )
                {
                    ( (ViewGroup) mActionView ).getChildAt( i ).setOnClickListener( new OnClickListener() {
                        @Override
                        public void onClick( View v )
                        {
                            mOnSwipeItemClickListener.onActionClick( v.getId() );
                        }
                    } );
                }
            }
            else
            {
                mActionView.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick( View v )
                    {
                        mOnSwipeItemClickListener.onActionClick( v.getId() );
                    }
                } );
            }
        }
        if ( mContentView != null )
        {
            mContentView.setOnClickListener( new OnClickListener() {
                @Override
                public void onClick( View v )
                {
                    mOnSwipeItemClickListener.onContentClick();
                }
            } );
        }
    }

    public interface OnSwipeItemClickListener
    {
        void onContentClick();

        void onActionClick( @IdRes int actionid );
    }
}
