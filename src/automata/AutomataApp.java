/*
 * AutomataApp.java
 */
package automata;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AutomataApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new AutomataView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of AutomataApp
     */
    public static AutomataApp getApplication() {
        return Application.getInstance(AutomataApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(AutomataApp.class, args);
    }

    public static void compute() {
        int i = 0, j = 0, k = 0, m = 0, max_state = 0, current_state = 0, flag = 0, last = 0, temp = 0, bracket_top = -1, plus_top = -1;
        int[][] plus = new int[2][20];
        int[] bracket = new int[20];
        char[] expression = new char[50];
        char ch;
        char[] symbols = new char[10];
        char[][] transition = new char[50][4];

        symbols = (AutomataView.jEditorPane1.getText() + "^").toCharArray();
        expression = ("(" + AutomataView.jEditorPane2.getText() + ")").toCharArray();

        AutomataView.jTextField4.setText("q0");

        while (i < expression.length) {

            if (current_state > max_state) {
                max_state = current_state;
            }

            ch = expression[i];
            i++;
            if (ch >= 95 && ch <= 122) {
                transition[j][0] = (char) current_state;
                transition[j][1] = ch;
                transition[j][2] = (char) (max_state + 1);
                j++;
                current_state = max_state + 1;
            } else if (ch == '(') {
                bracket_top++;
                bracket[bracket_top] = current_state;
                flag++;
            } else if (ch == ')') {
                last = bracket[bracket_top];
                bracket_top--;
                if (temp == 1) {
                    transition[j][0] = (char) current_state;
                    transition[j][1] = '^';
                    transition[j][2] = (char) (max_state + 1);
                    j++;
                    while (plus_top != -1) {
                        if (plus[1][plus_top] == flag) {
                            current_state = plus[0][plus_top];
                            transition[j][0] = (char) current_state;
                            transition[j][1] = '^';
                            transition[j][2] = (char) (max_state + 1);
                            j++;
                            plus_top--;
                        } else {
                            flag--;
                            break;
                        }
                    }
                    current_state = max_state + 1;
                }
            } else if (ch == '+') {
                plus_top++;
                plus[0][plus_top] = current_state;
                plus[1][plus_top] = flag;
                current_state = bracket[bracket_top];
                temp = 1;
            } else if (ch == '*') {
                transition[j][0] = (char) current_state;
                transition[j][1] = '^';
                transition[j][2] = (char) last;
                j++;
                transition[j][0] = (char) last;
                transition[j][1] = '^';
                transition[j][2] = (char) current_state;
                j++;
            }

        }

        AutomataView.jTextField6.setText("q" + current_state);
        i = 0;

        for (m = 0; m < symbols.length; m++) {
            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "____________");
        }

        AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "\n|States  |");

        for (m = 0; m < symbols.length; m++) {
            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "   " + symbols[m] + "   |");
        }

        AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "\n");

        for (m = 0; m < symbols.length; m++) {
            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "____________");
        }

        AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "\n");

        while (i <= max_state) {

            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "|   q" + i + "\t |");
            m = 0;
            while (m < symbols.length) {
                for (k = 0; k < j; k++) {
                    if (transition[k][0] == (char) i && transition[k][1] == symbols[m]) {
                        AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + " q" + (int) transition[k][2]);
                    }
                }
                AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "\t|");
                m++;
            }
            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "\n");
            i++;
        }

        for (m = 0; m < symbols.length; m++) {
            AutomataView.jTextField7.setText(AutomataView.jTextField7.getText() + "____________");
        }
    }
}