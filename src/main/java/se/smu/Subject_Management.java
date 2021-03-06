package se.smu;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class Subject_Management extends JFrame implements MouseListener,ActionListener{
	Vector v;
	Vector cols;
	DefaultTableModel model;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private JScrollPane scrollPane;
	public static JTable Subject_Data_Tb;
	private JPopupMenu popup = new JPopupMenu();
	private JMenuItem ChangeMenu = new JMenuItem("변경");
	private JMenuItem DeleteMenu = new JMenuItem("제거");
	
	//class value
	Subject_Management sList;
	
	//
	public String Subject;
	//combobox text send value
	public static String Sortcob; //sort를 위한 combobox
	public static String Clickdata; // Click 한 데이터 저장

	



	
	@SuppressWarnings("unchecked")
	public Subject_Management() {
		Subject_Dao dao  = new Subject_Dao();
		v = dao.getSubject_List();
		System.out.println("v="+v);
		cols = getColumn();
		//sort class 선언
		final Sort_Subject ssb = new Sort_Subject();
		
		model = new DefaultTableModel(v,cols)
		{
			public boolean isCellEditable(int i,int j)
			{
				return false;
			}
		};
		
		setTitle("수강 과목");
		setBounds(100, 100, 745, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JButton Subject_Scroll = new JButton("로그아웃");
		Subject_Scroll.setForeground(Color.WHITE);
		Subject_Scroll.setFont(Subject_Scroll.getFont().deriveFont(Subject_Scroll.getFont().getStyle() | Font.BOLD, Subject_Scroll.getFont().getSize() + 4f));
		Subject_Scroll.setBackground(new Color(0, 0, 128));
		Subject_Scroll.setBounds(562, 451, 144, 42);
		Subject_Scroll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				int logout = JOptionPane.showConfirmDialog(null, "로그아웃을 하시겠습니까??", "*경고*", JOptionPane.YES_NO_OPTION);
				if(logout == JOptionPane.YES_OPTION){
					
					Login Info = new Login();
					Info.setVisible(true);
					dispose();
					
				}
				else{
					Subject_Management sList = new Subject_Management();
					sList.setVisible(true);
					dispose();
				}

			}
		}
	    );
		contentPane.add(Subject_Scroll);

		JButton Alarm_Btn = new JButton("");
		Alarm_Btn.setIcon(new ImageIcon(Subject_Management.class.getResource("/image/alarm--alarm-icon-91768.png")));
		Alarm_Btn.setBounds(655, 15, 50, 43);
		contentPane.add(Alarm_Btn);
		Alarm_Btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Alarm_Management Info = new Alarm_Management(); //act class define
				Info.setVisible(true); //class act
			}
			
			
		});
		

		
		JButton New_Alarm_Btn = new JButton("");
		New_Alarm_Btn.setIcon(new ImageIcon(Subject_Management.class.getResource("/image/%C0̹%CC%C1%F6_007-iloveimg-resized.png")));
		New_Alarm_Btn.setBounds(588, 15, 50, 43);
		contentPane.add(New_Alarm_Btn);
		New_Alarm_Btn.setVisible(false);
		
		
		JScrollPane Subject_Data_Scroll = new JScrollPane();
		Subject_Data_Scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Subject_Data_Scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		Subject_Data_Scroll.setBounds(17, 90, 688, 346);
		contentPane.add(Subject_Data_Scroll);
		
		Subject_Data_Tb = new JTable(model);
		scrollPane = new JScrollPane(Subject_Data_Tb);
        add(scrollPane);
		Subject_Data_Tb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Subject_Data_Tb.setFillsViewportHeight(true);
		Subject_Data_Tb.setFont(new Font("굴림", Font.PLAIN, 12));

		Subject_Data_Tb.getColumnModel().getColumn(0).setPreferredWidth(100);
		Subject_Data_Tb.getColumnModel().getColumn(3).setPreferredWidth(51);
		Subject_Data_Tb.getColumnModel().getColumn(5).setPreferredWidth(51);
		Subject_Data_Tb.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		Subject_Data_Tb.setSurrendersFocusOnKeystroke(true);
		Subject_Data_Tb.setCellSelectionEnabled(true);
		Subject_Data_Tb.setColumnSelectionAllowed(true);
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		 
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		 
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = Subject_Data_Tb.getColumnModel();
		 
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		
		Subject_Data_Scroll.setViewportView(Subject_Data_Tb);
		
        //combobox 텍스트 전달을 위해서 combobox선언위치를 이곳으로 변경
		final JComboBox Sort_Subject_Btn = new JComboBox();
		//
		JButton Add_Subject_Btn = new JButton("");
		Subject_Data_Scroll.setRowHeaderView(Add_Subject_Btn);
		Add_Subject_Btn.setIcon(new ImageIcon(Subject_Management.class.getResource("/image/add.png")));
		Add_Subject_Btn.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				try {
					Sortcob = Sort_Subject_Btn.getSelectedItem().toString();
					Add_Subject frame = new Add_Subject();
					frame.setVisible(true); 
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}			
		});
		
		
		//comboBox event
		Sort_Subject_Btn.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						//사전식 order
						if(Sort_Subject_Btn.getSelectedItem().toString().equals("사전식순"))
						{
							ssb.Subject_Sort();//sort class call,subject asc
						}
					    //dayofweek order
						else
						{
							ssb.Dayofweek_Sort(); //sort class call,day order
						}
					}}
				);
		//
		Sort_Subject_Btn.setModel(new DefaultComboBoxModel(new String[] {"사전식순", "요일순"}));
		Sort_Subject_Btn.setForeground(Color.WHITE);
		Sort_Subject_Btn.setFont(Sort_Subject_Btn.getFont().deriveFont(Sort_Subject_Btn.getFont().getStyle() | Font.BOLD, Sort_Subject_Btn.getFont().getSize() + 4f));
		Sort_Subject_Btn.setBackground(new Color(0, 0, 128));
		Sort_Subject_Btn.setBounds(17, 451, 144, 42);
		contentPane.add(Sort_Subject_Btn);

		Subject_Data_Tb.addMouseListener(new Mouseclick());
	}
	
	public Vector getColumn(){
		Vector col = new Vector();
		col.add("수강과목");
		col.add("교수");
		col.add("요일");
		col.add("시간");
		col.add("년도");
		col.add("학기");
		col.add("분반");
		return col;
	}


	public void jTableRefresh(){
		Subject_Dao dao = new Subject_Dao();
		DefaultTableModel model = new DefaultTableModel(dao.getSubject_List(), getColumn());
		Subject_Data_Tb.setModel(model);
	}
	
	public class Mouseclick extends MouseAdapter implements ActionListener
	{   
		
		
	   public Mouseclick(){
	      popup.add(ChangeMenu);
	      popup.add(DeleteMenu);
	      
	      ChangeMenu.addActionListener(this);
	      DeleteMenu.addActionListener(this); 
	    
	      ChangeMenu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {		
						Subject_Dto dto = new Subject_Dto();
						int row = Subject_Data_Tb.getSelectedRow();
						String subject = (String) Subject_Data_Tb.getValueAt(row,  0);
						Change_Subject frame = new Change_Subject(subject); 
					
						frame.setVisible(true); 
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}			
			});
	      
	      DeleteMenu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
		
					try {
						
						int row = Subject_Data_Tb.getSelectedRow();
						String subject = (String) Subject_Data_Tb.getValueAt(row, 0);
						int del = JOptionPane.showConfirmDialog(null, "해당 수강 과목을 삭제하시겠습니까?", "*경고*", JOptionPane.YES_NO_OPTION);
						if(del == JOptionPane.YES_OPTION){
							
							Subject_Dao dao = new Subject_Dao();
							dao.Delete_Subject(subject);
							
							if (row == -1)
							{
								return;
							}
							
							DefaultTableModel model = (DefaultTableModel) Subject_Data_Tb.getModel();
							model.removeRow(row);
							
						}else{
							return;
						}
						
						
					
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}			
			});
	   }

	   public void mouseClicked(MouseEvent e){
		   
			int rowclickdata = Subject_Data_Tb.getSelectedRow();
			Clickdata = (String) Subject_Data_Tb.getValueAt(rowclickdata, 0);
			System.out.println(Clickdata);
		   int row = Subject_Data_Tb.rowAtPoint(e.getPoint());
		   int column = Subject_Data_Tb.columnAtPoint(e.getPoint());
		   
		   if (row >= 0 && column == 0)
		   {
			   if(e.getButton() == 3){
			         popup.show((Component)e.getSource(), e.getX(), e.getY());
			      }
			   else if(e.getClickCount()==2){
				   	  
				  	  Todo_Management Info = new Todo_Management(); //act class define
					  Info.setVisible(true); //class act
					  dispose();
			   }
		   }
		   
	   }
	   public void actionPerformed(ActionEvent e) {
		   // TODO Auto-generated method stub


	   }
	}
	 public void actionPerformed(ActionEvent e) {
		   // TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub
		
	}
}
