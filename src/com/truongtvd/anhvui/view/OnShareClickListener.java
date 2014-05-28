package com.truongtvd.anhvui.view;

import java.util.Arrays;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.truongtvd.anhvui.model.ItemNewFeed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OnShareClickListener implements OnClickListener {
	private Context context;
	private ItemNewFeed item;
	private ProgressDialog dialog;

	public OnShareClickListener(Context context, ItemNewFeed item) {
		this.context = context;
		this.item = item;
		dialog = new ProgressDialog(context);
		dialog.setMessage("Sharing...");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			if (!Session.getActiveSession().getPermissions()
					.contains("publish_actions")) {
				NewPermissionsRequest request = new NewPermissionsRequest(
						(Activity) context, Arrays.asList("publish_actions"));

				Session.getActiveSession()
						.requestNewPublishPermissions(request);
				return;
			}
		} catch (Exception e) {

		}
		dialog.show();
		Bundle postParams = new Bundle();
		postParams.putString("name", "Ảnh vui");
		postParams.putString("message", item.getImage());
		postParams.putString("description", item.getMessage());
		postParams
				.putString("link",
						"https://play.google.com/store/apps/details?id=com.truongtvd.anhvui");
		postParams.putString("picture", item.getImage());

		Request.Callback callback = new Request.Callback() {
			public void onCompleted(Response response) {
				dialog.dismiss();
				Toast.makeText(context, "Share successfuly	",
						Toast.LENGTH_SHORT).show();
			}
		};

		Request request = new Request(Session.getActiveSession(), "me/feed",
				postParams, HttpMethod.POST, callback);

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}

}
