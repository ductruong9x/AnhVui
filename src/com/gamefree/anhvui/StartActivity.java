package com.gamefree.anhvui;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class StartActivity extends SherlockActivity {
	private LoginButton btnLogin;
	private UiLifecycleHelper uiHelper;
	private StatusCallback mCallBack = new StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		uiHelper = new UiLifecycleHelper(this, mCallBack);
		uiHelper.onCreate(savedInstanceState);
		btnLogin = (LoginButton) findViewById(R.id.authButton);
		btnLogin.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		// btnLogin.setPublishPermissions(Arrays.asList("publish_actions"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
			Session.setActiveSession(session);
			finish();
		}

		uiHelper.onResume();
	}
}
