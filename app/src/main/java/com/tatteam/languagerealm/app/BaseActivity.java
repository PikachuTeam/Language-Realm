package com.tatteam.languagerealm.app;

import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.entity.NavEntity;
import com.tatteam.languagerealm.ui.adapter.NavAdapter;
import com.tatteam.languagerealm.ui.module.FavoriteFragment;
import com.tatteam.languagerealm.ui.module.RecentFragment;
import com.tatteam.languagerealm.ui.module.phrasemodule.PhraseFullModePage;
import com.tatteam.languagerealm.ui.module.phrasemodule.phrase.IdiomFragment;
import com.tatteam.languagerealm.ui.module.phrasemodule.phrase.ProverbFragment;
import com.tatteam.languagerealm.ui.module.phrasemodule.phrase.SlangFragment;


/**
 * Created by Shu on 10/4/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private ListView lvNav;
    private ImageView imgHeader;
    private NavEntity[] listNavItem;
    private NavAdapter mAdapter;
    private long backPressed;

    protected abstract BasePhraseFragment getFragmentContent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigationView();
        addFragmentContent();

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                BaseFragment fragment = (BaseFragment) getFragmentManager().findFragmentById(R.id.main_content);
                if (fragment.isPhraseFragment()) {
                    BasePhraseFragment fragment1 = (BasePhraseFragment) getFragmentManager().findFragmentById(R.id.main_content);
                    if (fragment1.getContentSearch().getVisibility() == View.VISIBLE) {
                        fragment1.backSearch();
                    } else handleDoubleBackToExit();
                } else
                    handleDoubleBackToExit();
            }

        }
    }

    private void handleDoubleBackToExit() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            backPressed = System.currentTimeMillis();
            makeSnackBar(R.string.please_click_back_again_to_exit);
        }
    }

    private void addFragmentContent() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        BasePhraseFragment fragment = getFragmentContent();
        transaction.add(R.id.main_content, fragment, fragment.getClass().getName());
        transaction.commit();
    }

    public void lockNavigationView(boolean isLock) {
        if (isLock) drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        else drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    private void setUpNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        lvNav = (ListView) findViewById(R.id.lis_nav_item);
        listNavItem = new NavEntity[]{
//                new NavEntity("Home", R.drawable.ic_language_white_18dp, true),
                new NavEntity(R.string.slang, R.drawable.ic_slang, true),
                new NavEntity(R.string.idiom, R.drawable.ic_idiom, false),
                new NavEntity(R.string.proverb, R.drawable.ic_proverb, false),
                new NavEntity(R.string.favorite, R.drawable.ic_grade_white_24dp, false),
                new NavEntity(R.string.recent, R.drawable.ic_query_builder_white_24dp, false)};
        mAdapter = new NavAdapter(this, listNavItem);
        lvNav.setAdapter(mAdapter);
        lvNav.setDivider(null);
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             if (!listNavItem[position].selected) {
                                                 switch (listNavItem[position].tittle_id) {
//                    case "Home":

//
//                        drawerLayout.closeDrawers();
//                        break;
                                                     case R.string.slang:
                                                         SlangFragment slangFragment = new SlangFragment();
                                                         replaceFragment(slangFragment, position);
                                                         drawerLayout.closeDrawers();
                                                         break;
                                                     case R.string.idiom:
                                                         IdiomFragment idiomFragment = new IdiomFragment();
                                                         replaceFragment(idiomFragment, position);
                                                         drawerLayout.closeDrawers();
                                                         break;
                                                     case R.string.proverb:
                                                         ProverbFragment proverbFragment = new ProverbFragment();
                                                         replaceFragment(proverbFragment, position);
                                                         drawerLayout.closeDrawers();
                                                         refeshSelectedListNav(position);
                                                         break;
                                                     case R.string.favorite:
                                                         FavoriteFragment favoriteFragment = new FavoriteFragment();
                                                         replaceFragment(favoriteFragment, position);
                                                         drawerLayout.closeDrawers();
                                                         refeshSelectedListNav(position);
                                                         break;
                                                     case R.string.recent:
                                                         RecentFragment recentFragment = new RecentFragment();
                                                         replaceFragment(recentFragment, position);
                                                         drawerLayout.closeDrawers();
                                                         refeshSelectedListNav(position);

                                                         break;
                                                 }
                                             } else drawerLayout.closeDrawers();
                                         }
                                     }

        );
    }

    public void refeshSelectedListNav(int position) {
        for (int i = 0; i < listNavItem.length; i++) {
            listNavItem[i].selected = false;
        }
        listNavItem[position].selected = true;
        mAdapter.notifyDataSetChanged();
    }

    public void replaceFragment(BaseFragment fragment, int position) {
        refeshSelectedListNav(position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_content, fragment).commit();


    }

    public void makeSnackBar(int stringID) {
        Snackbar.make(findViewById(R.id.coordinatorLayout), stringID, Snackbar.LENGTH_SHORT).show();
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }


}
