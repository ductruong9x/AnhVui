package com.gamefree.anhvui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamefree.anhvui.R;
import com.gamefree.anhvui.model.CommentInfo;
import com.gamefree.anhvui.util.Util;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CommentItemAdapter extends ArrayAdapter<CommentInfo> {

	private Context context;
	private ArrayList<CommentInfo> listInfo = null;
	private ImageLoader imvLoader;
	private DisplayImageOptions options;
	private Typeface font_light;
	private SharedPreferences sharedPreferences;
	private boolean isLoad;

	public CommentItemAdapter(Context context, int resource,
			ArrayList<CommentInfo> listInfo) {
		super(context, resource, listInfo);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listInfo = listInfo;
		imvLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_default)
				.showImageOnFail(R.drawable.ic_default).cacheInMemory(true)
				.cacheOnDisc(false).displayer(new RoundedBitmapDisplayer(30))
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		imvLoader.init(config);
		font_light = Typeface.createFromAsset(context.getAssets(),
				"fonts/Roboto-Light.ttf");
		sharedPreferences = context.getSharedPreferences("SETTING",
				Context.MODE_PRIVATE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		isLoad = sharedPreferences.getBoolean("LOAD", true);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_comment, null);
			viewHolder = new ViewHolder();
			viewHolder.imgAvatar = (ImageView) convertView
					.findViewById(R.id.imgAvatar);
			viewHolder.tvComment = (TextView) convertView
					.findViewById(R.id.tvComment);
			viewHolder.tvUsername = (TextView) convertView
					.findViewById(R.id.tvUser);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tvTimeComment);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CommentInfo commemt = getItem(position);

		if (isLoad) {
			imvLoader.displayImage(commemt.getAvatar(), viewHolder.imgAvatar,
					options, imageload);
		}
		viewHolder.tvComment.setText(commemt.getComment() + "");
		viewHolder.tvComment.setTypeface(font_light);
		viewHolder.tvUsername.setText(commemt.getUsername() + "");
		viewHolder.tvTime.setText(Util.convertDate(commemt.getTime()));
		return convertView;
	}

	private ImageLoadingListener imageload = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String imageUri, View view) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoadingFailed(String imageUri, View view,
				FailReason failReason) {
			// TODO Auto-generated method stub
			// Log.e("ERROR", "load eror");

		}

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			// TODO Auto-generated method stub
			// btnMenu.setImageBitmap(loadedImage);
			// Log.e("COM", "load com");
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			// TODO Auto-generated method stub

		}
	};

	private class ViewHolder {
		private ImageView imgAvatar;
		private TextView tvUsername, tvComment, tvTime;

	}

}
