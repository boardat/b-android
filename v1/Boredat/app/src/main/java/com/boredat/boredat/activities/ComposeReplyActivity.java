package com.boredat.boredat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boredat.boredat.R;

public class ComposeReplyActivity extends AppCompatActivity {
    public static String KEY_POST_ID = "postId";
    private long mPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_reply);

        // Get post id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPostId = bundle.getLong(KEY_POST_ID);
        }

    }
}
