import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;


public class Main extends JFrame implements ActionListener, KeyListener{
    private JLabel randomLabel;
    private JLabel howto = new JLabel();
    int count;
    int charCount;
    double yes;
    double no;
    private JButton jb1, jb2,jb3,jb4;
    String outputString, inputString;
    Calculator cal;
    Scanner s = new Scanner(System.in);
    private JTextField userTyping;
    private boolean jb1Clicked = false, jb2Clicked = false;
    Level level;
    LevelOne levelone = new LevelOne();
    LevelTwo leveltwo = new LevelTwo();
    LevelThree levelthree = new LevelThree();
    LevelFour levelfour = new LevelFour();

    public Main() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(5, 5));

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());

        jb1 = new JButton("Level1");
        jb2 = new JButton("Level2");
        jb3 = new JButton("Level3");
        jb4 = new JButton("Level4");
        p1.add(jb1);
        p1.add(jb2);
        p1.add(jb3);
        p1.add(jb4);
        ct.add(p1, BorderLayout.NORTH);
       

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        randomLabel = new JLabel("*타자연습 프로그램*"); 
        howto = new JLabel();
        p2.add(randomLabel);
        p2.add(howto);
        ct.add(p2, BorderLayout.CENTER);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        userTyping = new JTextField(15);
        p3.add(userTyping);
        ct.add(p3, BorderLayout.SOUTH);
        
        
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        userTyping.addKeyListener(this);
        userTyping.setVisible(false);
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e)
    {
		// TODO Auto-generated method stub
		String b = e.getActionCommand();
    	if(e.getSource() == jb1)
    	{
    			jb1Clicked = true;
    			jb2Clicked = false;
    			level = levelone;
    			cal = new Calculator();
    			userTyping.setVisible(true);
    			userTyping.setEnabled(true);
    			randomLabel.setText("Level1 -> TextField클릭 후 shift 누르기");
    			reset();
    		
    		
    		
    	}
    	else if(e.getSource() == jb2)
		{
			jb1Clicked = false;
			jb2Clicked = true;
			level = leveltwo;
			cal = new Calculator();
			userTyping.setVisible(true);
			userTyping.setEnabled(true);
			randomLabel.setText("Level2 -> TextField클릭 후 shift 누르기");
			reset();
			
		}
    	else if(e.getSource() == jb3)
		{
			jb1Clicked = false;
			jb2Clicked = true;
			level = levelthree;
			cal = new Calculator();
			userTyping.setVisible(true);
			userTyping.setEnabled(true);
			randomLabel.setText("Level3 -> TextField클릭 후 shift 누르기");
			reset();
			
		}
    	else if(e.getSource() == jb4)
		{
			jb1Clicked = false;
			jb2Clicked = true;
			level = levelfour;
			cal = new Calculator();
			userTyping.setVisible(true);
			userTyping.setEnabled(true);
			randomLabel.setText("Level4 -> TextField클릭 후 shift 누르기");
			reset();
			
		}
    	
		
	}
    private void reset()
    {
    	count = 0;
        charCount = 0;
        yes = 0;
        no = 0;
        outputString = null;
        inputString = null;
        
    }
    public void keyPressed(KeyEvent e) 
    {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		try
		{
			
			if (key == KeyEvent.VK_SHIFT) 
			{
				if(count == 0 || randomLabel.getText().equals("종료"))
				{
					
					reset();
					cal.start();
					outputString = level.randomPrint();
					count++;
					charCount += outputString.length();
					randomLabel.setText(outputString);
				}
				else if(count < 10)
				{
						
				}
			}
			else if(key == KeyEvent.VK_ENTER)
			{
				if(count == 10)
				{
					inputString = userTyping.getText();
					userTyping.setText("");
					if(outputString.length() > inputString.length())
					{
						for(int j = 0; j < inputString.length(); j++)
						{
							if(outputString.charAt(j)== inputString.charAt(j))
							{
								yes++;
							}
							else
							{
									no++;
							}
							no += outputString.length() - inputString.length();					
						}
						
							
					}
					else
					{
						for(int j = 0; j < inputString.length(); j++)
						{
							if(outputString.length() > 0 && j < outputString.length())
							{
								if(outputString.charAt(j) == inputString.charAt(j))
								{
									yes++;
								}
								else
								{
									no++;
								}
							}	
						}
						
					}
					
					cal.end();
					randomLabel.setText("종료");
					userTyping.setText("");
					userTyping.disable();
					
					double correct = yes / (double)charCount *100;
					double notCorrect = no;
					double elapsedMinutes = (System.currentTimeMillis() - cal.startTime) / (60.0 * 1000.0);
					double tasu = (charCount / elapsedMinutes);
					
					String message = String.format("정확도 : %.2f%%\n오타수 : %.2f\n 분당평균타수 : %.2f",correct,notCorrect,tasu);
					JOptionPane.showMessageDialog(null, message);
					count = 0;
				
			}
			
			else
			{
				inputString = userTyping.getText();
				userTyping.setText("");
				if(inputString.trim().isEmpty())
				{
					no += outputString.length();
				}
				else
				{
					
					if(outputString.length() > inputString.length())
					{
						for(int j = 0; j < inputString.length(); j++)
						{
							if(outputString.charAt(j)== inputString.charAt(j))
							{
								yes++;
							}
							else
							{
								no++;
							}
							no += outputString.length() - inputString.length();					}
					}
					else
					{
						for(int j = 0; j < inputString.length(); j++)
						{
							if(outputString.length() > 0 && j < outputString.length())
							{
								if(outputString.charAt(j) == inputString.charAt(j))
								{
									yes++;
								}
								else
								{
									no++;
								}
							}
							
						}
					}
				}
				
				outputString = level.randomPrint();
				charCount += outputString.length();
				randomLabel.setText(outputString);
				count++;
			}
			}
			}
    catch (NullPointerException ex) 
	{
		// TODO: handle exception
    	randomLabel.setText("Level 버튼을 누르고 Shift 키를 눌러 다시 시작");
    	userTyping.setText("");
    	
	}
    }
    

	
	
	
	public static void main(String[] args) {
        new Main();
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
