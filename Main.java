import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Hw3InputTextField extends JTextField {
    JTextField textField;

    Hw3InputTextField(JTextField textField) {
        this.textField = textField;

        setEditable(false);
        setBounds(75, 10, 350, 120);
        setBackground(Color.gray);
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        setOpaque(true);

        add(textField);
        textField.setFont(new Font("Dialog", Font.PLAIN, 60)); // 글씨 체
        textField.setText("0");
        textField.setBounds(15, 15, 320, 90);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setBackground(new Color(80, 85, 80));
    }

    void resizeTextField(int width, int height) {
        setBounds((int) (width * 0.15), (int) (height * 0.24 * 0.083), (int) (width * 0.7), (int) (height * 0.24));
        textField.setBounds((int) (width * 0.03), (int) (width * 0.03), (int) (width * 0.64), (int) (height * 0.24 * 0.75));
        textField.setFont(new Font("Dialog", Font.PLAIN, (int) (height * 0.12)));
    }
}

class Hw3ButtonPanel extends JPanel implements MouseListener {
    String[] button_names = {"7", "8", "9", "C", "4", "5", "6", "+", "1", "2", "3", "-", "0", "", "", "="};

    JButton[] buttons = new JButton[button_names.length];

    Hw3ButtonPanel(ActionListener actionListener) {
        setLayout(null);
        setBackground(new Color(80, 82, 85));
        setBounds(75, 130, 350, 330);

        for (int i = 0; i < button_names.length; i++) {
            buttons[i] = new JButton(button_names[i]);
            buttons[i].setFont(new Font("Dialog", Font.PLAIN, 40));

            if (button_names[i].matches("[+=-]")) {
                buttons[i].setForeground(new Color(255, 159, 9));
            } else if (button_names[i].matches("[C]")) {
                buttons[i].setForeground(Color.RED);
            } else {
                buttons[i].setForeground(Color.BLACK);
            }

            buttons[i].addActionListener(actionListener);

            int row = i / 4;  // 행 번호
            int col = i % 4;  // 열 번호

            int x = col * (getWidth() / 4);  // 버튼의 X 좌표
            int y = row * (getHeight() / 4);  // 버튼의 Y 좌표

            buttons[i].setBounds(x, y, getWidth() / 4, getHeight() / 4);  // 버튼 크기 및 위치 설정

            add(buttons[i]);
            buttons[i].addMouseListener(this);
        }
    }

    void resizeButtonPanel(int width, int height) {
        setBounds((int) (width * 0.15), (int) (height * 0.26), (int) (width * 0.7), (int) (height * 0.66));
        for (int i = 0; i < button_names.length; i++) {
            int row = i / 4;  // 행 번호
            int col = i % 4;  // 열 번호

            int x = col * (getWidth() / 4);  // 버튼의 X 좌표
            int y = row * (getHeight() / 4);  // 버튼의 Y 좌표
            buttons[i].setFont(new Font("Dialog", Font.PLAIN, (int) (height * 0.08)));
            buttons[i].setBounds(x, y, getWidth() / 4, getHeight() / 4);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int target = 0;

        for (int i = 0; i < buttons.length; i++) {
            if (button.equals(buttons[i])) {
                target = i;
            }
        }

        buttons[target].setForeground(Color.green);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int target = 0;

        for (int i = 0; i < buttons.length; i++) {
            if (button.equals(buttons[i])) {
                target = i;
            }
        }

        if (button_names[target].matches("[+=-]")) {
            buttons[target].setForeground(new Color(255, 159, 9));
        } else if (button_names[target].matches("[C]")) {
            buttons[target].setForeground(Color.RED);
        } else {
            buttons[target].setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

public class Main extends JFrame {
    JTextField textField = new JTextField();
    ArrayList<Integer> inputs = new ArrayList<>();
    String operator;
    String result;
    String prev = "";

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputValue = e.getActionCommand();

            if (inputValue.equals("C")) {
                textField.setText("0");
                result = null;
                operator = null;
                inputs.clear();
            } else if (inputValue.equals("=")) {
                if (inputs.size() == 2) {
                    if (operator == null) {
                        // null인데 =가 눌리면 아무 행동도 하지않기
                    } else if (operator.equals("+")) {
                        result = String.valueOf(inputs.get(0) + inputs.get(1));
                        inputs.clear();
                        inputs.add(Integer.valueOf(result));
                        operator = null;
                    } else if (operator.equals("-")) {
                        result = String.valueOf(inputs.get(0) - inputs.get(1));
                        inputs.clear();
                        inputs.add(Integer.valueOf(result));
                        operator = null;
                    }
                }

                if (result != null) {
                    textField.setText(result);
                    inputs.clear();
                    inputs.add(Integer.valueOf(result));
                    operator = null;
                }
            } else if (inputValue.matches("[+-]")) {
                if (operator == null)
                    operator = inputValue;

                if (inputs.size() == 2) {
                    if (operator.equals("+")) {
                        result = String.valueOf(inputs.get(0) + inputs.get(1));
                        inputs.clear();
                        inputs.add(Integer.valueOf(result));
                        operator = null;
                    } else if (operator.equals("-")) {
                        result = String.valueOf(inputs.get(0) - inputs.get(1));
                        inputs.clear();
                        inputs.add(Integer.valueOf(result));
                        operator = null;
                    }
                }

                if (inputs.size() == 1)
                    operator = inputValue;

                if (result != null) {
                    textField.setText(result);
                    result = null;
                }
            } else if (inputValue.matches("")) {
                // null값을 가지는 버튼에 대한 처리 --> 아무 행동 하지 않음
            } else { // 숫자일 경우
                if (prev.matches("[0123456789]")) {
                    textField.setText(textField.getText() + inputValue);
                    inputs.remove(inputs.size() - 1);
                } else {
                    textField.setText(inputValue);
                }

                inputs.add(Integer.parseInt(textField.getText()));
            }

            prev = inputValue;
        }
    };

    Main() {
        setTitle("javaHW3");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Hw3InputTextField inputTextField = new Hw3InputTextField(textField);
        Hw3ButtonPanel buttonPanel = new Hw3ButtonPanel(actionListener);

        add(inputTextField);
        add(buttonPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = getWidth();
                int h = getHeight();
                inputTextField.resizeTextField(w, h);
                buttonPanel.resizeButtonPanel(w, h);
            }
        });

        getContentPane().setBackground(Color.black);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
