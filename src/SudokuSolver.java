import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {
    private JPanel[][] panels;
    private JPanel panel;
    private JPanel npanel;
    private JButton[][][] buttons;
    private JButton[] nbuttons;
    private int[][] puzzle;
    private boolean showSolution = false;
    private boolean checkmoves = false;
    private String turn="";
    private int[][] preGeneratedPuzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    private int[][] solution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };


    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setSize(500, 630);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        panel = new JPanel();


        panel.setBounds(50,50,450,450);
        panel.setLayout(new GridLayout(3,3));
        panels = new JPanel[3][3];
        buttons = new JButton[3][3][9];
        nbuttons = new JButton[9];
        puzzle = new int[9][9];
        add(panel);
        initializeGame();
    }



    private void initializeGame() {
        // Create panels and add them to the grid
        for (int panelRow = 0; panelRow < 3; panelRow++) {
            for (int panelCol = 0; panelCol < 3; panelCol++) {
                panels[panelRow][panelCol] = new JPanel();
                panels[panelRow][panelCol].setLayout(new GridLayout(3, 3));
                panels[panelRow][panelCol].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                for (int row = panelRow * 3; row < panelRow * 3 + 3; row++) {
                    for (int col = panelCol * 3; col < panelCol * 3 + 3; col++) {
                        buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3] = new JButton();
                        buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setFont(new Font("Arial", Font.BOLD, 20));
                        buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].addActionListener(new ButtonClickListener(row, col));

                        panels[panelRow][panelCol].add(buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3]);
                    }
                }

                panel.add(panels[panelRow][panelCol]);
            }
        }

        // Create control buttons
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonClickListener());
        resetButton.setBounds(50,570,100,50);
        resetButton.setBackground(new Color(153,204,255));
        add(resetButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitButtonClickListener());
        exitButton.setBounds(160,570,100,50);
        exitButton.setBackground(Color.red);
        add(exitButton);

        JButton solutionButton = new JButton("Solution");
        solutionButton.addActionListener(new SolutionButtonClickListener());
        solutionButton.setBounds(270,570,100,50);
        solutionButton.setBackground(new Color(0,204,255));
        add(solutionButton);

        JButton checkMovesButton = new JButton("Check Moves");
        checkMovesButton.addActionListener(new CheckMovesButtonClickListener());
        checkMovesButton.setBounds(380,570,100,50);
        checkMovesButton.setBackground(new Color(0,255,255));
        add(checkMovesButton);

        npanel = new JPanel();
        npanel.setBounds(50,510,450,50);

        for (int row = 0 ; row < 9; row++) {
            nbuttons[row] = new JButton();
            nbuttons[row].setFont(new Font("Arial", Font.BOLD, 20));
            nbuttons[row].setBackground(new Color(0,0,0));
            nbuttons[row].setForeground(Color.white);
            nbuttons[row].addActionListener(new nButtonClickListener(row));
            int val = row+1;
            nbuttons[row].setText(String.valueOf(val));
            npanel.add(nbuttons[row]);

        }
        add(npanel);

        // Generate a new puzzle and display it
        generatePuzzle();
        displayPuzzle();
    }
    private void AssignTurn(JButton btn){
        for(int i=0;i<9;i++){
            nbuttons[i].setBackground(new Color(0,0,0));
        }
        btn.setBackground(Color.blue);
    }

    private void generatePuzzle() {

        // Copy the pre-generated puzzle to the puzzle array
        for (int row = 0; row < 9; row++) {
            System.arraycopy(preGeneratedPuzzle[row], 0, puzzle[row], 0, 9);
        }
    }

    private void displayPuzzle() {
        // Display the puzzle on the buttons
        for (int panelRow = 0; panelRow < 3; panelRow++) {
            for (int panelCol = 0; panelCol < 3; panelCol++) {
                for (int row = panelRow * 3; row < panelRow * 3 + 3; row++) {
                    for (int col = panelCol * 3; col < panelCol * 3 + 3; col++) {
                        int value = puzzle[row][col];
                        int x=preGeneratedPuzzle[row][col];
                        buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setText(value > 0 ? String.valueOf(value) : "");
                        if(x==0){
                            buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setBackground(Color.white);
                        }else {
                            buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setBackground(new Color(0,200,255));
                            buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setFont(new Font("Arial", Font.BOLD, 20));
                            buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setForeground(Color.black);


                        }
                    }
                }
            }
        }
    }

    private void resetGame() {
        // Clear the puzzle and display it
        int choice = JOptionPane.showConfirmDialog(SudokuSolver.this, "Are you sure you want to reset the puzzle?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            generatePuzzle();
            displayPuzzle();
        }

    }

    private void checkMoves() {
        int pc,pr;
        for(int i=0;i<9;i++){
            if(i<=2)pr=0;
            else if (i<=5) {
                pr=1;
            }else pr=2;
            for(int j=0;j<9;j++){
                if(j<=2)pc=0;
                else if (j<=5) {
                    pc=1;
                }else pc=2;
                if(puzzle[i][j]!=0 && puzzle[i][j] != solution[i][j]){
                    if(checkmoves){
                        buttons[pr][pc][j%3+((i%3)*3)].setBackground(Color.red);
                    }else{
                        buttons[pr][pc][j%3+((i%3)*3)].setBackground(Color.white);
                    }

                }
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (preGeneratedPuzzle[row][col] == 0) {
                try {
                    int text = Integer.valueOf(turn)+1;
                    puzzle[row][col] = Integer.valueOf(turn)+1;
                    button.setText(String.valueOf(text));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SudokuSolver.this, "Invalid input! Please enter a digit from 1 to 9.");
                }
            }else{
                JOptionPane.showMessageDialog(SudokuSolver.this, "This place is allocated");
            }
        }
    }

    private class nButtonClickListener implements ActionListener {
        private int row;
        public nButtonClickListener(int row) {
            this.row = row;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            AssignTurn(nbuttons[row]);
            turn = ""+row;
        }
    }


    private class ResetButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    private class ExitButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(SudokuSolver.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private class SolutionButtonClickListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (showSolution) {
                displayPuzzle();
                ((JButton) e.getSource()).setText("Solution");
            } else {

                for (int panelRow = 0; panelRow < 3; panelRow++) {
                    for (int panelCol = 0; panelCol < 3; panelCol++) {
                        for (int row = panelRow * 3; row < panelRow * 3 + 3; row++) {
                            for (int col = panelCol * 3; col < panelCol * 3 + 3; col++) {
                                int value = solution[row][col];
                                buttons[panelRow][panelCol][col - panelCol * 3 + (row - panelRow * 3) * 3].setText(String.valueOf(value));
                            }
                        }
                    }
                }
                ((JButton) e.getSource()).setText("Hide Solution");
            }

            showSolution = !showSolution;
        }
    }

    private class CheckMovesButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkmoves=!checkmoves;
            checkMoves();
        }
    }


}
