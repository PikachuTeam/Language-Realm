package com.tatteam.languagerealm.ui.module.phrasemodule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.PhraseInFullModeAdapter;
import com.tatteam.languagerealm.ui.module.DetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shu on 10/22/2015.
 */
public class ListPhraseFullModeFragment extends BaseFragment implements PhraseInFullModeAdapter.ClickListener {
    private Toolbar toolbar;

    private String SQL_TABLE_NAME = "";
    private String FRAGMENT_NAME;
    private String LETTER = "";
    private List<PhraseEntity> listPhrase;
    private RecyclerView mRvPhrase;
    private PhraseInFullModeAdapter mAdapterPhrase;
    private RecyclerView.LayoutManager mLMPhrase;

    @Override
    protected boolean isPhraseFragment() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        LETTER = bundle.getString("letter");
        SQL_TABLE_NAME = bundle.getString("phrase_kind");
        FRAGMENT_NAME = bundle.getString("phrase_kind_name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_list_phrase, container, false);
        setUpToolBar(rootView);
        loadListPhraseInData();
        setUpRvPhrase(rootView);
        return rootView;
    }

    public void setUpToolBar(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(LETTER);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    private void setUpRvPhrase(View rootView) {
        mRvPhrase = (RecyclerView) rootView.findViewById(R.id.recycler_view_list_idioms);
        mRvPhrase.setHasFixedSize(true);
        mLMPhrase = new LinearLayoutManager(getBaseActivity());
        mRvPhrase.setLayoutManager(mLMPhrase);
        mAdapterPhrase = new PhraseInFullModeAdapter(getBaseActivity(), listPhrase);
        mRvPhrase.setAdapter(mAdapterPhrase);
        mAdapterPhrase.setmLislistener(this);
    }

    public void loadListPhraseInData() {
        listPhrase = new ArrayList<>();
        listPhrase = DataSource.getInstance().getPhraseByLetter(LETTER, SQL_TABLE_NAME);

    }


    @Override
    public void onPhraseClick(int position) {
        DetailFragment detailIdiomFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", listPhrase.get(position).phrase);
        bundle.putString("phrase_kind", SQL_TABLE_NAME);
        bundle.putString("phrase_kind_name", FRAGMENT_NAME);
        detailIdiomFragment.setArguments(bundle);
        replaceFragment(getBaseActivity().getFragmentManager(), detailIdiomFragment, listPhrase.get(position).phrase, listPhrase.get(position).phrase);
    }

    @Override
    public void onFavoriteChange(int position) {
        if (listPhrase.get(position).isFavorite > 0) {
            listPhrase.get(position).isFavorite = 0;
            makeSnackBar(R.string.removed_from_favorite);
        } else {
            listPhrase.get(position).isFavorite = 1;
            makeSnackBar(R.string.added_to_favorite);
        }
        DataSource.getInstance().changeFavoritePhrase(listPhrase.get(position).phrase, SQL_TABLE_NAME);
        mAdapterPhrase.notifyDataSetChanged();
    }

}
