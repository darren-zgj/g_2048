package com.example.g_2048;

import com.example.g_2048.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Button btnShare;
	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		root = (LinearLayout) findViewById(R.id.container);
		root.setBackgroundColor(0xfffaf8ef);

		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);

		gameView = (GameView) findViewById(R.id.gameView);

		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameView.startGame();
			}
		});
		btnShare = (Button) findViewById(R.id.BtnShare);
		btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//new ScreenShot().shoot(MainActivity.this,new File("/data/12.png"));
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			    sharingIntent.setType("text/plain");
			    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "分享");
			    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "我现在的2048成绩是："+tvBestScore.getText()+",你来挑战我啊.游戏在这里下载哦：http://www.baidu.com");
		
			    startActivity(Intent.createChooser(sharingIntent, "分享到"));
			}
		});
			    
		animLayer = (AnimLayer) findViewById(R.id.animLayer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void showScore() {
		tvScore.setText(score + "");
	}

	public void addScore(int s) {
		score += s;
		showScore();

		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}

	public void saveBestScore(int s) {
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();
	}

	public int getBestScore() {
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}

	public void showBestScore(int s) {
		tvBestScore.setText(s + "");
	}

	public AnimLayer getAnimLayer() {
		return animLayer;
	}

	private int					score			= 0;
	private TextView			tvScore, tvBestScore;
	private LinearLayout		root			= null;
	private Button				btnNewGame;
	private GameView			gameView;
	private AnimLayer			animLayer		= null;

	private static MainActivity	mainActivity	= null;

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public static final String	SP_KEY_BEST_SCORE	= "bestScore";

}
