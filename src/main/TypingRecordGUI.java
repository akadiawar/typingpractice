package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TypingRecordGUI extends JFrame implements ActionListener{
	private JTable list;
	private JPanel Jp1, Jp2;
	private JButton Jb;
	private Container ct;
	private Database database;
    public TypingRecordGUI() throws SQLException {
    	database = Database.getInstance();
    	String loggedInUserId = database.getLoggedInUserId();
    	
    	setLayout(new BorderLayout());
        setTitle(loggedInUserId + "���� ���");
        setSize(500, 500);
        Jb = new JButton("��� �ʱ�ȭ");
        Jp1 = new JPanel();
        Jp2 = new JPanel();
        ct = new Container();
        ct.setLayout(new BorderLayout());
        String columnNames[] = {"����", "��Ȯ��", "��Ÿ��", "�д����Ÿ��", "�����ð�"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        list = new JTable(model) {
        	@Override
        	public boolean isCellEditable(int row, int column) {
        		return false;
        	}
        };
        
        JScrollPane scrollPane = new JScrollPane(list);
        JTableHeader header = list.getTableHeader(); // JTable�� ��� ��������
        header.setBackground(Color.lightGray);
        loadDB(model);
        
        list.getTableHeader().setReorderingAllowed(false);
        list.getTableHeader().setResizingAllowed(false);
        list.getColumn("����").setPreferredWidth(20);
        list.getColumn("��Ȯ��").setPreferredWidth(70);
        list.getColumn("��Ÿ��").setPreferredWidth(70);
        list.getColumn("�д����Ÿ��").setPreferredWidth(70);
        list.getColumn("�����ð�").setPreferredWidth(100);
        Jp1.add(scrollPane);
        Jp2.add(Jb);
        ct.add(Jp1,BorderLayout.CENTER);
        ct.add(Jb,BorderLayout.SOUTH);
        
        add(ct);
        Jb.addActionListener(this);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
    	String b = e.getActionCommand();
    	
    	if(e.getSource() == Jb) { 
    		Database.getInstance().Deletelist();
    		try {
                DefaultTableModel model = (DefaultTableModel) list.getModel();
                loadDB(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    		
    	}
    }
    /* DB���� ���� ���� �������� */
    private void loadDB(DefaultTableModel model) throws SQLException {
        Vector<Record> list = Database.getInstance().getUserRecord();
        model.setRowCount(0);
        for (Record record : list) {
            String level = String.valueOf(record.getLevel());
            String correct = String.valueOf(record.getCorrect());
            String not_correct = String.valueOf(record.getNot_correct());
            String tasu = String.valueOf(record.getTasu());
            String testdate = record.getTestdate();

            Vector<String> rowData = new Vector<>();
            rowData.add(level);
            rowData.add(correct);
            rowData.add(not_correct);
            rowData.add(tasu);
            rowData.add(testdate);
            model.addRow(rowData);
        }
    }
}
