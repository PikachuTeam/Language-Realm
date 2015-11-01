package com.tatteam.languagerealm.ui.module.phrasemodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BaseFragment;
import com.tatteam.languagerealm.app.BasePage;
import com.tatteam.languagerealm.app.BasePhraseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.LetterEntity;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.LetterQuickModeAdapter;
import com.tatteam.languagerealm.ui.adapter.PhraseInQuickModeAdapter;
import com.tatteam.languagerealm.ui.module.DetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shu on 10/19/2015.
 */
public class PhraseQuickModePage extends BasePage implements PhraseInQuickModeAdapter.clickListener, LetterQuickModeAdapter.ClickListener {

    private int SELECTED_POSITION;

    private RecyclerView rvPhrase, rvLetter;
    private PhraseInQuickModeAdapter adapterPhrase;
    private LetterQuickModeAdapter adapterLetter;

    private RecyclerView.LayoutManager lmPhrase, lmLetter;

    private List<LetterEntity> listletter;
    private List<PhraseEntity> listPhrase;
    private ProgressBar progressBar;

    public PhraseQuickModePage(BaseActivity activity, BasePhraseFragment fragment) {
        super(fragment, activity);
        progressBar = (ProgressBar) content.findViewById(R.id.progress);
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                loadDta();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                setUpRvLetter();
                setupRvPhrase();
                progressBar.setVisibility(View.GONE);
            }
        }.execute();


    }


    @Override
    protected int getContentId() {
        return R.layout.page_phrase_quick_mode;
    }


    public void setUpRvLetter() {
        rvLetter = (RecyclerView) getContent().findViewById(R.id.rv_letter);
        rvLetter.setHasFixedSize(true);
        lmLetter = new LinearLayoutManager(activity);
        rvLetter.setLayoutManager(lmLetter);
        adapterLetter = new LetterQuickModeAdapter(activity, listletter, fragment.FRAGMENT_NAME_ID);
        rvLetter.setAdapter(adapterLetter);
        adapterLetter.setmListener(this);
    }

    public void setupRvPhrase() {
        rvPhrase = (RecyclerView) getContent().findViewById(R.id.rv_phrase);
        rvPhrase.setHasFixedSize(true);
        lmPhrase = new LinearLayoutManager(activity);
        rvPhrase.setLayoutManager(lmPhrase);
        adapterPhrase = new PhraseInQuickModeAdapter(activity, listPhrase, fragment.FRAGMENT_NAME_ID);
        rvPhrase.setAdapter(adapterPhrase);
        adapterPhrase.setmListener(this);
    }

    public void loadDta() {
        listletter = new ArrayList<>();
        listPhrase = new ArrayList<>();
        listletter = DataSource.getInstance().getLetters(fragment.SQL_TABLE_NAME);
        listPhrase = DataSource.getInstance().getPhraseByLetter(listletter.get(0).letter, fragment.SQL_TABLE_NAME);

        if (!listLetterHasSelected()) {
            listletter.get(0).selected = true;
            SELECTED_POSITION = 0;
        }

    }

    public boolean listLetterHasSelected() {
        for (int i = 0; i < listletter.size(); i++) {
            if (listletter.get(i).selected) return true;
        }
        return false;
    }

    @Override
    public void onPhraseClick(int position) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", listPhrase.get(position).phrase);
        bundle.putString("phrase_kind", fragment.SQL_TABLE_NAME);
        bundle.putString("phrase_kind_name", activity.getResources().getString(fragment.FRAGMENT_NAME_ID));
        detailFragment.setArguments(bundle);
        BaseFragment.replaceFragment(activity.getFragmentManager(), detailFragment, activity.getResources().getString(fragment.FRAGMENT_NAME_ID), activity.getResources().getString(fragment.FRAGMENT_NAME_ID));

    }

    @Override
    public void onLetterClick(int position) {
        listletter.get(SELECTED_POSITION).selected = false;
        listletter.get(position).selected = true;

        adapterLetter.setListLetterEntity(listletter);
        adapterLetter.notifyDataSetChanged();

        listPhrase = DataSource.getInstance().getPhraseByLetter(listletter.get(position).letter.toUpperCase(), fragment.SQL_TABLE_NAME);
        adapterPhrase.setListPhraseEntity(listPhrase);
        adapterPhrase.notifyDataSetChanged();
        SELECTED_POSITION = position;
    }
}
