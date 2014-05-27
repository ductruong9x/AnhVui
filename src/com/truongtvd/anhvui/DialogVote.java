package com.truongtvd.anhvui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogVote extends Dialog implements
		android.view.View.OnClickListener {
	Button btnOk, btnLater, btnNoVote;
	Context context;

	public DialogVote(Context context) {
		super(context, R.style.Dialog);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_dialog_vote);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnLater = (Button) findViewById(R.id.btnLater);
		btnNoVote = (Button) findViewById(R.id.btnNovote);
		btnOk.setOnClickListener(this);
		btnLater.setOnClickListener(this);
		btnNoVote.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences share = context.getSharedPreferences("SAVE",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = share.edit();
		switch (v.getId()) {
		case R.id.btnOk:
			Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri
					.parse("market://details?id=" + context.getPackageName()));
			context.startActivity(goToMarket);
			edit.putInt("VOTE", 6);
			edit.commit();
			dismiss();
			break;

		case R.id.btnLater:
			dismiss();
			break;

		case R.id.btnNovote:
			edit.putInt("VOTE", 6);
			edit.commit();
			dismiss();
			break;

		}

	}
}
