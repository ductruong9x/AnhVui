package com.truongtvd.anhvui;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.truongtvd.anhvui.adapter.DetailAdapter;
import com.truongtvd.anhvui.model.ItemNewFeed;
import com.truongtvd.anhvui.network.NetworkOperator;
import com.truongtvd.anhvui.util.JsonUtils;

public class MainActivity extends SherlockActivity {
	private ActionBar actionBar;
	private ViewPager vpMain;
	private DetailAdapter adapter;
	private ArrayList<ItemNewFeed> listNew = new ArrayList<ItemNewFeed>();
	private NetworkOperator operator;
	private Session session;
	private ProgressBar loading;
	private String id, avatar, nameUser;
	private AdView adView;
	private ImageButton btnInvate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		session = Session.getActiveSession();
		operator = NetworkOperator.getInstance().init(this);
		vpMain = (ViewPager) findViewById(R.id.vpMain);
		vpMain.setOffscreenPageLimit(1);
		btnInvate = (ImageButton) findViewById(R.id.btnInvate);
		loading = (ProgressBar) findViewById(R.id.loading);
		adView = (AdView) findViewById(R.id.adFragment);
		adView.loadAd(new AdRequest());
//		try {
//			if (!Session.getActiveSession().getPermissions()
//					.contains("publish_actions")) {
//				NewPermissionsRequest request = new NewPermissionsRequest(this,
//						Arrays.asList("publish_actions"));
//				request.setCallback(new StatusCallback() {
//
//					@Override
//					public void call(Session session, SessionState state,
//							Exception exception) {
//						// TODO Auto-generated method stub
//						Log.e("PER", session.getPermissions().toString());
//					}
//				});
//				Session.getActiveSession()
//						.requestNewPublishPermissions(request);
//
//			}
//		} catch (Exception e) {
//
//		}
		getIDUser();
		btnInvate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendRequestDialog();
			}
		});
	}

	private void sendRequestDialog() {
		Bundle params = new Bundle();
		params.putString("message", "Sử dụng Ảnh VL để vui hơn nhé");

		WebDialog requestsDialog = (new WebDialog.RequestsDialogBuilder(
				MainActivity.this, Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error != null) {
							if (error instanceof FacebookOperationCanceledException) {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										"Request cancelled", Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										"Network Error", Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							final String requestId = values
									.getString("request");
							if (requestId != null) {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										"Request sent", Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(
										MainActivity.this
												.getApplicationContext(),
										"Request cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						}
					}

				}).build();
		requestsDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	private void getIDUser() {
		Request request = Request.newMeRequest(session,
				new GraphUserCallback() {

					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						try {
							id = user.getId();
							getUserInfo(id);
						} catch (Exception e) {

						}
					}
				});
		Request.executeBatchAsync(request);
	}

	private void getUserInfo(String id) {
		String fqlQuery = "SELECT name,pic FROM user WHERE uid='" + id + "'";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);

		// session = Session.getActiveSession();
		Request request = new Request(session, "/fql", params, HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {
						JSONObject jso = JsonUtils.parseResponToJson(response);
						try {
							JSONArray data = jso.getJSONArray("data");
							if (data.length() > 0) {
								JSONObject info = data.getJSONObject(0);
								avatar = info.getString("pic");
								nameUser = info.getString("name");
								MyApplication.setAvater(avatar);
								MyApplication.setName(nameUser);
								Log.e("AVATAR", MyApplication.getAvater() + "");
								Log.e("USERNAME", MyApplication.getName() + "");
								getNewFeed();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Log.e("USER_INFO", jso.toString());

					}
				});
		Request.executeBatchAsync(request);

	}

	private void getNewFeed() {
		operator.getNewFeed(300, getSuccess(), getError());
	}

	private Listener<JSONObject> getSuccess() {
		// TODO Auto-generated method stub
		return new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response, String extraData) {
				// TODO Auto-generated method stub
				loading.setVisibility(View.GONE);
				Log.e("NEW", response.toString());
				listNew = JsonUtils.getListItem(response, listNew);
				adapter = new DetailAdapter(MainActivity.this, listNew);
				vpMain.setAdapter(adapter);
			}
		};
	}

	private ErrorListener getError() {
		// TODO Auto-generated method stub
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error, String extraData) {
				// TODO Auto-generated method stub
				loading.setVisibility(View.GONE);
				MyApplication.showToast(MainActivity.this, "Error");
				error.printStackTrace();
			}
		};
	}

	public void danhGia() {
		SharedPreferences getPre = getSharedPreferences("SAVE", MODE_PRIVATE);
		int i = getPre.getInt("VOTE", 0);
		SharedPreferences pre;
		SharedPreferences.Editor edit;
		switch (i) {
		case 0:
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", 1);
			edit.commit();
			break;
		case 1:
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", i + 1);
			edit.commit();
			break;
		case 2:
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", i + 1);
			edit.commit();
			break;
		case 3:
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", i + 1);
			edit.commit();
			break;
		case 4:
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", i + 1);
			edit.commit();
			break;
		case 5:
			DialogVote dialog = new DialogVote(MainActivity.this);
			dialog.show();
			pre = getSharedPreferences("SAVE", MODE_PRIVATE);
			edit = pre.edit();
			edit.putInt("VOTE", 5);
			edit.commit();
			break;
		}
	}
}
