package bubble.test.ex18;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BubbleFrame extends JFrame {

	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	private List<Enemy> enemys; // 컬렉션으로 관리
	private BGM bgm;
	private GameOver gameOver;

	public JLabel scoreLabel; // score를 표시할 JLabel
	private GameTimer gameTimer; // 변경된 클래스 이름 사용
	private int remainingTime = 10;
	private JLabel timerLabel;

	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setFocusable(true); // 포커스를 받을 수 있도록 설정
		setVisible(true);
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player(mContext);
		add(player);
		enemys = new ArrayList<Enemy>();
		enemys.add(new Enemy(mContext, EnemyWay.RIGHT, 1));
		enemys.add(new Enemy(mContext, EnemyWay.LEFT, 2));
		enemys.add(new Enemy(mContext, EnemyWay.RIGHT, 3));
		enemys.add(new Enemy(mContext, EnemyWay.LEFT, 4));
		enemys.add(new Enemy(mContext, EnemyWay.LEFT, 5));


		for (Enemy e : enemys)
			add(e);
		bgm = new BGM();
		bgm.playBGM("bgm.wav");

		gameTimer = new GameTimer(this); // GameTimer 클래스 사용
		gameTimer.setBounds(470, 25, 50, 50); // 원하는 위치에 설정
		add(gameTimer);
	}

	private void initSetting() {
		setSize(1000, 640);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);// JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {

			// 키보드 클릭이벤트 핸들
			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (!player.isLeft() && !player.isLeftWallCrash()) {
						player.left();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (!player.isRight() && !player.isRightWallCrash()) {
						player.right();
					}
					break;
				case KeyEvent.VK_UP:
					if (!player.isUp() && !player.isDown()) {
						player.up();
					}
					break;
				case KeyEvent.VK_DOWN:
					player.down();
					break;
				case KeyEvent.VK_SPACE:
					player.attack();
					break;
				}
			}

			// 키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
		});
	}

	public static void main(String[] args) {
		new GameLauncher();
	}
}
