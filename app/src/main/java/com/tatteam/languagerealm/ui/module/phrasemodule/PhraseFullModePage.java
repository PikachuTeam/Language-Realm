package com.tatteam.languagerealm.ui.module.phrasemodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BasePage;
import com.tatteam.languagerealm.app.BasePhraseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.LetterEntity;
import com.tatteam.languagerealm.ui.adapter.LetterFullModeAdapter;

import java.util.ArrayList;
import java.util.List;


public class PhraseFullModePage extends BasePage implements LetterFullModeAdapter.OnClickLetter {
    public RelativeLayout contentLetter, contentPhrase;
    private int COLUMN_NUMBER = 6;
    private RecyclerView mRvLetter;
    private RecyclerView.LayoutManager mLMLetter;
    private LetterFullModeAdapter mAdapterFullMode;
    private ArrayList<String> listLetter;
    private ProgressBar progressBar;

    public PhraseFullModePage(BaseActivity activity, BasePhraseFragment fragment) {
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
                loadLetterInData();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                setUpRvLetter();
                progressBar.setVisibility(View.GONE);
            }
        }.execute();


    }

    @Override
    protected int getContentId() {
        return R.layout.page_phrase_full_mode;
    }

    public void setUpRvLetter() {
        contentLetter = (RelativeLayout) content.findViewById(R.id.view_list_letter);
        mRvLetter = (RecyclerView) content.findViewById(R.id.recycler_view_fullmode);
        mRvLetter.setHasFixedSize(true);
        mLMLetter = new GridLayoutManager(activity, COLUMN_NUMBER);
        mRvLetter.setLayoutManager(mLMLetter);
        mAdapterFullMode = new LetterFullModeAdapter(listLetter);
        mAdapterFullMode.setLetterClickListener(this);
        mRvLetter.setAdapter(mAdapterFullMode);
    }

    public void loadLetterInData() {
        listLetter = new ArrayList<>();
        List<LetterEntity> list = DataSource.getInstance().getLetters(fragment.SQL_TABLE_NAME);
        for (int i = 0; i < list.size(); i++) {
            listLetter.add(list.get(i).letter);
        }
    }

    @Override
    public void onLetterClick(int position) {

        ListPhraseFullModeFragment listPhraseFullModeFragment = new ListPhraseFullModeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("letter", listLetter.get(position));
        bundle.putString("phrase_kind", fragment.SQL_TABLE_NAME);
        bundle.putString("phrase_kind_name", activity.getResources().getString(fragment.FRAGMENT_NAME_ID));
        listPhraseFullModeFragment.setArguments(bundle);
        fragment.replaceFragment(fragment.getBaseActivity().getFragmentManager(), listPhraseFullModeFragment, listLetter.get(position), listLetter.get(position));


    }


}
