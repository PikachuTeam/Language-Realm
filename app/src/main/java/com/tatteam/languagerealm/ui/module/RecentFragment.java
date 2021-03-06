package com.tatteam.languagerealm.ui.module;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.FavoriteAdapter;

import java.util.List;


/**
 * Created by hoaba on 9/10/2015.
 */
public class RecentFragment extends BaseFragment implements FavoriteAdapter.ClickListener {
    private List<PhraseEntity> listRecent;
    private int NAME_ID = R.string.recent;
    private int STYLE_ID = R.style.AppTheme;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout noRecent;
    private int MAX_COUNT = 100;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(STYLE_ID);
        setNavHeaderColor(R.color.colorPrimaryDark);

    }

    @Override
    protected boolean isPhraseFragment() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        lockNavigationView(false);
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        noRecent = (RelativeLayout) rootView.findViewById(R.id.non_favorite);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_favorite);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new

                LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                listRecent = getListRecent();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listRecent.size() > 0) noRecent.setVisibility(View.GONE);
                else noRecent.setVisibility(View.VISIBLE);
                mAdapter = new

                        FavoriteAdapter(getBaseActivity(), listRecent

                );
                mAdapter.activity = getBaseActivity();

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setmLislistener(RecentFragment.this);

                setUpToolBar();
                progressBar.setVisibility(View.GONE);
            }
        }.execute();


        return rootView;

    }

    @Override
    public void onPhraseClick(int position) {
        DetailFragment detailIdiomFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", listRecent.get(position).phrase);
        switch (listRecent.get(position).kind_ID) {
            case R.string.slang:
                bundle.putString("phrase_kind", "slang");
                break;
            case R.string.idiom:
                bundle.putString("phrase_kind", "idioms");
                break;
            case R.string.proverb:
                bundle.putString("phrase_kind", "proverbs");
                break;
        }
        bundle.putString("phrase_kind_name", getResources().getString(listRecent.get(position).kind_ID));


        detailIdiomFragment.setArguments(bundle);
        replaceFragment(getActivity().getFragmentManager(), detailIdiomFragment, listRecent.get(position).phrase, listRecent.get(position).phrase);

    }

    @Override
    public void onFavoriteChange(int position) {

        if (listRecent.get(position).isFavorite > 0) {
            listRecent.get(position).isFavorite = 0;
            makeSnackBar(R.string.removed_from_favorite);
        } else {
            listRecent.get(position).isFavorite = 1;
            makeSnackBar(R.string.added_to_favorite);
        }

        switch (listRecent.get(position).kind_ID) {
            case R.string.slang:
                DataSource.changeFavoritePhrase(listRecent.get(position).phrase, "slang");
                break;
            case R.string.idiom:
                DataSource.changeFavoritePhrase(listRecent.get(position).phrase, "idioms");
                break;
            case R.string.proverb:
                DataSource.changeFavoritePhrase(listRecent.get(position).phrase, "proverbs");
                break;
        }
        mAdapter.notifyDataSetChanged();

        if (listRecent.size() == 0)
            noRecent.setVisibility(View.VISIBLE);
        else noRecent.setVisibility(View.GONE);
    }

    public List<PhraseEntity> getListRecent() {
        listRecent = DataSource.getListRecent(MAX_COUNT);
        return listRecent;
    }


    public void setUpToolBar() {

        toolbar.setTitle(getResources().getString(NAME_ID));
        getBaseActivity().setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });


    }


}
