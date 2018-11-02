package com.liruya.swiperecyclerview.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liruya.swiperecyclerview.OnSwipeItemTouchListener;
import com.liruya.swiperecyclerview.R;
import com.liruya.swiperecyclerview.SimpleSwipeAdapter;
import com.liruya.swiperecyclerview.SwipeViewHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment
{
    private static final String TAG = "MainFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private MainViewModel mViewModel;

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.main_fragment, container, false );

        initView( view );
        return view;
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );
        mViewModel = ViewModelProviders.of( this )
                                       .get( MainViewModel.class );
        initData();
        initEvent();
    }

    private void initView( View view )
    {
        mRecyclerView = view.findViewById( R.id.recyclerview );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(), DividerItemDecoration.HORIZONTAL ) );
        mRecyclerView.addOnItemTouchListener( new OnSwipeItemTouchListener() );
    }

    private void initData()
    {

        mAdapter = new SimpleSwipeAdapter<Model>(getContext(), R.layout.item_swipe, mViewModel.createModels()) {
            @Override
            public void onBindViewHolder( @NonNull SwipeViewHolder holder, int position )
            {
                Model model = getItem( position );
                if ( model != null )
                {
                    ImageView icon = holder.getContentView().findViewById( R.id.item_content_icon );
                    TextView text = holder.getContentView().findViewById( R.id.item_content_text );
                    icon.setImageResource( model.getDrawableResID() );
                    text.setText( model.getText() );
                }
            }
        };
        mRecyclerView.setAdapter( mAdapter );
    }

    private void initEvent()
    {

    }
}
