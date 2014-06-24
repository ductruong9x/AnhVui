package com.gamefree.anhvui.view;

import com.gamefree.anhvui.adapter.DetailAdapter.ViewHolder;
import com.gamefree.anhvui.util.AnimationUtil;

import android.view.View;
import android.view.View.OnClickListener;

public class OnCloseClickListener implements OnClickListener {

	private ViewHolder viewHolder;

	public OnCloseClickListener(ViewHolder viewHolder) {
		this.viewHolder = viewHolder;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		viewHolder.comment_detail.setAnimation(AnimationUtil
				.translateAnimation(0, 0, 0, -1500));
		viewHolder.comment_detail.setVisibility(View.GONE);
		OnCommentClickListener.deleteComment();
		OnCommentClickListener.isOpen = false;
	}

}
