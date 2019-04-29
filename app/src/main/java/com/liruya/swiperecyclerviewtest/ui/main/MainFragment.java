package com.liruya.swiperecyclerviewtest.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liruya.swiperecyclerview.OnSwipeItemClickListener;
import com.liruya.swiperecyclerview.OnSwipeItemTouchListener;
import com.liruya.swiperecyclerviewtest.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment
{
    private static final String TAG = "MainFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private final List<Model> mModels = new ArrayList<>();

    private MainViewModel mViewModel;

    private final OnSwipeItemTouchListener mSwipeItemTouchListener = new OnSwipeItemTouchListener();

    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate( R.layout.main_fragment, container, false );

        initView( view );
        return view;
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );
        mViewModel = ViewModelProviders.of(this)
                                       .get( MainViewModel.class );
        initData();
        initEvent();
    }

    private void initView( View view )
    {
        mRecyclerView = view.findViewById( R.id.recyclerview );
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false ));
        mRecyclerView.addItemDecoration( new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL ));
        mRecyclerView.addOnItemTouchListener( mSwipeItemTouchListener );
    }

    private void initData()
    {
        mModels.addAll(mViewModel.createModels());
        mAdapter = new MainAdapter( getContext(), mModels );
        ( (MainAdapter) mAdapter ).setOnSwipeItemClickListener( new OnSwipeItemClickListener() {
            @Override
            public void onContentClick( final int position )
            {
                Log.e(TAG, "onContentClick: " + position + " / " + mModels.size() );
                Toast.makeText( getContext(), "Click Index: " + (position+1) + "  Content", Toast.LENGTH_SHORT )
                     .show();
            }

            @Override
            public void onActionClick( final int position, int actionid )
            {
                switch ( actionid )
                {
                    case R.id.remove:
                        mSwipeItemTouchListener.close();
                        Toast.makeText( getContext(), "Click Index: " + (position+1) + "  Action - Remove", Toast.LENGTH_SHORT )
                             .show();
                        mModels.remove(position);
//                        mAdapter.notifyDataSetChanged();
                        mAdapter.notifyItemRemoved(position);
                        break;
                    case R.id.upgrade:
                        Toast.makeText( getContext(), "Click Index: " + (position+1) + "  Action - Upgrade", Toast.LENGTH_SHORT )
                             .show();
                        break;
                }

            }
        } );
//        mAdapter = new SimpleSwipeAdapter<Model>( getContext(), R.layout.item_swipe_cover_left, mViewModel.createModels()) {
//            @Override
//            public void onBindViewHolder( @NonNull SwipeViewHolder holder, int position )
//            {
//                Model model = getItem( position );
//                if ( model != null )
//                {
//                    ImageView icon = holder.getContentView().findViewById( R.id.item_content_icon );
//                    TextView text = holder.getContentView().findViewById( R.id.item_content_text );
//                    icon.setImageResource( model.getDrawableResID() );
//                    text.setText( model.getText() );
//                }
//            }
//        };
        mRecyclerView.setAdapter( mAdapter );
    }

    private void initEvent()
    {

    }
}
