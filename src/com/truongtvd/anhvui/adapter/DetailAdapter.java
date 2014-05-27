package com.truongtvd.anhvui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.truongtvd.anhvui.MyApplication;
import com.truongtvd.anhvui.R;
import com.truongtvd.anhvui.model.ItemNewFeed;
import com.truongtvd.anhvui.network.MyVolley;
import com.truongtvd.anhvui.view.FadeInNetworkImageView;
import com.truongtvd.anhvui.view.OnAvatarClickListener;
import com.truongtvd.anhvui.view.OnLikeClickListener;

public class DetailAdapter extends PagerAdapter implements OnClickListener {
	private Context context;
	private ImageLoader imgLoader;
	private DisplayImageOptions options;

	private LayoutInflater inflater;
	private View detailview;
	private ArrayList<ItemNewFeed> listNew = null;
	private ItemNewFeed item;
	private boolean isOpen = false;
	// ImageView btnAvatar;
	// ImageButton btnShare, btnComment, btnLike;
	private ViewHolder viewHolder;

	public DetailAdapter(Context context, ArrayList<ItemNewFeed> listNew) {
		this.context = context;
		this.listNew = listNew;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imgLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_default)
				.showImageOnFail(R.drawable.ic_default).cacheInMemory(true)
				.cacheOnDisc(false).displayer(new RoundedBitmapDisplayer(50))
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		imgLoader.init(config);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listNew.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		// TODO Auto-generated method stub
		return view == (arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		viewHolder = new ViewHolder();
		detailview = inflater.inflate(R.layout.layout_detail, container, false);
		item = listNew.get(position);
		viewHolder.btnAvatar = (ImageView) detailview
				.findViewById(R.id.btnAvatar);
		viewHolder.btnShare = (RelativeLayout) detailview
				.findViewById(R.id.btnShare);
		viewHolder.btnComment = (RelativeLayout) detailview
				.findViewById(R.id.btnComment);
		viewHolder.btnLike = (RelativeLayout) detailview
				.findViewById(R.id.btnLike);
		viewHolder.tvCountComment = (TextView) detailview
				.findViewById(R.id.tvCountComment);
		viewHolder.tvCountLike = (TextView) detailview
				.findViewById(R.id.tvCountLike);
		TextView tvDes = (TextView) detailview.findViewById(R.id.tvDes);
		FadeInNetworkImageView imgDetail = (FadeInNetworkImageView) detailview
				.findViewById(R.id.imgDetial);

		imgDetail.setImageUrl(item.getImage(), MyVolley.getImageLoader());
		imgLoader.displayImage(MyApplication.getAvater(), viewHolder.btnAvatar,
				options, new ImageLoadingListener() {

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
				});
		viewHolder.tvCountComment.setText(item.getComment_count() + "");
		viewHolder.tvCountLike.setText(item.getLike_count() + "");

		tvDes.setText(item.getMessage() + "");
		Linkify.addLinks(tvDes, Linkify.ALL);
		viewHolder.btnAvatar.setOnClickListener(new OnAvatarClickListener(
				viewHolder));
		viewHolder.btnShare.setOnClickListener(this);
		viewHolder.btnComment.setOnClickListener(this);
		viewHolder.btnLike.setOnClickListener(new OnLikeClickListener(context,
				viewHolder, item));
		((ViewPager) container).addView(detailview, 0);
		return detailview;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAvatar:
			MyApplication.showToast(context, "asasd");
			if (isOpen == false) {

				viewHolder.btnComment.setVisibility(View.VISIBLE);
				viewHolder.btnLike.setVisibility(View.VISIBLE);
				viewHolder.btnShare.setVisibility(View.VISIBLE);
				isOpen = true;
			} else {
				viewHolder.btnComment.setVisibility(View.GONE);
				viewHolder.btnLike.setVisibility(View.GONE);
				viewHolder.btnShare.setVisibility(View.GONE);
				isOpen = false;
			}

			break;
		case R.id.btnComment:

			break;
		case R.id.btnLike:

			break;
		case R.id.btnShare:

			break;
		}
	}

	public class ViewHolder {
		public ImageView btnAvatar;
		public RelativeLayout btnShare, btnComment, btnLike;
		public TextView tvCountLike, tvCountComment;
	}
}
