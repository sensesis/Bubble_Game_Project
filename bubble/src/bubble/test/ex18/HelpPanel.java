package bubble.test.ex18;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class HelpPanel extends JPanel {

    private CardLayout cardLayout;  // cardLayout 변수 추가
    private JPanel cardPanel;       // cardPanel 변수 추가

    public HelpPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(null);

        JTextArea textArea = new JTextArea("방향키로 움직이고 스페이스 바를 눌러 버블을 쏴 공격하세요! \n"
        		+ "주어진 시간 안에 클리어하지 못할 시 게임오버가 됩니다.");
        textArea.setBounds(100, 100, 800, 400);
        textArea.setEditable(false); // 편집 불가능하도록 설정

        JButton backButton = new JButton("뒤로 가기");
        backButton.setBounds(400, 520, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 뒤로 가기 버튼을 누를 때 메인 메뉴로 돌아가도록 설정
                cardLayout.show(cardPanel, "MainMenuPanel");
            }
        });

        add(textArea);
        add(backButton);
    }
}
