package main;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class JoinFrame extends JFrame {
	/* Panel */
	JPanel panel = new JPanel();
	
	/* Label */
	JLabel idL = new JLabel("���̵�");
	JLabel pwL = new JLabel("��й�ȣ");
	
	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	
	/* Button */
	JButton joinBtn = new JButton("�����ϱ�");
	JButton cancelBtn = new JButton("�������");
	
	Operator o = null;
	
	JoinFrame(Operator _o) {
		o = _o;
		
		setTitle("ȸ������");
		
		/* Label ũ�� �۾� */
		idL.setPreferredSize(new Dimension(50, 30));
		pwL.setPreferredSize(new Dimension(50, 30));
		
		/* TextField ũ�� �۾� */
		id.setPreferredSize(new Dimension(140, 30));
		pw.setPreferredSize(new Dimension(140, 30));
		
		/* Button ũ�� �۾� */
		joinBtn.setPreferredSize(new Dimension(95, 25));
		cancelBtn.setPreferredSize(new Dimension(95, 25));
		
		/* Panel �߰� �۾� */
		setContentPane(panel);
		
		panel.add(idL);
		panel.add(id);
		
		panel.add(pwL);
		panel.add(pw);
		
		panel.add(cancelBtn);
		panel.add(joinBtn);
		
		/* Button �̺�Ʈ ������ �߰� */
		ButtonListener bl = new ButtonListener();
		
		cancelBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);
		
		setSize(250, 150);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	/* Button �̺�Ʈ ������ */
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			/* TextField�� �Էµ� ȸ�� �������� ������ �ʱ�ȭ */
			String uid = id.getText();
			String upass = "";
			for(int i=0; i<pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}
			
			/* ������� ��ư �̺�Ʈ */
			if(b.getText().equals("�������")) {
				dispose();
			}
			
			/* �����ϱ� ��ư �̺�Ʈ */
			else if(b.getText().equals("�����ϱ�")) {
				if(uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "��� ������ �������ּ���", "ȸ������ ����", JOptionPane.ERROR_MESSAGE);
					System.out.println("ȸ������ ���� > ȸ������ ���Է�");
				}
				
				else if(!uid.equals("") && !upass.equals("")) {
					if(o.db.joinCheck(uid, upass)) {
						System.out.println("ȸ������ ����");
						JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����Ͽ����ϴ�");
						dispose();
					} else {
						System.out.println("ȸ������ ����");
						JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����Ͽ����ϴ�");
						id.setText("");
						pw.setText("");
					}
				}
			}
		}
	}
}