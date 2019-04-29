package com.liruya.swiperecyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseSwipeAdapter<VH extends SwipeViewHolder> extends RecyclerView.Adapter<VH>
{
    private Context mContext;
    private OnSwipeItemClickListener mOnSwipeItemClickListener;

    public final void setOnSwipeItemClickListener( OnSwipeItemClickListener listener )
    {
        mOnSwipeItemClickListener = listener;
    }

    public BaseSwipeAdapter( @NonNull Context context)
    {
        mContext = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        @LayoutRes int layoutid = getLayoutResID(viewType);
        if ( layoutid != 0 )
        {
            View view = LayoutInflater.from( mContext ).inflate( layoutid, parent, false );
            if ( view instanceof SwipeLayout )
            {
                return onCreateSwipeViewHolder( (SwipeLayout) view );
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position)
    {
        onBindSwipeViewHolder( holder, position );
        if ( holder.itemView instanceof SwipeLayout )
        {
            ( (SwipeLayout) holder.itemView ).setOnClickListener( new SwipeLayout.OnClickListener() {
                @Override
                public void onContentClick()
                {
                    if ( mOnSwipeItemClickListener != null )
                    {
                        mOnSwipeItemClickListener.onContentClick(holder.getAdapterPosition());
                    }
                }

                @Override
                public void onActionClick( int actionid )
                {
                    if ( mOnSwipeItemClickListener != null )
                    {
                        mOnSwipeItemClickListener.onActionClick(holder.getAdapterPosition(), actionid);
                    }
                }
            } );
        }
    }

    public void close() {

    }

    protected abstract @LayoutRes int getLayoutResID( int viewType );

    protected abstract VH onCreateSwipeViewHolder( SwipeLayout swipeLayout );

    protected abstract void onBindSwipeViewHolder( @NonNull VH holder, final int position );
}
