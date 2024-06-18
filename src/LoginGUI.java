import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("로그인");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("사용자명:");
        JLabel passwordLabel = new JLabel("비밀번호:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("로그인");
        loginButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        
        if (username.equals("1234") && password.equals("1234")) 
        {
            JOptionPane.showMessageDialog(null, "로그인 성공!");
            dispose(); 

            
            new Main();
        } else 
        {
            JOptionPane.showMessageDialog(null, "로그인 실패. 사용자명 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    public static void main(String[] args)
    {
        new LoginGUI();
    }
}
