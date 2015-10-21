package com.tatteam.languagerealm.ui.module.phrasemodule;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;


import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BasePage;
import com.tatteam.languagerealm.app.BasePhraseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.LetterEntity;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.LetterFullModeAdapter;
import com.tatteam.languagerealm.ui.adapter.PhraseInFullModeAdapter;
import com.tatteam.languagerealm.ui.module.DetailFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class PhraseFullModePage extends BasePage implements LetterFullModeAdapter.OnClickLetter, PhraseInFullModeAdapter.ClickListener {
    private int COLUMN_NUMBER = 6;
    private ObjectAnimator moveDown, moveUp;

    private RecyclerView mRvLetter;
    private RecyclerView.LayoutManager mLMLetter;
    private LetterFullModeAdapter mAdapterFullMode;
    private ArrayList<String> listLetter;
    private int DURATION_ANIMATION = 300;
    private List<PhraseEntity> listPhrase;
    private RecyclerView mRvPhrase;
    private PhraseInFullModeAdapter mAdapterPhrase;
    private RecyclerView.LayoutManager mLMPhrase;

    public RelativeLayout contentLetter, contentPhrase;

    @Override
    protected int getContentId() {
        return R.layout.page_phrase_full_mode;
    }

    public PhraseFullModePage(BaseActivity activity, BasePhraseFragment fragment) {
        super(fragment, activity);
        contentLetter = (RelativeLayout) content.findViewById(R.id.view_list_letter);
        contentPhrase = (RelativeLayout) content.findViewById(R.id.view_lisr_phrase);

        setUpDownAnimator();
        setUpUpAnimator();
        loadLetterInData();
        setUpRvLetter();
        setUpRvPhrase();
        setUpFabSwitchMode();
    }

    private void setUpUpAnimator() {
        moveUp = ObjectAnimator.ofFloat(contentLetter, "y", activity.getWindowManager().getDefaultDisplay().getHeight(), 0f);
        moveUp.setDuration(DURATION_ANIMATION);
        moveUp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                contentLetter.setVisibility(View.VISIBLE);
                fragment.fabSwitchMode.setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_view_list_white_24dp));

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fragment.fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.viewPager.setCurrentItem(0);
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void setUpDownAnimator() {
        moveDown = ObjectAnimator.ofFloat(contentLetter, "y", contentLetter.getY(), activity.getWindowManager().getDefaultDisplay().getHeight());
        moveDown.setDuration(DURATION_ANIMATION);
        moveDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fragment.fabSwitchMode.setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_24dp));

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contentLetter.setVisibility(View.GONE);
                fragment.fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveUp.start();
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

    }

    public void setUpFabSwitchMode() {
        if (contentLetter.getVisibility() == View.VISIBLE) {
            fragment.fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.viewPager.setCurrentItem(0);
                }
            });
        } else {
            fragment.fabSwitchMode.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_keyboard_backspace_white_24dp));
            fragment.fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveUp.start();
                }
            });
        }
    }

    public void setUpRvLetter() {
        mRvLetter = (RecyclerView) content.findViewById(R.id.recycler_view);
        mRvLetter.setHasFixedSize(true);
        mLMLetter = new GridLayoutManager(activity, COLUMN_NUMBER);
        mRvLetter.setLayoutManager(mLMLetter);
        mAdapterFullMode = new LetterFullModeAdapter(listLetter);
        mAdapterFullMode.setLetterClickListener(this);
        mRvLetter.setAdapter(mAdapterFullMode);
    }

    private void setUpRvPhrase() {
        mRvPhrase = (RecyclerView) getContent().findViewById(R.id.recycler_view_list_idioms);
        mRvPhrase.setHasFixedSize(true);
        mLMPhrase = new LinearLayoutManager(activity);
        mRvPhrase.setLayoutManager(mLMPhrase);
    }

    public void loadLetterInData() {
        listLetter = new ArrayList<>();
        List<LetterEntity> list = DataSource.getInstance().getLetters(fragment.SQL_TABLE_NAME);
        for (int i = 0; i < list.size(); i++) {
            listLetter.add(list.get(i).letter);
        }
    }

    public void loadPhraseInData(String letter) {
        listPhrase = new ArrayList<>();
        listPhrase = DataSource.getInstance().getPhraseByLetter(letter, fragment.SQL_TABLE_NAME);
        mAdapterPhrase = new PhraseInFullModeAdapter(activity, listPhrase);
        mRvPhrase.setAdapter(mAdapterPhrase);
        mAdapterPhrase.setmLislistener(this);
    }

    @Override
    public void onLetterClick(int position) {

        loadPhraseInData(listLetter.get(position));
        showListPhrase();
        moveDown.start();


    }

    private void showListPhrase() {

    }

    @Override
    public void onPhraseClick(int position) {
        DetailFragment detailIdiomFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", listPhrase.get(position).phrase);
        bundle.putString("phrase_kind", fragment.SQL_TABLE_NAME);
        bundle.putString("phrase_kind_name", activity.getResources().getString(fragment.FRAGMENT_NAME_ID));
        detailIdiomFragment.setArguments(bundle);
        fragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, listPhrase.get(position).phrase, listPhrase.get(position).phrase);
    }

    @Override
    public void onFavoriteChange(int position) {
        if (listPhrase.get(position).isFavorite > 0) {
            listPhrase.get(position).isFavorite = 0;
            activity.makeSnackBar(R.string.removed_from_favorite);
        } else {
            listPhrase.get(position).isFavorite = 1;
            activity.makeSnackBar(R.string.added_to_favorite);
        }
        DataSource.getInstance().changeFavoritePhrase(listPhrase.get(position).phrase, fragment.SQL_TABLE_NAME);
        mAdapterPhrase.notifyDataSetChanged();
    }
}
