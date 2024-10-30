package bubble.test.ex18;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameTimer extends JPanel {
	private BubbleFrame mContext;
	private javax.swing.Timer gameTimer;
	private int remainingTime = 30;
	private JLabel timerLabel;
	private GameOver gameOver;
	private Player player;
	private List<Enemy> enemys;

	public GameTimer() {
		this.mContext = null; // 또는 필요에 따라 초기값을 설정
		setOpaque(false);
		initTimer();
		initTimerLabel();
	}

	public GameTimer(BubbleFrame context) {
		this();
		this.mContext = context;
		this.player = mContext.getPlayer();
		this.enemys = mContext.getEnemys();
	}

	private void initTimer() {
		int delay = 1000;
		gameTimer = new javax.swing.Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTimer();
			}
		});
		gameTimer.start();
	}

	private void initTimerLabel() {
		timerLabel = new JLabel("" + remainingTime);
		timerLabel.setForeground(new Color(255, 168, 253));
		timerLabel.setFont(new Font("Arial", Font.BOLD, 40));
		add(timerLabel);
	}

	private void updateTimer() {
		remainingTime--;
		timerLabel.setText("" + remainingTime);
		
		if(enemys.isEmpty() || !player.isState()) {
			gameTimer.stop();
		}

		if (remainingTime == 0) {
			gameTimer.stop();
			handleTimeUp();
			player.die();
		}
	}

	private void handleTimeUp() {
		gameOver = new GameOver(mContext);
		if (mContext != null) {
			mContext.add(gameOver);
			mContext.remove(this);
			mContext.repaint();
			try {
				// 2초 동안 대기
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Game Timer Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GameTimer());
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}