package com.tatteam.languagerealm.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import com.tatteam.languagerealm.R;


/**
 * Created by Shu on 10/4/2015.
 */
public abstract class BaseFragment extends Fragment {
    private boolean isPhraseFragment;

    public static void replaceFragment(FragmentManager fragmentManager, BaseFragment newFragment, String fragmentTag,
                                       String transactionName) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit);
        transaction.replace(R.id.main_content, newFragment, fragmentTag);
        transaction.addToBackStack(transactionName);
        transaction.commit();
    }

    protected abstract boolean isPhraseFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void setTheme(int id) {
        getBaseActivity().setTheme(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void setNavHeaderColor(int id) {
        try {
            getBaseActivity().getBgHeader().setBackgroundColor(getResources().getColor(id));
        } catch (Exception ex) {
        }
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void onBackPressed() {
        getFragmentManager().popBackStack();

    }

    public void lockNavigationView(boolean lock) {
        getBaseActivity().lockNavigationView(lock);

    }

    public void makeSnackBar(int stringID) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.makeSnackBar(stringID);
    }
}
