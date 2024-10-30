package bubble.test.ex18;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameLauncher extends JFrame {

	private JPanel cardPanel;
	private CardLayout cardLayout;

	public GameLauncher() {
		cardPanel = new JPanel();
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);

		MainMenuPanel mainMenuPanel = new MainMenuPanel(this, cardLayout, cardPanel);
		cardPanel.add(mainMenuPanel, "MainMenuPanel");

		add(cardPanel);
		setSize(1000, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void showBubbleFrame() {
		BubbleFrame bubbleFrame = new BubbleFrame();
		bubbleFrame.setVisible(true);
		bubbleFrame.setFocusable(true);
		bubbleFrame.requestFocusInWindow();
	}

	public static void main(String[] args) {
		new GameLauncher();
	}
}

class MainMenuPanel extends JPanel {

	private GameLauncher gameLauncher;
	private ImageIcon backgroundImage;
	private CardLayout cardLayout;  
    private JPanel cardPanel;       

    public MainMenuPanel(GameLauncher gameLauncher, CardLayout cardLayout, JPanel cardPanel) {
        this.gameLauncher = gameLauncher;
        this.cardLayout = cardLayout;  
        this.cardPanel = cardPanel;      

        setLayout(null); // 배치 관리자를 null로 설정

        String imagePath = "image/MainUI.png";
        backgroundImage = new ImageIcon(imagePath);

        JButton startButton = new JButton("게임 시작");

        // Set the position and size of the "게임 시작" button
        startButton.setBounds(400, 300, 200, 50);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLauncher.showBubbleFrame();
            }
        });

        add(startButton);

        JButton exitButton = new JButton("게임 종료");

        // Set the position and size of the "게임 종료" button
        exitButton.setBounds(400, 360, 200, 50);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 프로그램 종료
            }
        });

        add(exitButton);
        
        JButton helpButton = new JButton("게임 플레이 방법");
        helpButton.setBounds(400, 420, 200, 50);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpPanel();
            }
        });
        add(helpButton);
        
        
    }
	private void showHelpPanel() {
	    // 게임 설명을 담은 새로운 패널 생성, 생성자에 cardLayout과 cardPanel 전달
	    HelpPanel helpPanel = new HelpPanel(cardLayout, cardPanel);

	    // 기존의 cardPanel에 helpPanel을 추가하고 "HelpPanel"이라는 이름으로 등록
	    cardPanel.add(helpPanel, "HelpPanel");

	    // cardLayout을 이용하여 HelpPanel을 표시
	    cardLayout.show(cardPanel, "HelpPanel");
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image image = backgroundImage.getImage();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
}
