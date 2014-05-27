package com.truongtvd.anhvui.view;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.truongtvd.anhvui.R;
import com.truongtvd.anhvui.adapter.CommentItemAdapter;
import com.truongtvd.anhvui.adapter.DetailAdapter.ViewHolder;
import com.truongtvd.anhvui.model.CommentInfo;
import com.truongtvd.anhvui.model.ItemNewFeed;
import com.truongtvd.anhvui.network.NetworkOperator;
import com.truongtvd.anhvui.util.AnimationUtil;
import com.truongtvd.anhvui.util.JsonUtils;

public class OnCommentClickListener implements OnClickListener {

	private ViewHolder viewHolder;
	private ItemNewFeed item;
	public static boolean isOpen = false;
	private NetworkOperator operator;
	public static ArrayList<CommentInfo> listcomment = new ArrayList<CommentInfo>();
	public static CommentItemAdapter adapter;
	private Context context;

	public OnCommentClickListener(Context context, ViewHolder viewHolder,
			ItemNewFeed item, NetworkOperator operator) {
		this.context = context;
		this.viewHolder = viewHolder;
		this.item = item;
		this.operator = operator;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isOpen == false) {
			viewHolder.comment_detail.setAnimation(AnimationUtil
					.translateAnimation(0, 0, -1500, 0));
			viewHolder.comment_detail.setVisibility(View.VISIBLE);
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

}
