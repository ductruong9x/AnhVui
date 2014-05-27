package com.truongtvd.anhvui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.truongtvd.anhvui.adapter.DetailAdapter.ViewHolder;
import com.truongtvd.anhvui.util.AnimationUtil;

public class OnAvatarClickListener implements OnClickListener {

	private ViewHolder viewHolder;
	private boolean isOpen = false;

	public OnAvatarClickListener(ViewHolder viewHolder) {
		this.viewHolder = viewHolder;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int x = (int) viewHolder.btnAvatar.getX();
		
		int xmove = -x;
		if (isOpen == false) {
			viewHolder.btnComment.setAnimation(AnimationUtil
					.translateAnimation(x, 0, 0, 0));
			viewHolder.btnLike.setAnimation(AnimationUtil.translateAnimation(x,
					0, 0, 0));
			viewHolder.btnShare.setAnimation(AnimationUtil.translateAnimation(
					x, 0, 0, 0));
			viewHolder.btnComment.setVisibility(View.VISIBLE);
			viewHolder.btnLike.setVisibility(View.VISIBLE);
			viewHolder.btnShare.setVisibility(View.VISIBLE);
			isOpen = true;
		} else {
			viewHolder.btnComment.setAnimation(AnimationUtil
					.translateAnimation(0, xmove, 0, 0));
			viewHolder.btnLike.setAnimation(AnimationUtil.translateAnimation(0,
					xmove, 0, 0));
			viewHolder.btnShare.setAnimation(AnimationUtil.translateAnimation(
					0, xmove, 0, 0));
			viewHolder.btnComment.setVisibility(View.GONE);
			viewHolder.btnLike.setVisibility(View.GONE);
			viewHolder.btnShare.setVisibility(View.GONE);

			isOpen = false;
		}

	}

}
