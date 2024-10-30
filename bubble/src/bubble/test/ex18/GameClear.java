package bubble.test.ex18;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameClear extends JLabel {

	// 의존성 콤포지션
	private BubbleFrame mContext;

	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean down;

	private ImageIcon gameClear;
	public GameClear(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
	}

	private void initObject() {
		gameClear = new ImageIcon("image/GameClearText.png");
	}

	private void initSetting() {
		down = false;

		x = 340;
		y = 40;

		setIcon(gameClear);
		setSize(700, 187);
		setLocation(20, 40);

	}

}