package com.boredat.boredat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.boredat.boredat.R;
import com.boredat.boredat.fragments.DetailPostFragment;

public class DetailPostActivity extends AppCompatActivity {
    public static String KEY_POST_ID = "postId";
    public static String KEY_FEED_ID = "feedId";


    private long mPostId;
    private int mFeedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        // Get post id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPostId = bundle.getLong(KEY_POST_ID);
            mFeedId = bundle.getInt(KEY_FEED_ID);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setup title
        String title = String.format("Post #%d", mPostId);
        toolbar.setTitle(title);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToComposeReplyActivity();


            }
        });


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DetailPostFragment.newInstance(mFeedId, mPostId))
                .commit();
    }


    private void navigateToComposeReplyActivity() {
        Intent intent = new Intent(this, ComposeReplyActivity.class);
        intent.putExtra(ComposeReplyActivity.KEY_POST_ID, mPostId);
        intent.putExtra(ComposeReplyActivity.KEY_FEED_ID, mFeedId);
        startActivity(intent);
    }

}
