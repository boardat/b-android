package com.boredat.boredatdroid.activities;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.boredat.boredatdroid.CreateNewPost.CreateNewPostFragment;
import com.boredat.boredatdroid.Feed.FeedFragment;
import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.network.UserSessionManager;


public class MainActivity extends ActionBarActivity
        implements FeedFragment.OnFragmentInteractionListener, CreateNewPostFragment.OnFragmentInteractionListener  {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout mLinearLayout;
    private RelativeLayout mRelativeLayout;
    private Toolbar mToolbar;
    private NetworkImageView mNetworkImageView;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ProgressDialog mProgressDialog;

    UserSessionManager sessionManager;

    //Refactor the name of this
    private String[] mNavigationItems;

    @Override
    // - Override the required methods of LocalCardBoardFragment's interface
    // - Controls what happens when tapping on a generic_content_item_list item
    public void onFragmentInteraction(String id){
        if (id == null) {
            navigateToLocalBoard();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new UserSessionManager(this);

        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if (checkSessionLogin())
            finish();

        setupToolbar(savedInstanceState);
    }

    private void setupToolbar(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();
        mNavigationItems = getResources().getStringArray(R.array.navigation_items);
        /* might want to send this as an intent or somethin else, unsure, sleepy rn */
        mNavigationItems[0] = sessionManager.getLocalBoardTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.content_frame);
        mToolbar = (Toolbar) findViewById(R.id.alternate_toolbar);
        setSupportActionBar(mToolbar);

        // Fiddle around with toolbar stuff which might not be compatible with versions older than 5.0 (oops)
        // mDrawerLayout.setStatusBarBackground();
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,               /* Toolbar object*/
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerSlide(drawerView,0); //Disable arrow
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerSlide(View drawerView, float slideOffset){
                super.onDrawerSlide(drawerView,0); //Disable hamburger animation
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Go to Local Board by default
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (checkSessionLogin())
            finish();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if (checkSessionLogin())
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLinearLayout);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // this could get hairy
        if (checkSessionLogin())
            finish();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // Logout user
        if (id == R.id.action_logout) {
            // Clear the User session data
            // and redirect user to LoginActivity
            sessionManager.logoutUser();
            finish();
            return true;
        }

        // Create new post
        if (id == R.id.action_create_new_post) {
            navigateToCreateNewPost();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments

        // case position=0
        navigateToLocalBoard();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);

        setTitle(mNavigationItems[position]);
        mDrawerLayout.closeDrawer(mLinearLayout);
    }

    private void navigateToLocalBoard() {
        FeedFragment fragment = new FeedFragment().newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void navigateToCreateNewPost() {
        CreateNewPostFragment fragment = new CreateNewPostFragment().newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /* TODO refactor current progress showing method to conform to this one
    * probs by moving those methods from FeedView interface to a MainBoardView interface that MainActivity implements...
     **/
    protected void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg);
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void checkSessionExists() {
        if (sessionManager == null) {
            // Session class instance
            sessionManager = new UserSessionManager(getApplicationContext());
        }
    }

    private boolean checkSessionLogin() {
        checkSessionExists();
        return sessionManager.checkLogin();
    }
}