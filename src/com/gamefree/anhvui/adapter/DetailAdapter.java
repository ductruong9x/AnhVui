package com.gamefree.anhvui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamefree.anhvui.MyApplication;
import com.gamefree.anhvui.R;
import com.gamefree.anhvui.model.ItemNewFeed;
import com.gamefree.anhvui.network.MyVolley;
import com.gamefree.anhvui.network.NetworkOperator;
import com.gamefree.anhvui.view.FadeInNetworkImageView;
import com.gamefree.anhvui.view.OnAvatarClickListener;
import com.gamefree.anhvui.view.OnCloseClickListener;
import com.gamefree.anhvui.view.OnCommentClickListener;
import com.gamefree.anhvui.view.OnLikeClickListener;
import com.gamefree.anhvui.view.OnSendClickListener;
import com.gamefree.anhvui.view.OnShareClickListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DetailAdapter extends PagerAdapter implements OnClickListener {
	private Context context;
	private ImageLoader imgLoader;
	private DisplayImageOptions options;

	private LayoutInflater inflater;
	private View detailview;
	private ArrayList<ItemNewFeed> listNew = null;
	private ItemNewFeed item;
	private boolean isOpen = false;
	private NetworkOperator operator;
	private ViewHolder viewHolder;
	private ProgressDialog dialog;
	private InterstitialAd interstitial;
	private String MY_AD_UNIT_ID = "ca-app-pub-6063844612770322/7329740094";

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
		operator = NetworkOperator.getInstance().init(context);
		dialog = new ProgressDialog(context);
		dialog.setMessage("Sharing...");
		interstitial = new InterstitialAd((Activity) context, MY_AD_UNIT_ID);
		AdRequest request = new AdRequest();
		interstitial.loadAd(request);
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
		viewHolder.comment_detail = (RelativeLayout) detailview
				.findViewById(R.id.comment_detail);
		viewHolder.btnClose = (ImageButton) detailview
				.findViewById(R.id.btnCloseComment);
		viewHolder.load = (ProgressBar) detailview.findViewById(R.id.load);
		viewHolder.lvListComment = (ListView) detailview
				.findViewById(R.id.lvListComment);
		viewHolder.imgMyAvatar = (ImageView) detailview
				.findViewById(R.id.imgMyAvatar);
		viewHolder.edComment = (EditText) detailview
				.findViewById(R.id.edComment);
		viewHolder.btnSend = (Button) detailview.findViewById(R.id.btnSend);

		if (position % 10 == 0) {
			interstitial.show();
		}
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
		viewHolder.btnShare.setOnClickListener(new OnShareClickListener(
				context, item));
		viewHolder.btnComment.setOnClickListener(new OnCommentClickListener(
				context, viewHolder, item, operator, imgLoader, options));
		viewHolder.btnLike.setOnClickListener(new OnLikeClickListener(context,
				viewHolder, item));
		viewHolder.btnClose.setOnClickListener(new OnCloseClickListener(
				viewHolder));
		viewHolder.btnSend.setOnClickListener(new OnSendClickListener(context,
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

		}
	}

	public class ViewHolder {
		public ImageView btnAvatar;
		public RelativeLayout btnShare, btnLike, comment_detail;
		public TextView tvCountLike, tvCountComment;
		public RelativeLayout btnComment;
		public ImageButton btnClose;
		public ProgressBar load;
		public ListView lvListComment;
		public ImageView imgMyAvatar;
		public EditText edComment;
		public Button btnSend;
	}

}
