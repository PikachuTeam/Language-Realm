package com.tatteam.languagerealm.ui.module;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseFragment;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.PhraseEntity;

import java.util.Locale;


public class DetailFragment extends BaseFragment {
    private String SQL_TABLE_NAME;
    private String PHRASE_KIND_NAME;
    private String phrase;
    private Toolbar toolbar;
    private PhraseEntity phraseEntity;
    private TextView tvPhrase, tvExplanation;
    private TextToSpeech textToSpeech;
    private FloatingActionButton fabWeb, fabTts;


    @Override
    protected boolean isPhraseFragment() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lockNavigationView(true);
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        tvPhrase = (TextView) rootView.findViewById(R.id.tvPhrase);
        tvExplanation = (TextView) rootView.findViewById(R.id.tvExplanation);
        fabWeb = (FloatingActionButton) rootView.findViewById(R.id.fab_web);
        fabTts = (FloatingActionButton) rootView.findViewById(R.id.fab_tts);
        refeshData();

        setUpToolBar();
        setUpTextToSpeech();
        setUpFabWeb();
        setText();

        return rootView;
    }


    public void setUpToolBar() {
        toolbar.inflateMenu(R.menu.menu_detail);
        toolbar.setTitle(PHRASE_KIND_NAME);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_grade_white_24dp, null));
        } else toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_grade_white_24dp));
        if (phraseEntity.isFavorite > 0) {
            toolbar.getMenu().setGroupVisible(R.id.favorite_true, true);
            toolbar.getMenu().setGroupVisible(R.id.favorite_false, false);
        } else {
            toolbar.getMenu().setGroupVisible(R.id.favorite_true, false);
            toolbar.getMenu().setGroupVisible(R.id.favorite_false, true);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.remove_favorite) {
                    DataSource.getInstance().changeFavoritePhrase(phrase, SQL_TABLE_NAME);
                    toolbar.getMenu().setGroupVisible(R.id.favorite_true, false);
                    toolbar.getMenu().setGroupVisible(R.id.favorite_false, true);
                    makeSnackBar(R.string.removed_from_favorite);
                }
                if (item.getItemId() == R.id.add_favorite) {
                    DataSource.getInstance().changeFavoritePhrase(phrase, SQL_TABLE_NAME);
                    toolbar.getMenu().setGroupVisible(R.id.favorite_true, true);
                    toolbar.getMenu().setGroupVisible(R.id.favorite_false, false);
                    makeSnackBar(R.string.added_to_favorite);
                }
                return false;
            }
        });


    }


    private void refeshData() {
        Bundle bundle = this.getArguments();
        phrase = bundle.getString("phrase");
        SQL_TABLE_NAME = bundle.getString("phrase_kind");
        PHRASE_KIND_NAME = bundle.getString("phrase_kind_name");
        phraseEntity = new PhraseEntity();

        phraseEntity = DataSource.getInstance().getOnePhrase(phrase, SQL_TABLE_NAME);
        DataSource.getInstance().updateRecent(phrase,SQL_TABLE_NAME);


    }

    public void setText() {
        tvPhrase.setText(Html.fromHtml(phraseEntity.phrase));

        String explanationFormated = explanationFormated(phraseEntity.explanation);
        tvExplanation.setText(explanationFormated);
    }

    public String explanationFormated(String explanation) {
        String result = "";
        String[] parts = explanation.split("\\(");
        if (parts.length > 1) {
            for (int i = 0; i < parts.length - 1; i++)
                result = result + parts[i];
            result = result + "\n" + " (" + parts[parts.length - 1];
        } else result = result + explanation;
        return result;
    }

    public void setUpFabWeb() {

        fabWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSearchPhraseFragment searchIdiomFragment = new GoogleSearchPhraseFragment() {

                };
                Bundle bundle = new Bundle();
                bundle.putString("phrase", phrase);
                searchIdiomFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_silde_bot_enter, R.anim.fragment_silde_bot_exit,
                        R.anim.fragment_silde_bot_enter, R.anim.fragment_silde_bot_exit);
                transaction.add(R.id.main_content, searchIdiomFragment, "WebView");
                transaction.addToBackStack("WebView");
                transaction.commit();
            }
        });
    }

    private void setUpTextToSpeech() {

        fabTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, null);
                    } else textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);


                }
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();
        try {
            textToSpeech = new TextToSpeech(getBaseActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        try {
                            textToSpeech.setLanguage(Locale.FRENCH);
                            textToSpeech.setSpeechRate(0.8f);
                        } catch (Exception e) {
                            fabTts.setVisibility(View.GONE);
                        }
                    }
                }
            });
        } catch (Exception ae) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}