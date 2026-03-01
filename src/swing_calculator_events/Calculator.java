package swing_calculator_events;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator {

    public static void main(String[] args) {
        // Create the calculator window
        CalculatorFrame calculator = new CalculatorFrame();
    }
}

// Main window of the calculator
class CalculatorFrame extends JFrame {

    public CalculatorFrame() {
        setTitle("calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 400, 400);

        // Add the calculator panel
        add(new CalculatorSheet());

        setVisible(true);
    }
}

// Panel that contains all calculator components
class CalculatorSheet extends JPanel {

    public CalculatorSheet() {
        // Indicates if a new number is being entered
        beggining = true;

        setLayout(new BorderLayout());

        // Display screen (disabled button used as display)
        screen = new JButton("0");
        screen.setEnabled(false);
        add(screen, BorderLayout.NORTH);

        // Panel for buttons
        sheet2 = new JPanel();
        sheet2.setLayout(new GridLayout(4, 4));

        // Listener for number buttons
        ActionListener insert = new InsertNumber();

        // Listener for operation buttons
        ActionListener order = new ActionOrder();

        // Add buttons to the panel
        putButton("7", insert); 
        putButton("8", insert); 
        putButton("9", insert); 
        putButton("/", order);

        putButton("4", insert); 
        putButton("5", insert); 
        putButton("6", insert); 
        putButton("*", order);

        putButton("1", insert); 
        putButton("2", insert); 
        putButton("3", insert); 
        putButton("-", order);

        putButton("0", insert); 
        putButton(".", insert); 
        putButton("=", order); 
        putButton("+", order);

        add(sheet2, BorderLayout.CENTER);

        // Initial operation is "="
        lastOperation = "=";
    }

    // Method to create and add a button
    private void putButton(String s, ActionListener listen) {
        JButton button = new JButton(s);
        button.addActionListener(listen);
        sheet2.add(button);
    }

    // Listener for number and decimal point buttons
    private class InsertNumber implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();

            // Clear screen if starting a new number
            if (beggining) {
                screen.setText("");
                beggining = false;
            }

            // Append the pressed number to the display
            screen.setText(screen.getText() + input);
        }
    }

    // Listener for operation buttons (+, -, *, /, =)
    private class ActionOrder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String operation = e.getActionCommand();

            // Perform calculation using the previous operation
            calculate(Double.parseDouble(screen.getText()));

            // Save the new operation
            lastOperation = operation;

            // Prepare for a new number input
            beggining = true;
        }

        // Performs the calculation based on the last operation
        public void calculate(double x) {

            if (lastOperation.equals("+")) {
                result += x;
            }
            else if (lastOperation.equals("-")) {
                result -= x;
            }
            else if (lastOperation.equals("*")) {
                result *= x;
            }
            else if (lastOperation.equals("/")) {
                result /= x;
            }
            else if (lastOperation.equals("=")) {
                result = x;
            }

            // Update the display with the result
            screen.setText("" + result);
        }
    }

    // Panel that contains all buttons
    private JPanel sheet2;

    // Display screen
    private JButton screen;

    // Indicates if the user is starting to type a new number
    private boolean beggining;

    // Stores the current result
    private double result;

    // Stores the last operation pressed (+, -, *, /, =)
    private String lastOperation;
}