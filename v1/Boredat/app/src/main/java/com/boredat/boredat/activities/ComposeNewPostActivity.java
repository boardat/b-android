package com.boredat.boredat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.boredat.boredat.util.oauth.BuildOauthAuthorize;
import com.boredat.boredat.R;

public class ComposeNewPostActivity extends AppCompatActivity {
    private EditText mEditText;
    private Switch mPersonalitySwitch;
    private Switch mLocationSwitch;
    private Button mPostButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        mEditText = (EditText) findViewById(R.id.action_create_new_post_text);
        mPersonalitySwitch = (Switch) findViewById(R.id.action_create_new_post_switch_personality);
        mLocationSwitch = (Switch) findViewById(R.id.action_create_new_post_switch_location);
        mPostButton = (Button) findViewById(R.id.action_create_new_post_button_post);

        // Handle Post button clicks
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mLocation = 0;
                if (mLocationSwitch.isChecked()) {
                    // TODO get the location id
                }
                // TODO personality switch


                String mText = mEditText.getText().toString();
                String paramText = BuildOauthAuthorize.encode(mText);

                /* TODO: POST PARAMTEXT TO MAIN FEED
                * */

            }
        });
    }
}
