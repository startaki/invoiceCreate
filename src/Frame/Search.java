package Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import postnumSearch.XmlParsing;


public class Search extends JFrame {
	
	private JPanel contentPane, search_panel;
	private JTable search_table;
	private JLabel titlelb, infolb;
	private JButton invoice_search_btn;
	private DefaultTableModel tableModel;
	private JTextField invoice_search;	
	
	private Font basicfont = new Font("맑은 고딕", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	
	public static String postnum, addr;
	public static boolean listopen = true;
	

	public Search() {
		this(null);
	}
	public Search(String id) {
		super("우편번호 검색");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(405, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		
		titlelb = new JLabel("우편번호 검색");
		titlelb.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		titlelb.setBounds(130, 10, 150, 25);
		add(titlelb);
		infolb = new JLabel(" * 도로명을 입력하세요. 예) 대지로 82");
		infolb.setFont(basicfont);
		infolb.setBounds(10, 70, 400, 25);
		add(infolb);
		
		
		contentPane = new JPanel();
		contentPane.setBounds(0, 100, 390, 500);
		add(contentPane);
		
		//tableModel에 열 이름과 행 개수 설정
		//tableModel에 전체 행 넣기
		String[] titleArray = { "우편번호", "주소" };
		tableModel = new DefaultTableModel(titleArray, 0);
		
		//tableModel을 JTable에 넣기
		search_table = new JTable(tableModel);
		search_table.setFont(basicfont);
		search_table.setRowHeight(30);
		search_table.getTableHeader().setReorderingAllowed(false);
		search_table.setDefaultEditor(Object.class, null);
		search_table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		search_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		search_table.getColumnModel().getColumn(1).setPreferredWidth(300);
		
		search_panel = new JPanel();
		search_panel.setBounds(0, 35, 405, 70);
		search_panel.setLayout(null);
		add(search_panel);
	    
	    invoice_search = new JTextField();
	    invoice_search.setBounds(5, 10, 300, 30);
	    invoice_search.setFont(basicfont);
	    search_panel.add(invoice_search);
	    
	    invoice_search_btn = new JButton("검색");
	    invoice_search_btn.setBounds(313, 10, 70, 30);
	    invoice_search_btn.setFont(basicfont);
	    search_panel.add(invoice_search_btn);
		
	    invoice_search_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
				if(invoice_search.getText().equals("")) {
					
				} else {
					new XmlParsing(invoice_search.getText(), tableModel);
					search_table.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							int row = search_table.getSelectedRow();
							if (row != -1) {
								Object postnum_obj = search_table.getValueAt(row, 0);
								Object addr_obj = search_table.getValueAt(row, 1);
								postnum = postnum_obj.toString();
								addr = addr_obj.toString();
								
								int result = JOptionPane.showConfirmDialog(null, dialogfont + "선택한 주소가 맞습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
								if (result == JOptionPane.YES_OPTION) {
									dispose();
								}
							}
						}
					});
				}

			}
		});
	    
		//JTable에 scroll달아서 add하기
		JScrollPane scrollPane = new JScrollPane(search_table);
		scrollPane.setPreferredSize(new Dimension(380, 470));
		contentPane.add(scrollPane);
		setVisible(true);
	}
	public static void main(String[] args) {
		
	}
}

