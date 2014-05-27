package com.truongtvd.anhvui.view;

import java.util.Arrays;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.truongtvd.anhvui.MyApplication;
import com.truongtvd.anhvui.adapter.DetailAdapter.ViewHolder;
import com.truongtvd.anhvui.model.CommentInfo;
import com.truongtvd.anhvui.model.ItemNewFeed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class OnSendClickListener implements OnClickListener {
	private Context context;
	private ViewHolder viewHolder;
	private ItemNewFeed item;

	public OnSendClickListener(Context context, ViewHolder viewHolder,
			ItemNewFeed item) {
		this.context = context;
		this.viewHolder = viewHolder;
		this.item = item;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new CommentFacebook().execute(item.getPost_id());
	}

	private class CommentFacebook extends AsyncTask<String, Void, Void> {
		private ProgressDialog dialogComment;

		public CommentFacebook() {
			dialogComment = new ProgressDialog(context);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// dialog.dismiss();
			CommentInfo comment = new CommentInfo();
			int unixTime = (int) (System.currentTimeMillis() / 1000);
			comment.setAvatar(MyApplication.getAvater());
			comment.setComment(viewHolder.edComment.getText().toString() + "");
			comment.setTime(unixTime);
			comment.setUsername(MyApplication.getName());
			OnCommentClickListener.listcomment.add(comment);
			OnCommentClickListener.adapter.notifyDataSetChanged();

			viewHolder.edComment.setText("");
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(viewHolder.edComment.getWindowToken(),
					0);
			dialogComment.dismiss();
			Toast.makeText(context, "Comment successfuly", Toast.LENGTH_SHORT)
					.show();
			viewHolder.lvListComment
					.setSelection(OnCommentClickListener.listcomment.size() - 1);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialogComment.setMessage("Posting....");
			dialogComment.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				if (!Session.getActiveSession().getPermissions()
						.contains("publish_actions")) {
					NewPermissionsRequest request = new NewPermissionsRequest(
							(Activity) context,
							Arrays.asList("publish_actions"));

					Session.getActiveSession().requestNewPublishPermissions(
							request);
					return null;
				}
			} catch (Exception e) {

			}
			Bundle bundle = new Bundle();
			bundle.putString("message", viewHolder.edComment.getText()
					.toString() + "");
			Request commentRequest = new Request(Session.getActiveSession(),
					params[0] + "/comments", bundle, HttpMethod.POST,
					new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							Log.i("Comment", response.toString());
						}
					});
			Request.executeBatchAndWait(commentRequest);

			return null;
		}

	}
}
