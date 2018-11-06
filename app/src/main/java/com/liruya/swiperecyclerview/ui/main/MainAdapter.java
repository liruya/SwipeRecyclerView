package com.liruya.swiperecyclerview.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liruya.swiperecyclerview.BaseSimpleSwipeAdapter;
import com.liruya.swiperecyclerview.R;
import com.liruya.swiperecyclerview.SwipeLayout;
import com.liruya.swiperecyclerview.SwipeViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class MainAdapter extends BaseSimpleSwipeAdapter<Model, MainAdapter.MainSwipeViewHolder>
{
    public MainAdapter( @NonNull Context context, List< Model > list )
    {
        super( context, list );
    }

    @Override
    public int getItemViewType( int position )
    {
        return (position%5);
    }

    @Override
    protected int getLayoutResID( int viewType )
    {
        int layoutid = 0;
        switch ( viewType )
        {
            case 0:
                layoutid = R.layout.item_swipe_disabled;
                break;
            case 1:
                layoutid = R.layout.item_swipe_cover_left;
                break;
            case 2:
                layoutid = R.layout.item_swipe_cover_right;
                break;
            case 3:
                layoutid = R.layout.item_swipe_scroll_left;
                break;
            case 4:
                layoutid = R.layout.item_swipe_scroll_right;
                break;
        }
        return layoutid;
    }

    @Override
    protected MainSwipeViewHolder onCreateSwipeViewHolder( SwipeLayout swipeLayout )
    {
        return new MainSwipeViewHolder( swipeLayout );
    }

    @Override
    protected void onBindSwipeViewHolder( @NonNull MainSwipeViewHolder holder, int position )
    {
        Model model = getItem( position );
        if ( model != null && holder != null )
        {
            if ( holder.icon != null )
            {
                holder.icon.setImageResource( model.getDrawableResID() );
            }
            if ( holder.text != null )
            {
                switch ( getItemViewType( position ) )
                {
                    case 0:
                        holder.text.setText( model.getText() + "   SwipeMode - Disabled" );
                        break;
                    case 1:
                        holder.text.setText( model.getText() + "   SwipeMode - Cover Left" );
                        break;
                    case 2:
                        holder.text.setText( model.getText() + "   SwipeMode - Cover Right" );
                        break;
                    case 3:
                        holder.text.setText( model.getText() + "   SwipeMode - Scroll Left" );
                        break;
                    case 4:
                        holder.text.setText( model.getText() + "   SwipeMode - Scroll Right" );
                        break;
                }

            }
        }
    }

    class MainSwipeViewHolder extends SwipeViewHolder
    {
        private ImageView icon;
        private TextView text;

        public MainSwipeViewHolder( @NonNull View itemView )
        {
            super( itemView );
            if ( getContentView() != null )
            {
                icon = getContentView().findViewById( R.id.item_content_icon );
                text = getContentView().findViewById( R.id.item_content_text );
            }
        }
    }
}
