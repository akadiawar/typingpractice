import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("�α���");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("����ڸ�:");
        JLabel passwordLabel = new JLabel("��й�ȣ:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("�α���");
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
            JOptionPane.showMessageDialog(null, "�α��� ����!");
            dispose(); 

            
            new Main();
        } else 
        {
            JOptionPane.showMessageDialog(null, "�α��� ����. ����ڸ� �Ǵ� ��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
        }
    }

    public static void main(String[] args)
    {
        new LoginGUI();
    }
}
