/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kinvey.statusshare.ui;

import java.io.Serializable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kinvey.KCSClient;
import com.kinvey.statusshare.R;
import com.kinvey.statusshare.StatusShareApp;

/**
 * Base sherlock activity
 */
public class BaseActivity extends SherlockActivity {

    protected KCSClient mKinveyClient;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKinveyClient = ((StatusShareApp) getApplication()).getKinveyService();

    }

    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

	protected void showAboutDialog() {
		new AlertDialog.Builder(this)
		.setTitle(R.string.title_about)
		.setMessage(Html.fromHtml(getString(R.string.about_msg)))
		.setPositiveButton(R.string.about_ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		})
		.create()
		.show();
	}
		
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        	
			case R.id.menu_about:
				showAboutDialog();
				return(true);
				
			case R.id.menu_sign_out:
		        mKinveyClient.getActiveUser().logout();
		        Intent intent = new Intent(this, LoginActivity.class);
		        intent.putExtra(LoginActivity.LOGGED_OUT, true);
		        startActivity(intent);
		        finish();
				return(true);
		}
				
		return super.onOptionsItemSelected(item);
	}
	
}