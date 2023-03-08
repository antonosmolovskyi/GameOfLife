package firstForMe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameOfLife {

    final String NAME_OF_GAME = "Game of Life";
    final int START_LOCATION = 200;
    final int LIFE_SIZE = 40;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = LIFE_SIZE * POINT_RADIUS + 9;
    final int BTN_PANEL_HEIGHT = 50;
    boolean[][] lifeGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    boolean[][] nextGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    int showDelay = 0;
    Canvas canvasPanel;
    Random random = new Random();

    public static void main(String[] args) {
    	GameOfLife game = new GameOfLife();
    	game.go();
    }

    void go() {
        JFrame frame = new JFrame(NAME_OF_GAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_SIZE, FIELD_SIZE + BTN_PANEL_HEIGHT);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.magenta);

        JButton fillButton = new JButton("Fill");
        fillButton.addActionListener(new FillButtonListener());

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processOfLife();
                canvasPanel.repaint();
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(fillButton);
        btnPanel.add(stepButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);

       
    }

    public class FillButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    lifeGeneration[x][y] = random.nextBoolean();
                }
            }
            canvasPanel.repaint();
        }
    }

    int countNeighbors(int x, int y) {
        int countOfNeighbors = 0;
        for (int z = -1; z < 2; z++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + z;
                int nY = y + dy;
                if(nX < 0) nX = LIFE_SIZE - 1;
                if(nY < 0) nY = LIFE_SIZE - 1;
                if(nX > LIFE_SIZE - 1) nX = 0;
                if(nY > LIFE_SIZE - 1) nY = 0;
                
                countOfNeighbors += (lifeGeneration[nX][nY]) ? 1 : 0;
            }
        }
        if (lifeGeneration[x][y]) { countOfNeighbors--; }
        return countOfNeighbors;
    }

    void processOfLife() {
        for (int x = 0; x < LIFE_SIZE; x++) {
            for (int y = 0; y < LIFE_SIZE; y++) {
                int countOfNeig = countNeighbors(x, y);
                nextGeneration[x][y] = lifeGeneration[x][y];
                if(countOfNeig == 3) nextGeneration[x][y] = true; 
                if((countOfNeig < 2) || (countOfNeig > 3)) nextGeneration[x][y] = false;
            }
        }
        for (int x = 0; x < LIFE_SIZE; x++) {
            System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, LIFE_SIZE);
        }
    }

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    if (lifeGeneration[x][y]) {
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                        //g.fillRect(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                        g.setColor(getBackground().DARK_GRAY);
                    }
                }
            }
        }
    }
}