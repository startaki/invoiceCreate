package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ExcelInsert.ExcelInsert;
import InvoiceDB.InvoiceDAO;
import InvoiceDB.InvoiceDB;
import MemberDB.MemberDAO;
import MemberDB.MemberDB;

public class Invoice extends JFrame {
	private JPanel p1, p2;
	private JTextField send_name, send_postnum, send_addr, send_addr_detail, send_phone, send_item, reci_name,
			reci_postnum, reci_addr, reci_addr_detail, reci_phone1, reci_phone2, invoice_excel;
	private JLabel send_namelb, send_postnumlb, send_addr_detaillb, send_phonelb, send_itemlb, reci_namelb, reci_phone1lb,
			reci_phone2lb, reci_postnumlb, reci_addr_detaillb, invoice_excellb;
	
	private JButton invoice_excel_btn, invoice_firmup, invoice_print_btn, invoice_cancel, invoice_delete,
			invoice_save, send_postnum_btn, reci_postnum_btn;
	private JCheckBox member_chk;
	private JTableHeader tableHeader;

	public static Font basicfont = new Font("맑은 고딕", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	
	public static JMenuBar mb;
	public static JMenu menu1, menu2, menu3, mSeperator, mSpace;
	public static String invoicenum;
	public static JTable table;
	public static DefaultTableModel tableModel;
	public static int row;
	public static Object getInvoicenumber;
	public static FileDialog fd;
	public static String filePath, fileName;

	public static Random rnd = new Random();
	
	public Invoice() {
		this(null);
	}

	public Invoice(String id) {
		super("운송장 출력 프로그램     " + "접속중인 아이디 : " + id);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1060, 650);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		
		// 메뉴바 구현
		mb = new JMenuBar();
		mSpace = new JMenu(" ");
		mSpace.setEnabled(false);
		mb.add(mSpace);

		menu2 = new JMenu("운송장 조회");
		menu2.setFont(basicfont);
		mb.add(menu2);

		menu2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InvoiceList.listopen = true;
				InvoiceList ivl = new InvoiceList(id);
				setEnabled(false);
				ivl.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						setEnabled(true);
						InvoiceList.listopen = false;
					}
				}); 
			}
		});
		
		mSeperator = new JMenu("|");
		mSeperator.setEnabled(false);
		mb.add(mSeperator);

		menu3 = new JMenu("정보");
		menu3.setFont(basicfont);
		mb.add(menu3);
		
		menu3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Infomation();
			}
		});
		
		setJMenuBar(mb);
		
		// invoice_cancel, invoice_delete, invoice_save
		invoice_save = new JButton("저장");
		invoice_save.setFont(basicfont);
		invoice_save.setBounds(710, 20, 100, 25);
		add(invoice_save);

		String[] titleArray = { "운송장번호", "보내는 사람", "받는 사람", "받는 분 주소", "접수확정" };
		tableModel = new DefaultTableModel(titleArray, 0);

		invoice_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (send_name.getText().equals("") || send_postnum.getText().equals("")
						|| send_addr.getText().equals("") || send_addr_detail.getText().equals("")
						|| reci_name.getText().equals("") || reci_phone1.getText() == ""
						|| reci_postnum.getText().equals("") || reci_addr.getText().equals("")
						|| reci_addr_detail.getText().equals("")) {
					JOptionPane.showMessageDialog(null, dialogfont + "정보를 입력하세요.");
				} else {
					try {
						InvoiceDB invoice = new InvoiceDB();
						invoice.setInvoice_num(random());
						invoice.setMem_id(id);
						invoice.setSend_name(send_name.getText());
						invoice.setSend_phone(send_phone.getText().replaceAll("[^0-9]", ""));
						invoice.setSend_item(send_item.getText());
						invoice.setSend_postnum(send_postnum.getText());
						invoice.setSend_addr(send_addr.getText());
						invoice.setSend_addr_detail(send_addr_detail.getText());
						
						invoice.setReci_name(reci_name.getText());
						invoice.setReci_phone1(reci_phone1.getText().replaceAll("[^0-9]", ""));
						invoice.setReci_phone2(reci_phone2.getText().replaceAll("[^0-9]", ""));
						invoice.setReci_postnum(reci_postnum.getText());
						invoice.setReci_addr(reci_addr.getText());
						invoice.setReci_addr_detail(reci_addr_detail.getText());

						InvoiceDAO dao = InvoiceDAO.getInstance();
						int result = dao.invoice_save(invoice);
						if (result == 1) {
							JOptionPane.showMessageDialog(null, dialogfont + "운송장이 저장되었습니다.");
							Vector<Object> row = new Vector<>();
							row.addElement(invoicenum);
							row.addElement(send_name.getText());
							row.addElement(reci_name.getText());
							row.addElement(reci_addr.getText());
							row.addElement("X");
							tableModel.addRow(row);
						} else {
							JOptionPane.showMessageDialog(null, dialogfont + "운송장 저장에 실패하였습니다");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		table = new JTable(tableModel);
		table.setRowHeight(15);
		table.setFont(basicfont);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);

		invoice_cancel = new JButton("취소");
		invoice_cancel.setFont(basicfont);
		invoice_cancel.setBounds(820, 20, 100, 25);
		add(invoice_cancel);

		invoice_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send_name.setText("");
				send_item.setText("");
				send_postnum.setText("");
				send_addr.setText("");
				send_addr_detail.setText("");
				send_phone.setText("");
				reci_name.setText("");
				reci_postnum.setText("");
				reci_addr.setText("");
				reci_addr_detail.setText("");
				reci_phone1.setText("");
				reci_phone2.setText("");
				invoice_excel.setText("");
			}
		});

		invoice_delete = new JButton("삭제");
		invoice_delete.setFont(basicfont);
		invoice_delete.setBounds(930, 20, 100, 25);
		add(invoice_delete);
		invoice_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				// 행이 선택되지 않으면 row는 -1임
				if (row != -1) {
					int result = JOptionPane.showConfirmDialog(null, dialogfont + "삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
					Object id = table.getValueAt(row, 0);
					if (result == JOptionPane.YES_OPTION) {
						tableModel.removeRow(row);
						InvoiceDAO dao = InvoiceDAO.getInstance();
						dao.invoice_delete(id.toString());
					}
				}
			}
		});

		tableHeader = table.getTableHeader();
		tableHeader.setFont(basicfont);

		// 패널1 구현
		p1 = new JPanel();
		p1.setBounds(20, 60, 390, 450);
		p1.setBackground(new Color(189, 222, 253));
		p1.setLayout(null);
		add(p1);

		// 보내는 사람의 정보 입력란
		send_namelb = new JLabel("보내는 분");
		send_namelb.setFont(basicfont);
		send_namelb.setForeground(new Color(4, 70, 133));
		send_namelb.setBounds(15, 10, 100, 25);
		p1.add(send_namelb);

		send_name = new JTextField();
		send_name.setBounds(120, 10, 150, 25);
		p1.add(send_name);

		// 회원은 체크박스 선택시에 자동으로 보내는분 정보 입력
		member_chk = new JCheckBox("회원정보와 같음");
		member_chk.setFont(basicfont);
		member_chk.setBackground(new Color(189, 222, 253));
		member_chk.setBounds(275, 10, 150, 25);
		p1.add(member_chk);

		// 체크박스 체크 : 회원정보 읽어오기
		// 체크박스 해제 : 내용 삭제
		member_chk.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				MemberDAO dao = MemberDAO.getInstance();
				Vector<MemberDB> members = dao.findByAll();

				if (e.getStateChange() == 1) {
					for (int i = 0; i < members.size(); i++) {
						if (members.get(i).getId().equals(id)) {
							send_name.setText(members.get(i).getName());
							send_postnum.setText(members.get(i).getPostnum());
							send_addr.setText(members.get(i).getAddr());
							send_addr_detail.setText(members.get(i).getAddr_detail());
							send_phone.setText(members.get(i).getPhone());
							break;
						}
					}
				} else {
					send_name.setText("");
					send_postnum.setText("");
					send_addr.setText("");
					send_addr_detail.setText("");
					send_phone.setText("");
				}
			}
		});

		// 보내는 사람의 정보 입력란
		send_itemlb = new JLabel("물품명");
		send_itemlb.setFont(basicfont);
		send_itemlb.setForeground(new Color(4, 70, 133));
		send_itemlb.setBounds(15, 40, 100, 25);
		p1.add(send_itemlb);

		send_item = new JTextField();
		send_item.setBounds(120, 40, 150, 25);
		p1.add(send_item);

		send_phonelb = new JLabel("보내는 분 연락처");
		send_phonelb.setFont(basicfont);
		send_phonelb.setForeground(new Color(4, 70, 133));
		send_phonelb.setBounds(15, 70, 100, 25);
		p1.add(send_phonelb);

		send_phone = new JTextField();
		send_phone.setBounds(120, 70, 150, 25);
		p1.add(send_phone);

		// 보내는 사람 주소 입력란
		send_postnumlb = new JLabel("보내는 분 우편번호");
		send_postnumlb.setFont(basicfont);
		send_postnumlb.setForeground(new Color(4, 70, 133));
		send_postnumlb.setBounds(15, 100, 100, 25);
		p1.add(send_postnumlb);

		// 우편번호 api를 이용하여 파싱한 우편번호와 주소 입력
		send_postnum = new JTextField();
		send_postnum.setBounds(120, 100, 100, 25);
		send_postnum.setEditable(false);
		p1.add(send_postnum);

		send_postnum_btn = new JButton("우편번호");
		send_postnum_btn.setFont(basicfont);
		send_postnum_btn.setBounds(230, 100, 80, 25);
		p1.add(send_postnum_btn);
		
		send_postnum_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Search search = new Search();
				search.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						send_postnum.setText(Search.postnum);
						send_addr.setText(Search.addr);
					}
				}); 
			}
		});
		
		// 주소 입력
		send_addr = new JTextField();
		send_addr.setBounds(120, 130, 250, 25);
		p1.add(send_addr);

		send_addr_detaillb = new JLabel("보내는 분 상세주소");
		send_addr_detaillb.setFont(basicfont);
		send_addr_detaillb.setForeground(new Color(4, 70, 133));
		send_addr_detaillb.setBounds(15, 160, 100, 25);
		p1.add(send_addr_detaillb);
		
		// 상세주소 입력
		send_addr_detail = new JTextField();
		send_addr_detail.setBounds(120, 160, 250, 25);
		p1.add(send_addr_detail);

		// 받는 사람의 정보 입력란
		reci_namelb = new JLabel("받는 분");
		reci_namelb.setFont(basicfont);
		reci_namelb.setForeground(new Color(4, 70, 133));
		reci_namelb.setBounds(15, 220, 100, 25);
		p1.add(reci_namelb);

		reci_name = new JTextField();
		reci_name.setBounds(120, 220, 150, 25);
		p1.add(reci_name);

		// 연락처 입력란
		reci_phone1lb = new JLabel("받는 분 연락처1");
		reci_phone1lb.setFont(basicfont);
		reci_phone1lb.setForeground(new Color(4, 70, 133));
		reci_phone1lb.setBounds(15, 250, 100, 25);
		p1.add(reci_phone1lb);

		reci_phone1 = new JTextField();
		reci_phone1.setBounds(120, 250, 150, 25);
		p1.add(reci_phone1);

		reci_phone2lb = new JLabel("받는 분 연락처2");
		reci_phone2lb.setFont(basicfont);
		reci_phone2lb.setForeground(new Color(4, 70, 133));
		reci_phone2lb.setBounds(15, 280, 100, 25);
		p1.add(reci_phone2lb);

		reci_phone2 = new JTextField();
		reci_phone2.setBounds(120, 280, 150, 25);
		p1.add(reci_phone2);

		// 우편번호 api를 이용하여 파싱한 우편번호와 주소 입력
		reci_postnumlb = new JLabel("받는 분 우편번호");
		reci_postnumlb.setFont(basicfont);
		reci_postnumlb.setForeground(new Color(4, 70, 133));
		reci_postnumlb.setBounds(15, 310, 100, 25);
		p1.add(reci_postnumlb);

		reci_postnum = new JTextField();
		reci_postnum.setBounds(120, 310, 100, 25);
		reci_postnum.setEditable(false);
		p1.add(reci_postnum);
		
		reci_postnum_btn = new JButton("우편번호");
		reci_postnum_btn.setFont(basicfont);
		reci_postnum_btn.setBounds(230, 310, 80, 25);
		p1.add(reci_postnum_btn);
		
		reci_postnum_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Search search = new Search();
				search.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						reci_postnum.setText(Search.postnum);
						reci_addr.setText(Search.addr);
					}
				}); 
			}
		});
		
		reci_addr = new JTextField();
		reci_addr.setBounds(120, 340, 250, 25);
		p1.add(reci_addr);
		
		// 상세주소 입력
		reci_addr_detaillb = new JLabel("받는 분 상세주소");
		reci_addr_detaillb.setFont(basicfont);
		reci_addr_detaillb.setForeground(new Color(4, 70, 133));
		reci_addr_detaillb.setBounds(15, 370, 100, 25);
		p1.add(reci_addr_detaillb);
		
		reci_addr_detail = new JTextField();
		reci_addr_detail.setBounds(120, 370, 250, 25);
		p1.add(reci_addr_detail);

		// 엑셀 입력란
		invoice_excellb = new JLabel("파일");
		invoice_excellb.setFont(basicfont);
		invoice_excellb.setForeground(new Color(4, 70, 133));
		invoice_excellb.setBounds(15, 410, 100, 25);
		p1.add(invoice_excellb);

		invoice_excel = new JTextField();
		invoice_excel.setBounds(120, 410, 205, 25);
		invoice_excel.setEditable(false);
		p1.add(invoice_excel);
		
		invoice_excel_btn = new JButton("찾기");
		invoice_excel_btn.setFont(basicfont);
		invoice_excel_btn.setBounds(335, 410, 35, 25);
		invoice_excel_btn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		invoice_excel_btn.setBorder(new TitledBorder(new LineBorder(new Color(122, 138, 153))));
		p1.add(invoice_excel_btn);
		
		invoice_excel_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fd = new FileDialog(Invoice.this, "파일 열기", 0);
				fd.setDirectory("C:");
				fd.setVisible(true);
				filePath = fd.getDirectory();
				fileName = fd.getFile();
				try {
					if(filePath == null || fileName == null) {
						
					} else {
						ExcelInsert.getInvoiceList();
						invoice_excel.setText(Invoice.filePath + Invoice.fileName);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 패널 2
		p2 = new JPanel();
		p2.setBounds(430, 60, 600, 450);
		p2.setBackground(new Color(189, 222, 253));
		add(p2);

		// invoice_firmup, invoice_print_btn
		invoice_firmup = new JButton("운송장 확정");
		invoice_firmup.setFont(basicfont);
		invoice_firmup.setBounds(820, 530, 100, 25);
		add(invoice_firmup);

		// 클릭시 접수확정이 O로 바뀌게 됨
		invoice_firmup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.setValueAt("O", table.getSelectedRow(), 4);
			}
		});

		// 운송장 출력 클릭시 운송장 팝업을 띄워준다.
		invoice_print_btn = new JButton("운송장 출력");
		invoice_print_btn.setFont(basicfont);
		invoice_print_btn.setBounds(930, 530, 100, 25);
		add(invoice_print_btn);

		invoice_print_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				row = table.getSelectedRow();
				// 행이 선택되지 않으면 row는 -1이 됨.
				
				if (row < 0) {
					row = table.getRowCount() - 1;
				}
				
				Object id = table.getValueAt(row, 4);
				getInvoicenumber = table.getValueAt(row, 0);
				
				if (!id.equals("O")) {
					JOptionPane.showMessageDialog(null, dialogfont + "운송장을 출력하려면 접수 확정을 하세요.");
				} else {
					new Invoice_print(getInvoicenumber);
					InvoiceList.listopen = false;
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(580, 440));
		scrollPane.setFont(basicfont);
		p2.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public static String random() {
		invoicenum = String.valueOf(rnd.nextInt(9000) + 1000) + String.valueOf(rnd.nextInt(9000) + 1000) + String.valueOf(rnd.nextInt(9000) + 1000);
		return invoicenum;
	}

	public static void main(String[] args) {
		new Invoice();
	}
}
