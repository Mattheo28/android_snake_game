package com.codepath.examples.basicsnakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;

import com.codepath.simplegame.AbstractGamePanel;

public class SnakeGamePanel extends AbstractGamePanel {

	Context context;

	public SnakeGamePanel(Context context) {
		super(context);
		this.context = context;
	}

	private SnakeActor snake;
	private AppleActor apple;
	private ScoreBoard score;
	private boolean isPaused = false;

	@Override
	public void onStart() {
		this.snake = new SnakeActor(100, 100);
		this.apple = new AppleActor(200, 50);
		this.score = new ScoreBoard(this);
	}

	@Override
	public void onTimer() {
		if (!isPaused) {
			if (this.snake.checkBoundsCollision(this)) {
				this.snake.setEnabled(false);
			}
			this.snake.move();
			if (this.apple.intersect(this.snake)) {
				this.snake.grow();
				this.score.earnPoints(50);
				this.apple.reposition(this);
			}
		}
	}

	@Override
	public void redrawCanvas(Canvas canvas) {
		Button btnRestart = (Button) this.getRootView().findViewById(1);
		Button btnShare = (Button) this.getRootView().findViewById(2);
		if (this.snake.isEnabled()) {
			this.snake.draw(canvas);
			this.apple.draw(canvas);
			this.score.draw(canvas);
			if (btnRestart != null && btnShare != null)
				((SnakeGameActivity) context).makeButtonsInvisible();
		} else {
			Paint p = getPaint();
			p.setTextSize(100);
			p.setColor(Color.RED);
			canvas.drawText("Game over!", getWidth()/4, getHeight()/3, p);
			canvas.drawText("Score: " + score.getScore(), getWidth()/4, getHeight()/3 + 100, p);
			if (btnRestart != null && btnShare != null)
				((SnakeGameActivity) context).makeButtonsVisible();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		this.snake.handleKeyInput(keyCode);
		if (keyCode == KeyEvent.KEYCODE_G) {
			this.onStart();
		}
		if (keyCode == KeyEvent.KEYCODE_P) {
			isPaused = !isPaused;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			this.snake.handleTouchInput(event);
			return true;
		}
		return false;
	}

	int getScore() {
		return score.getScore();
	}

}
