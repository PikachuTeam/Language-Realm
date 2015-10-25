package com.tatteam.languagerealm.app;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.entity.PhraseEntity;
import com.tatteam.languagerealm.ui.adapter.MyViewPagerAdapter;
import com.tatteam.languagerealm.ui.adapter.SearchAdapter;
import com.tatteam.languagerealm.ui.module.DetailFragment;

import java.util.List;

/**
 * Created by Shu on 10/8/2015.
 */
public abstract class BasePhraseFragment extends BaseFragment {
    public int FRAGMENT_NAME_ID;
    public int THEME_STYLE_ID;
    public int STATUS_BAR_ID;
    public int IMAGE_BANNER_ID;
    public String SQL_TABLE_NAME;

    private List<PhraseEntity> listPhraseSearch;

    private Toolbar toolbar;
    private ImageView banner;
    private RelativeLayout contentSearch, listResult, dialogNoResult, backSearch;
    public FloatingActionButton fabSwitchMode;

    public ViewPager viewPager;
    public MyViewPagerAdapter pagerAdapter;

    private SearchView searchView;
    private SearchAdapter adapterSearch;
    private ListView lvSearch;


    protected abstract String getSqlTableName();

    protected abstract int getBannerID();

    protected abstract int getThemeID();

    protected abstract int getFragmentNameID();
    protected abstract int getStatusBarColor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQL_TABLE_NAME = getSqlTableName();
        IMAGE_BANNER_ID = getBannerID();
        FRAGMENT_NAME_ID = getFragmentNameID();
        THEME_STYLE_ID = getThemeID();
        STATUS_BAR_ID =getStatusBarColor();
        updateTheme();
    }

    public void updateTheme() {
        getBaseActivity().setTheme(THEME_STYLE_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().getWindow().setStatusBarColor(getResources().getColor(STATUS_BAR_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phrase, container, false);
        fabSwitchMode = (FloatingActionButton) rootView.findViewById(R.id.fab_switch_mode);

        lockNavigationView(false);
        setUpToolBar(rootView);
        setUpSearchView(rootView);
        setUpViewPager(rootView);
        setUpFloatingButton();
        return rootView;
    }

    private void setUpFloatingButton() {
        if (viewPager.getCurrentItem() == 0) {
            fabSwitchMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_module_white_24dp));
            fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(1);
                }
            });
        } else {
            fabSwitchMode.setImageDrawable(getResources().getDrawable(R.drawable.ic_view_list_white_24dp));
            fabSwitchMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }

    public void setUpViewPager(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.content_phrase);
        if (pagerAdapter == null) {
            pagerAdapter = new MyViewPagerAdapter(getBaseActivity(), this);
        }
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new TabletTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                setUpFloatingButton();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setUpToolBar(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        banner = (ImageView) rootView.findViewById(R.id.banner);
        toolbar.inflateMenu(R.menu.menu_phrase);
        if (toolbar.getTitle() == null) {
            toolbar.setTitle(getFragmentNameID());
            banner.setBackgroundResource(getBannerID());
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBaseActivity().getDrawerLayout().openDrawer(GravityCompat.START);
                }
            });
        }
    }

    public void setUpSearchView(View rootView) {
        contentSearch = (RelativeLayout) rootView.findViewById((R.id.content_search1));
        dialogNoResult = (RelativeLayout) rootView.findViewById((R.id.no_result));
        backSearch = (RelativeLayout) rootView.findViewById((R.id.back_search_view_listener));
        listResult = (RelativeLayout) rootView.findViewById((R.id.list_result));
        lvSearch = (ListView) rootView.findViewById(R.id.lv_search_result);
        lvSearch.setDivider(null);
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();

        setActionSearchViewQuery();
        setActionSearchViewExpaned();
        setActionSearchViewCollapsed();
        setLayoutContentSearchListener();
    }

    private void setLayoutContentSearchListener() {
        backSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSearch();
            }
        });

    }

    public void backSearch() {
        searchView.onActionViewCollapsed();
        contentSearch.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });
    }

    private void setActionSearchViewQuery() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().equals("")) {
                    dialogNoResult.setVisibility(View.GONE);
                    listResult.setVisibility(View.GONE);
                } else {
                    listPhraseSearch = DataSource.getInstance().getListSearchResutl(newText, SQL_TABLE_NAME);
                    adapterSearch = new SearchAdapter(getBaseActivity(), listPhraseSearch);
                    lvSearch.setAdapter(adapterSearch);

                    if (listPhraseSearch.size() > 0) {
                        dialogNoResult.setVisibility(View.GONE);
                        listResult.setVisibility(View.VISIBLE);
                    } else {
                        dialogNoResult.setVisibility(View.VISIBLE);
                        listResult.setVisibility(View.GONE);
                    }
                }
                lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("phrase", listPhraseSearch.get(position).phrase);
                        bundle.putString("phrase_kind", listPhraseSearch.get(position).sqlTableName);
                        bundle.putString("phrase_kind_name", getResources().getString(listPhraseSearch.get(position).kind_ID));
                        detailFragment.setArguments(bundle);
                        BaseFragment.replaceFragment(getBaseActivity().getFragmentManager(), detailFragment, getString(getFragmentNameID()), getString(getFragmentNameID()));

                    }
                });

                return false;

            }
        });
    }

    private void setActionSearchViewExpaned() {

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentSearch.setVisibility(View.VISIBLE);
                toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.onActionViewCollapsed();
                        contentSearch.setVisibility(View.GONE);
                        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getBaseActivity().getDrawerLayout().openDrawer(GravityCompat.START);
                            }
                        });
                    }
                });
            }
        });
    }

    private void setActionSearchViewCollapsed() {
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                contentSearch.setVisibility(View.GONE);
                toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBaseActivity().getDrawerLayout().openDrawer(GravityCompat.START);
                    }
                });
                return false;
            }
        });
    }


    public RelativeLayout getContentSearch() {
        return contentSearch;
    }


}
