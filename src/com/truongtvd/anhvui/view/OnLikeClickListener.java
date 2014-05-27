package com.truongtvd.anhvui.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.truongtvd.anhvui.adapter.DetailAdapter.ViewHolder;
import com.truongtvd.anhvui.model.ItemNewFeed;

public class OnLikeClickListener implements OnClickListener {
	private ViewHolder viewHolder;
	private boolean isLike = false;
	private ItemNewFeed item;
	private Context context;

	public OnLikeClickListener(Context context, ViewHolder viewHolder,
			ItemNewFeed item) {
		this.viewHolder = viewHolder;
		this.item = item;
		this.context = context;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int like = item.getLike_count();
		if (isLike == false) {
			int lik = like + 1;
			new LikeFacebook().execute(item.getPost_id());
			viewHolder.tvCountLike.setText(lik + "");
			item.setLike_count(lik);
			isLike = true;
		} else {
			int unlike = item.getLike_count() - 1;
			viewHolder.tvCountLike.setText(unlike + "");
			item.setLike_count(unlike);
			isLike = false;
		}

	}

	private class LikeFacebook extends AsyncTask<String, Void, Void> {

		public LikeFacebook() {

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Toast.makeText(context, "Like sucessfuly", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			Request likeRequest = new Request(Session.getActiveSession(),
					params[0] + "/likes", null, HttpMethod.POST,
					new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							Log.i("LIKE", response.toString());
						}
					});
			Request.executeBatchAndWait(likeRequest);
			return null;
		}

	}

}