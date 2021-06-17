package Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import InvoiceDB.InvoiceDAO;
import InvoiceDB.InvoiceDB;


public class InvoiceList extends JFrame {
	
	private JPanel contentPane, search_panel;
	private JTable table;
	private JLabel lbTitle;
	private JButton invoice_delete, invoice_print_btn, invoice_search_btn;
	private DefaultTableModel tableModel;
	private JComboBox<String> combo;
	
	private JTextField invoice_search;	
	
	private Font basicfont = new Font("맑은 고딕", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	public static boolean listopen = true;
	
	public InvoiceList() {
		this(null);
	}
	public InvoiceList(String id) {
		super("운송장 출력 프로그램     " + "접속중인 아이디 : " + id);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1000, 600);
		setLayout(null);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		
		contentPane = new JPanel();
		contentPane.setBounds(10, 60, 960, 450);
		add(contentPane);
		
		lbTitle = new JLabel("운송장 조회");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbTitle.setBounds(450, 20, 150, 25);
		add(lbTitle);
		
		//DB 데이터 가져오기
		InvoiceDAO dao = InvoiceDAO.getInstance();
		Vector<InvoiceDB> invoice = dao.findByAll();
		
		//tableModel에 열 이름과 행 개수 설정
		//tableModel에 전체 행 넣기
		String[] titleArray = { "운송장 번호", "받는 분", "받는 분 연락처1", "받는 분 연락처2", "받는 분 우편번호", "받는 분 주소", "받는 분 상세주소", "접수일자" };
		tableModel = new DefaultTableModel(titleArray, 0);
		dao.invoiceTable(tableModel, id);
		
		//tableModel을 JTable에 넣기
		table = new JTable(tableModel);
		table.setFont(basicfont);
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		search_panel = new JPanel();
		search_panel.setBounds(600, 10, 500, 50);
		search_panel.setLayout(null);
		add(search_panel);
		
		// DB내에 저장되어 있는 데이터로 검색할 수 있는 기능 구현 
		String[] comboArray = {"ALL", "운송장 번호", "받는 분", "받는 분 연락처1", "받는 분 연락처2", "받는 분 우편번호", "받는 분 주소", "받는 분 상세주소", "접수일자" };
	    combo = new JComboBox<String>(comboArray);
	    combo.setBounds(0, 10, 100, 29);
	    combo.setFont(basicfont);
	    search_panel.add(combo);
	    
	    invoice_search = new JTextField();
	    invoice_search.setBounds(110, 10, 190, 30);
	    invoice_search.setFont(basicfont);
	    search_panel.add(invoice_search);
	    
	    invoice_search_btn = new JButton("검색");
	    invoice_search_btn.setBounds(310, 10, 59, 29);
	    invoice_search_btn.setFont(basicfont);
	    search_panel.add(invoice_search_btn);
		
	    invoice_search_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InvoiceDAO dao = InvoiceDAO.getInstance();
				String fieldName = combo.getSelectedItem().toString();
				
				dao.findByAll();
			
				if(fieldName.equals("운송장 번호")) {
					fieldName = "invoice_num";
				} else if(fieldName.equals("받는 분")) {
					fieldName = "reci_name";
				} else if(fieldName.equals("받는 분 연락처1")) {
					fieldName = "reci_phone1";
				} else if(fieldName.equals("받는 분 연락처2")) {
					fieldName = "reci_phone2";
				} else if(fieldName.equals("받는 분 우편번호")) {
					fieldName = "reci_postnum";
				} else if(fieldName.equals("받는 분 주소")) {
					fieldName = "reci_addr";
				} else if(fieldName.equals("받는 분 상세주소")) {
					fieldName = "reci_addr_detail";
				} else if(fieldName.equals("접수일자")) {
					fieldName = "invoice_date";
				}
				
				
				if (fieldName.equals("ALL")) {// 전체검색
					dao.invoiceTable(tableModel, id);
					if (tableModel.getRowCount() > 0)
						table.setRowSelectionInterval(0, 0);
				} else {
					if (invoice_search.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, dialogfont + "값을 입력하세요.");
						invoice_search.requestFocus();
					} else {// 검색어를 입력했을경우
						dao.getInvoiceSearch(tableModel, fieldName, invoice_search.getText(), id);
						if (tableModel.getRowCount() > 0) {
							table.setRowSelectionInterval(0, 0);
						}
					}
				}
			}
		});
	 // 버튼 클릭시 운송장이 삭제됨 (DB에 저장되어 있는 정보도 삭제)
		invoice_delete = new JButton("삭제 [F4]");
		invoice_delete.setFont(basicfont);
		invoice_delete.setBounds(870, 520, 100, 25);
		add(invoice_delete);
		invoice_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				// 행이 선택되지 않으면 row는 -1임.
				
				if (row != -1) {
					int result = JOptionPane.showConfirmDialog(null, dialogfont + "삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						Object id = table.getValueAt(row, 0);
						for(int i = 0; i < Invoice.table.getRowCount(); i++ ) {
							if(id.equals(Invoice.table.getValueAt(i, 0))) {
								Invoice.tableModel.removeRow(i);
							}
						}
						tableModel.removeRow(row);	
						
						InvoiceDAO dao = InvoiceDAO.getInstance();
						dao.invoice_delete(id.toString());
					}
				}
			}
		});
		// 버튼 클릭시 운송장 출력 팝업이 뜸
		invoice_print_btn = new JButton("운송장 출력");
		invoice_print_btn.setFont(basicfont);
		invoice_print_btn.setBounds(760, 520, 100, 25);
		add(invoice_print_btn);

		invoice_print_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Invoice.getInvoicenumber = table.getValueAt(row, 0);
				// 행이 선택되지 않으면 row는 -1이 됨.
				if (row < 0) {
					row = table.getRowCount() - 1;
				}
				new Invoice_print(Invoice.getInvoicenumber);
			}
		});
		
		//JTable에 scroll달아서 add하기
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(960, 440));
		contentPane.add(scrollPane);
		setVisible(true);
	}
	public static void main(String[] args) {
		new InvoiceList();
	}
}

