package main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestMain extends JFrame implements ActionListener, KeyListener{
    private JLabel randomLabel;
    private JLabel howto = new JLabel();
    int count;
    int charCount;
    double yes;
    double no;
    private JButton jb1, jb2,jb3,jb4,jb5;
    String outputString, inputString;
    Calculator cal;
    Scanner s = new Scanner(System.in);
    private JTextField userTyping;
    private Database database;
    private boolean jb1Clicked = false, jb2Clicked = false,jb3Clicked = false;
    private boolean jb4Clicked = false;
    Level level;
    LevelOne levelone = new LevelOne();
    LevelTwo leveltwo = new LevelTwo();
    LevelThree levelthree = new LevelThree();
    LevelFour levelfour = new LevelFour();
    private ArrayList<String> records = new ArrayList<>();
    
    public TestMain() {
    	database = Database.getInstance();
    	String loggedInUserId = database.getLoggedInUserId();
    	System.out.println(loggedInUserId);
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(5, 5));

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());

        jb1 = new JButton("Level1");
        jb2 = new JButton("Level2");
        jb3 = new JButton("Level3");
        jb4 = new JButton("Level4");
        jb5 = new JButton("타자기록");
        p1.add(jb1);
        p1.add(jb2);
        p1.add(jb3);
        p1.add(jb4);
        p1.add(jb5);
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
        jb5.addActionListener(this);
        userTyping.addKeyListener(this);
        userTyping.setVisible(false);
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e)
    {
    	database = Database.getInstance();
    	String loggedInUserId = database.getLoggedInUserId();
    	String b = e.getActionCommand();
    	
    	if(e.getSource() == jb1)
    	{
             jb1Clicked = true;
             jb2Clicked = false;
             jb3Clicked = false;
             jb4Clicked = false;
             level = levelone;
             cal = new Calculator();
             userTyping.setVisible(true);
             userTyping.setEnabled(true);
             randomLabel.setText("Level1 -> TextField클릭 후 shift 누르기");
             reset();
             database.setCurrentLevel(1);
          
          
       }
       else if(e.getSource() == jb2)
      {
         jb1Clicked = false;
         jb2Clicked = true;
         jb3Clicked = false;
         jb4Clicked = false;
         level = leveltwo;
         cal = new Calculator();
         userTyping.setVisible(true);
         userTyping.setEnabled(true);
         randomLabel.setText("Level2 -> TextField클릭 후 shift 누르기");
         reset();
         database.setCurrentLevel(2);
      }
       else if(e.getSource() == jb3)
      {
         jb1Clicked = false;
         jb2Clicked = false;
         jb3Clicked = true;
         jb4Clicked = false;
         level = levelthree;
         cal = new Calculator();
         userTyping.setVisible(true);
         userTyping.setEnabled(true);
         randomLabel.setText("Level3 -> TextField클릭 후 shift 누르기");
         reset();
         database.setCurrentLevel(3);
      }
       else if(e.getSource() == jb4)
      {
         jb1Clicked = false;
         jb2Clicked = false;
         jb3Clicked = false;
         jb4Clicked = true;
         level = levelfour;
         cal = new Calculator();
         userTyping.setVisible(true);
         userTyping.setEnabled(true);
         randomLabel.setText("Level4 -> TextField클릭 후 shift 누르기");
         reset();
         database.setCurrentLevel(4);
      }
       if(e.getSource() == jb5)
       {
    	   try {
    		   new TypingRecordGUI();
    	   } catch (Exception ee) {
				JOptionPane.showMessageDialog(null, "기록이 없습니다.");
				ee.printStackTrace();
    	   }
       }
   }
    
    /* 기록 표기 및 DB에 기록 저장 */
    private void saveRecord(double correct, double notCorrect, double tasu) {
        String record = String.format("정확도: %.2f%%, 오타수: %.2f, 분당평균타수: %.2f", correct, notCorrect, tasu);
        database = Database.getInstance();
    	String loggedInUserId = database.getLoggedInUserId();
        records.add(record);
        boolean saveResult = database.saveTypingRecord(database.loggedInUserId, database.currentLevel, correct, notCorrect, tasu, database.testdate);
        if (saveResult) {
            System.out.println("타자 기록 데이터베이스에 저장 성공");
        } else {
            System.out.println("타자 기록 데이터베이스에 저장 실패");
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
               

               saveRecord(correct, notCorrect, tasu); // 기록 저장
               showResultMessage(correct, notCorrect, tasu); // 결과 메시지 표시
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
                     no += outputString.length() - inputString.length();               }
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
    

   private void showResultMessage(double correct, double notCorrect, double tasu) {
      // TODO Auto-generated method stub
      
   }


   public static void main(String[] args) {
        new TestMain();
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