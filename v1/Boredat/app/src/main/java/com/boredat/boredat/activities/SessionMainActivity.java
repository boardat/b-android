package com.boredat.boredat.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.fragments.InboxFragment;
import com.boredat.boredat.fragments.LoungeFragment;
import com.boredat.boredat.fragments.MoreInfoFragment;
import com.boredat.boredat.fragments.UserFragment;
import com.boredat.boredat.fragments.ZeitgeistFragment;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.presentation.SessionMain.ISessionMainPresenter;
import com.boredat.boredat.presentation.SessionMain.SessionMainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class SessionMainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Inject
    BoredatService mService;

    @Inject
    UserPreferences mUserPrefs;

    ISessionMainPresenter mPresenter;

    private int[] tabIcons = {
            R.drawable.ic_tab_lounge,
            R.drawable.ic_tab_zeitgeist,
            R.drawable.ic_tab_inbox,
            R.drawable.ic_tab_user,
            R.drawable.ic_tab_more_info
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_main);

        BoredatApplication.get(this).getSessionComponent().inject(this);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        getPresenter().start(new CompositeSubscription());
        getPresenter().getUserDetails();
    }

    private ISessionMainPresenter getPresenter() {
        if (mPresenter == null) {
            mPresenter = new SessionMainPresenter(mService, mUserPrefs);
        }

        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        getPresenter().finish();
    }





    private void setupTabIcons() {
        for (int i=0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LoungeFragment.newInstance(), "LOUNGE");
        adapter.addFragment(ZeitgeistFragment.newInstance(), "ZEITGEIST");
        adapter.addFragment(InboxFragment.newInstance(), "INBOX");
        adapter.addFragment(UserFragment.newInstance(), "USER");
        adapter.addFragment(MoreInfoFragment.newInstance(), "ABOUT");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
