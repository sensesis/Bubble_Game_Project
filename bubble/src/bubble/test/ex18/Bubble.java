package bubble.test.ex18;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

	// 의존성 콤포지션
	private BubbleFrame mContext;
	private Player player;
	private List<Enemy> enemys;
	private Enemy removeEnemy = null; // 적 제거 변수.
	private BackgroundBubbleService backgroundBubbleService;
	private GameClear gameclear;
	private boolean gameClearTriggered = false;
	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;

	// 적군을 맞춘 상태
	private int state; // 0(물방울), 1(적을 가둔 물방울)

	private ImageIcon bubble; // 물방울
	private ImageIcon bubbled; // 적을 가둔 물방울
	private ImageIcon bomb; // 물방울이 터진 상태

	public Bubble(BubbleFrame mContext) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemys = mContext.getEnemys();
		initObject();
		initSetting();
	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");

		backgroundBubbleService = new BackgroundBubbleService(this);
	}

	private void initSetting() {
		left = false;
		right = false;
		up = false;

		x = player.getX();
		y = player.getY();

		setIcon(bubble);
		setSize(50, 50);

		state = 0;
	}

	@Override
	public void left() {
		left = true;
		Stop: for (int i = 0; i < 400; i++) {
			x--;
			setLocation(x, y);

			if (backgroundBubbleService.leftWall()) {
				left = false;
				break;
			}

			// 40과 60의 범위 절대값
			for (Enemy e : enemys) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attack(e);
						break Stop;
					}
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		Stop: for (int i = 0; i < 400; i++) {
			x++;
			setLocation(x, y);

			if (backgroundBubbleService.rightWall()) {
				right = false;
				break;
			}

			// 아군과 적군의 거리가 10
			for (Enemy e : enemys) {
				if (Math.abs(x - e.getX()) < 10 && Math.abs(y - e.getY()) > 0 && Math.abs(y - e.getY()) < 50) {
					if (e.getState() == 0) {
						attack(e);
						break Stop;
					}
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void up() {
		up = true;
		while (up) {
			y--;
			setLocation(x, y);

			if (backgroundBubbleService.topWall()) {
				up = false;
				break;
			}

			try {
				if (state == 0) { // 기본 물방울
					Thread.sleep(1);
				} else { // 적을 가둔 물방울
					Thread.sleep(10);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (state == 0)
			clearBubble(); // 천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
	}

	@Override
	public void attack(Enemy e) {
		state = 1;
		e.setState(1);
		setIcon(bubbled);
		removeEnemy = e;
		mContext.remove(e); // 메모리에서 사라지게 한다. (가비지 컬렉션->즉시 발동하지 않음)
		mContext.repaint(); // 화면 갱신
	}

	// 행위 -> clear (동사) -> bubble (목적어)
	private void clearBubble() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(500);
			// 버블 객체 메모리에서 날리기
			mContext.getPlayer().getBubbleList().remove(this);
			mContext.remove(this); // BubbleFrame의 bubble이 메모리에서 소멸된다.
			mContext.repaint(); // BubbleFrame의 전체를 다시 그린다. (메모리에서 없는 건 그리지 않음)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clearBubbled() {
		new Thread(() -> {
			System.out.println("clearBubbled");
			try {
				up = false;
				setIcon(bomb);
				Thread.sleep(1000);

				// 버블 객체 메모리에서 제거하기
				mContext.getPlayer().getBubbleList().remove(this);
				mContext.getEnemys().remove(removeEnemy); // 컨텍스트에 enemy 삭제
				mContext.remove(this);
				mContext.repaint();

				// synchronized를 통해 gameClearTriggered를 안전하게 접근
				synchronized (mContext) {
					if (enemys.isEmpty() && !gameClearTriggered) {
						new GameClearBGM();
						mContext.getBgm().stopBGM(); // 음악이 멈춤

						gameclear = new GameClear(mContext);
						mContext.add(gameclear);
						Thread.sleep(2000);
						mContext.remove(this);
						mContext.repaint();

						System.out.println("게임 클리어");
						gameClearTriggered = true; // 플래그를 true로 설정
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}