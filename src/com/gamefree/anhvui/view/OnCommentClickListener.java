package com.gamefree.anhvui.view;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.gamefree.anhvui.MyApplication;
import com.gamefree.anhvui.R;
import com.gamefree.anhvui.adapter.CommentItemAdapter;
import com.gamefree.anhvui.adapter.DetailAdapter.ViewHolder;
import com.gamefree.anhvui.model.CommentInfo;
import com.gamefree.anhvui.model.ItemNewFeed;
import com.gamefree.anhvui.network.NetworkOperator;
import com.gamefree.anhvui.util.AnimationUtil;
import com.gamefree.anhvui.util.JsonUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class OnCommentClickListener implements OnClickListener {

	private ViewHolder viewHolder;
	private ItemNewFeed item;
	public static boolean isOpen = false;
	private NetworkOperator operator;
	public static ArrayList<CommentInfo> listcomment = new ArrayList<CommentInfo>();
	public static CommentItemAdapter adapter;
	private Context context;
	private ImageLoader imgLoader;
	private DisplayImageOptions options;

	public OnCommentClickListener(Context context, ViewHolder viewHolder,
			ItemNewFeed item, NetworkOperator operator, ImageLoader imgLoader,
			DisplayImageOptions options) {
		this.context = context;
		this.viewHolder = viewHolder;
		this.item = item;
		this.operator = operator;
		this.imgLoader = imgLoader;
		this.options = options;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isOpen == false) {
			viewHolder.comment_detail.setAnimation(AnimationUtil
					.translateAnimation(0, 0, -1500, 0));
			viewHolder.comment_detail.setVisibility(View.VISIBLE);
			imgLoader.displayImage(MyApplication.getAvater(),
					viewHolder.imgMyAvatar, options, imageload);

			getListCommemt();

			isOpen = true;
		} else {
			viewHolder.comment_detail.setAnimation(AnimationUtil
					.translateAnimation(0, 0, 0, -1500));
			viewHolder.comment_detail.setVisibility(View.GONE);
			deleteComment();
			isOpen = false;
		}

	}

	private void getListCommemt() {
		operator.getNewsFeedComments(item.getPost_id(), 300, getListSuccess(),
				getListError());

	}

	private ErrorListener getListError() {
		// TODO Auto-generated method stub
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error, String extraData) {
				// TODO Auto-generated method stub
				error.printStackTrace();
			}
		};
	}

	private Listener<JSONObject> getListSuccess() {
		// TODO Auto-generated method stub
		return new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response, String extraData) {
				// TODO Auto-generated method stub
				viewHolder.load.setVisibility(View.GONE);
				listcomment = JsonUtils.getCommentInfo(response, listcomment);
				adapter = new CommentItemAdapter(context,
						R.layout.item_comment, listcomment);
				viewHolder.lvListComment.setAdapter(adapter);

			}
		};
	}

	public static void deleteComment() {
		listcomment.clear();
		adapter.notifyDataSetChanged();
	}

	ImageLoadingListener imageload = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String imageUri, View view) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			// TODO Auto-generated method stub

		}
	};
}
