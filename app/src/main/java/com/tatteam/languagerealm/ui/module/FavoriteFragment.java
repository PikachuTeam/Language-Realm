package com.tatteam.languagerealm.ui.module;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.tatteam.languagerealm.entity.LetterEntity;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by hoaba on 9/10/2015.
 */
public class FavoriteFragment extends BaseFragment implements FavoriteAdapter.ClickListener {
    private List<PhraseEntity> list;

    private int NAME_ID = R.string.favorite;
    private int STYLE_ID = R.style.AppTheme;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout noFavorite;
    private LetterEntity[] letterEntities;
    private ProgressBar progressBar;

    @Override
    protected boolean isPhraseFragment() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createListLetter();
        setTheme(STYLE_ID);
        setNavHeaderColor(R.color.colorPrimaryDark);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list = getListFavorite();

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        lockNavigationView(false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        noFavorite = (RelativeLayout) rootView.findViewById(R.id.non_favorite);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_favorite);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            protected Object doInBackground(Object[] params) {
                list = getListFavorite();
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (list.size() > 0) noFavorite.setVisibility(View.GONE);
                else noFavorite.setVisibility(View.VISIBLE);
                mAdapter = new FavoriteAdapter(getBaseActivity(), list);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter.setmLislistener(FavoriteFragment.this);
                progressBar.setVisibility(View.GONE);
                setUpToolBar();
            }
        }.execute();
        return rootView;
    }

    @Override
    public void onPhraseClick(int position) {
        DetailFragment detailIdiomFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", list.get(position).phrase);
        switch (list.get(position).kind_ID) {
            case R.string.slang:
                bundle.putString("phrase_kind", "french_slang");
                break;
            case R.string.idiom:
                bundle.putString("phrase_kind", "french_idioms");
                break;
            case R.string.proverb:
                bundle.putString("phrase_kind", "french_proverbs");
                break;
        }
        bundle.putString("phrase_kind_name", getResources().getString(list.get(position).kind_ID));


        detailIdiomFragment.setArguments(bundle);
        replaceFragment(getActivity().getFragmentManager(), detailIdiomFragment, list.get(position).phrase, list.get(position).phrase);

    }

    @Override
    public void onFavoriteChange(int position) {

        switch (list.get(position).kind_ID) {
            case R.string.slang:
                DataSource.getInstance().changeFavoritePhrase(list.get(position).phrase, "french_slang");
                break;
            case R.string.idiom:
                DataSource.getInstance().changeFavoritePhrase(list.get(position).phrase, "french_idioms");
                break;
            case R.string.proverb:
                DataSource.getInstance().changeFavoritePhrase(list.get(position).phrase, "french_proverbs");
                break;
        }
        list.remove(position);
        makeSnackBar(R.string.removed_from_favorite);
        list = getListFavorite();


        mAdapter = new FavoriteAdapter(getBaseActivity(), list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmLislistener(this);
        if (list.size() == 0)
            noFavorite.setVisibility(View.VISIBLE);
        else noFavorite.setVisibility(View.GONE);
    }

    public List<PhraseEntity> getListFavorite() {
        list = DataSource.getInstance().getListFavorite();
        List<PhraseEntity> listFavorite = new ArrayList<>();
        //create favorites
        for (int i = 0; i < list.size(); i++) {
            PhraseEntity phraseEntity = list.get(i);
            char character = phraseEntity.letter.charAt(0);
            if (!checkExitsCharacter(listFavorite, character)) {
                PhraseEntity phraseEntityHeader = new PhraseEntity();
                phraseEntityHeader.phrase = character + "";
                phraseEntityHeader.isHeader = true;
                listFavorite.add(phraseEntityHeader);

            }
            listFavorite.add(phraseEntity);
        }

        //set index indioms
        for (int i = 0; i < listFavorite.size(); i++) {
            PhraseEntity phraseEntity = listFavorite.get(i);
            for (int k = 0; k < letterEntities.length; k++) {
                LetterEntity letterEntity = letterEntities[k];
                if (letterEntity.letter == phraseEntity.letter) {
                    phraseEntity.index = letterEntity.id;
                    if (phraseEntity.isHeader) {
                        phraseEntity.index -= 0.5f;
                    }
                    break;
                }
            }
        }

        //sort indioms
        for (int i = 0; i < listFavorite.size(); i++) {
            for (int j = i; j < listFavorite.size(); j++) {
                if (listFavorite.get(i).index > listFavorite.get(j).index) {
                    Collections.swap(listFavorite, i, j);
                }
            }
        }
        return listFavorite;
    }

    private boolean checkExitsCharacter(List<PhraseEntity> listFavorite, char character) {
        for (int i = 0; i < listFavorite.size(); i++) {
            if (character == listFavorite.get(i).phrase.charAt(0)) {
                return true;
            }
        }
        return false;
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

    public void createListLetter() {
        letterEntities = new LetterEntity[]{
                new LetterEntity(1, "A"),
                new LetterEntity(2, "B"),
                new LetterEntity(3, "C"),
                new LetterEntity(4, "D"),
                new LetterEntity(5, "E"),
                new LetterEntity(6, "F"),
                new LetterEntity(7, "G"),
                new LetterEntity(8, "H"),
                new LetterEntity(9, "I"),
                new LetterEntity(10, "J"),
                new LetterEntity(11, "K"),
                new LetterEntity(12, "L"),
                new LetterEntity(13, "M"),
                new LetterEntity(14, "N"),
                new LetterEntity(15, "O"),
                new LetterEntity(16, "P"),
                new LetterEntity(17, "Q"),
                new LetterEntity(18, "R"),
                new LetterEntity(19, "S"),
                new LetterEntity(20, "T"),
                new LetterEntity(21, "U"),
                new LetterEntity(22, "V"),
                new LetterEntity(23, "W"),
                new LetterEntity(24, "X"),
                new LetterEntity(25, "Y"),
                new LetterEntity(26, "Z"),


        };
    }
}
