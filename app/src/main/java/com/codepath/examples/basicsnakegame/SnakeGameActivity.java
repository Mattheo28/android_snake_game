package com.codepath.examples.basicsnakegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SnakeGameActivity extends com.codepath.simplegame.GameActivity {

	Button btnRestart;
	Button btnShare;
	View gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		gameView = new SnakeGamePanel(this);
		FrameLayout gameFrame = new FrameLayout(this);
		RelativeLayout GameButtons = new RelativeLayout(this);
		btnRestart = new Button(this);
		btnRestart.setText(R.string.restart);
		btnShare = new Button(this);
		btnShare.setText(R.string.share);
		btnRestart.setTag("btnRestart");
		btnRestart.setBackgroundColor(Color.WHITE);
		btnRestart.setWidth(300);
		btnRestart.setId(1);
		btnShare.setTag("btnShare");
		btnShare.setBackgroundColor(Color.WHITE);
		btnShare.setWidth(300);
		btnShare.setId(2);

		RelativeLayout.LayoutParams buttonParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		RelativeLayout.LayoutParams buttonParams2 =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams layoutParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);

		GameButtons.setLayoutParams(layoutParams);
		GameButtons.addView(btnRestart);
		GameButtons.addView(btnShare);
		buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		buttonParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		buttonParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		buttonParams2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		buttonParams.setMargins(5, 5, 5, 5);
		buttonParams2.setMargins(5, 5, 5, 5);
		btnRestart.setLayoutParams(buttonParams);
		buttonParams2.addRule(RelativeLayout.BELOW, btnRestart.getId());
		btnShare.setLayoutParams(buttonParams2);
		gameFrame.addView(gameView);
		gameFrame.addView(GameButtons);
        setTheme(R.style.AppTheme);

		switchFullscreen();
		setContentView(gameFrame);

		btnRestart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((SnakeGamePanel) gameView).onStart();
			}
		});

		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT,"I just got a score of " +
						((SnakeGamePanel) gameView).getScore() + " in snake!");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My snake score");
				startActivity(Intent.createChooser(shareIntent, "Share..."));
			}
		});
	}

	void makeButtonsVisible() {
		if (btnRestart.getVisibility() == View.GONE) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					btnRestart.setVisibility(View.VISIBLE);
					btnShare.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	void makeButtonsInvisible() {
		if (btnRestart.getVisibility() == View.VISIBLE) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					btnRestart.setVisibility(View.GONE);
					btnShare.setVisibility(View.GONE);
				}
			});
		}
	}
}
