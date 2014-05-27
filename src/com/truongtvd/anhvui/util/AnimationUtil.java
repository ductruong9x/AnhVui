package com.truongtvd.anhvui.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {
	public static Animation translateAnimation(int fromX, int toX, int fromY,
			int toY) {
		Animation animation = new TranslateAnimation(fromX, toX, fromY, toY);
		animation.setDuration(400);
		return animation;
	}
}
