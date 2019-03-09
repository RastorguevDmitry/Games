package GameBattleShip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameBattleShip extends JFrame {

    final String TITLE_OF_PROGRAM = "Морской бой";
    final int FIELD_SIZE = 10;
    final int AI_PANEL_SIZE = 400;
    final int AI_CELL_SIZE = AI_PANEL_SIZE / FIELD_SIZE;
    final int HUMAN_PANEL_SIZE = AI_PANEL_SIZE / 2;
    final int HUMAN_CELL_SIZE = HUMAN_PANEL_SIZE / FIELD_SIZE;
    final String BTN_INIT = "Новая игра";
    final String BTN_EXIT = "Выход";
    final String YOU_WIN = "YOU WIN";
    final String AI_WIN = "AI WIN";
    final int MOUSE_BUTTON_LEFT = 1;
    final int MOUSE_BUTTON_RIGHT = 3;

    JTextArea board;
    Canvas leftPanel, humanPanel;

    Ships aiShips, humanShips;
    Shots aiShots, humanShots;
    Random random;
    boolean gameOver;

    public static void main(String[] args) {
        new GameBattleShip();
    }

    GameBattleShip() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        leftPanel = new Canvas();
        leftPanel.setPreferredSize(new Dimension(AI_PANEL_SIZE, AI_PANEL_SIZE));
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        leftPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX() / AI_CELL_SIZE;
                int y = e.getY() / AI_CELL_SIZE;
                if (e.getButton() == MOUSE_BUTTON_LEFT && !gameOver)
                    if (!humanShots.hitSamePlace(x, y)) {
                        humanShots.add(x, y, true);
                        if (aiShips.checkHit(x, y)) {
                            if (!aiShips.checkSurvivors()) {
                                board.append("\n" + YOU_WIN);
                                gameOver = true;
                            }
                        } else shootsAI();
                        leftPanel.repaint();
                        humanPanel.repaint();
                        board.setCaretPosition(board.getText().length());
                    }
                if (e.getButton() == MOUSE_BUTTON_RIGHT) {
                    Shot label = humanShots.getLabel(x, y);
                    if (label != null)
                        humanShots.removeLable(label);
                    else humanShots.add(x, y, false);
                    leftPanel.repaint();
                }
            }
        });

        humanPanel = new Canvas();
        humanPanel.setPreferredSize(new Dimension(HUMAN_PANEL_SIZE, HUMAN_PANEL_SIZE));
        humanPanel.setBackground(Color.lightGray);
        humanPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        JButton init = new JButton(BTN_INIT);
        init.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
                leftPanel.repaint();
                humanPanel.repaint();
            }
        });

        JButton exit = new JButton(BTN_EXIT);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // поле для вывода информации
        board = new JTextArea();
        board.setEditable(false);
        JScrollPane scroll = new JScrollPane(board);

        // поле для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.add(init);
        buttonPanel.add(exit);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        rightPanel.add(humanPanel, BorderLayout.NORTH);
        rightPanel.add(scroll, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(leftPanel);
        add(rightPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        init();
    }

    void init() {
        aiShips = new Ships(FIELD_SIZE, AI_CELL_SIZE, true);
        humanShips = new Ships(FIELD_SIZE, HUMAN_CELL_SIZE, false);
        aiShots = new Shots(HUMAN_CELL_SIZE);
        humanShots = new Shots(AI_CELL_SIZE);
        board.setText(BTN_INIT);
        gameOver = false;
        random = new Random();
    }

    void shootsAI() {
        int x, y;
        do {
            x = random.nextInt(FIELD_SIZE);
            y = random.nextInt(FIELD_SIZE);
        } while (aiShots.hitSamePlace(x, y));
        aiShots.add(x, y, true);
        if (!humanShips.checkHit(x, y)) {
            board.append("\n" + (x + 1) + ":" + (y + 1) + " AI промазал.");
            return;
        } else {
            board.append("\n" + (x + 1) + ":" + (y + 1) + " AI попал.");
            board.setCaretPosition(board.getText().length());
            if (!humanShips.checkSurvivors()) {
                board.append("\n" + AI_WIN);
                gameOver = true;
            } else shootsAI();
        }
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int cellSize = (int) getSize().getWidth() / FIELD_SIZE;
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 1; i < FIELD_SIZE; i++) {
                g.drawLine(0, i * cellSize, FIELD_SIZE * cellSize, i * cellSize);
                g.drawLine(i * cellSize, 0, i * cellSize, FIELD_SIZE * cellSize);
            }
            if (cellSize == AI_CELL_SIZE) {
                humanShots.paint(g);
                aiShips.paint(g);
            } else {
                aiShots.paint(g);
                humanShips.paint(g);
            }
        }
    }


}
