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
	
	private Font basicfont = new Font("���� ���", Font.PLAIN, 11);
	private String dialogfont = "<html><h1 style='font-family:Malgun Gothic; font-weight: normal; font-size: 11pt;'>";
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	public static boolean listopen = true;
	
	public InvoiceList() {
		this(null);
	}
	public InvoiceList(String id) {
		super("����� ��� ���α׷�     " + "�������� ���̵� : " + id);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1000, 600);
		setLayout(null);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		
		contentPane = new JPanel();
		contentPane.setBounds(10, 60, 960, 450);
		add(contentPane);
		
		lbTitle = new JLabel("����� ��ȸ");
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		lbTitle.setBounds(450, 20, 150, 25);
		add(lbTitle);
		
		//DB ������ ��������
		InvoiceDAO dao = InvoiceDAO.getInstance();
		Vector<InvoiceDB> invoice = dao.findByAll();
		
		//tableModel�� �� �̸��� �� ���� ����
		//tableModel�� ��ü �� �ֱ�
		String[] titleArray = { "����� ��ȣ", "�޴� ��", "�޴� �� ����ó1", "�޴� �� ����ó2", "�޴� �� �����ȣ", "�޴� �� �ּ�", "�޴� �� ���ּ�", "��������" };
		tableModel = new DefaultTableModel(titleArray, 0);
		dao.invoiceTable(tableModel, id);
		
		//tableModel�� JTable�� �ֱ�
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
		
		// DB���� ����Ǿ� �ִ� �����ͷ� �˻��� �� �ִ� ��� ���� 
		String[] comboArray = {"ALL", "����� ��ȣ", "�޴� ��", "�޴� �� ����ó1", "�޴� �� ����ó2", "�޴� �� �����ȣ", "�޴� �� �ּ�", "�޴� �� ���ּ�", "��������" };
	    combo = new JComboBox<String>(comboArray);
	    combo.setBounds(0, 10, 100, 29);
	    combo.setFont(basicfont);
	    search_panel.add(combo);
	    
	    invoice_search = new JTextField();
	    invoice_search.setBounds(110, 10, 190, 30);
	    invoice_search.setFont(basicfont);
	    search_panel.add(invoice_search);
	    
	    invoice_search_btn = new JButton("�˻�");
	    invoice_search_btn.setBounds(310, 10, 59, 29);
	    invoice_search_btn.setFont(basicfont);
	    search_panel.add(invoice_search_btn);
		
	    invoice_search_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InvoiceDAO dao = InvoiceDAO.getInstance();
				String fieldName = combo.getSelectedItem().toString();
				
				dao.findByAll();
			
				if(fieldName.equals("����� ��ȣ")) {
					fieldName = "invoice_num";
				} else if(fieldName.equals("�޴� ��")) {
					fieldName = "reci_name";
				} else if(fieldName.equals("�޴� �� ����ó1")) {
					fieldName = "reci_phone1";
				} else if(fieldName.equals("�޴� �� ����ó2")) {
					fieldName = "reci_phone2";
				} else if(fieldName.equals("�޴� �� �����ȣ")) {
					fieldName = "reci_postnum";
				} else if(fieldName.equals("�޴� �� �ּ�")) {
					fieldName = "reci_addr";
				} else if(fieldName.equals("�޴� �� ���ּ�")) {
					fieldName = "reci_addr_detail";
				} else if(fieldName.equals("��������")) {
					fieldName = "invoice_date";
				}
				
				
				if (fieldName.equals("ALL")) {// ��ü�˻�
					dao.invoiceTable(tableModel, id);
					if (tableModel.getRowCount() > 0)
						table.setRowSelectionInterval(0, 0);
				} else {
					if (invoice_search.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, dialogfont + "���� �Է��ϼ���.");
						invoice_search.requestFocus();
					} else {// �˻�� �Է��������
						dao.getInvoiceSearch(tableModel, fieldName, invoice_search.getText(), id);
						if (tableModel.getRowCount() > 0) {
							table.setRowSelectionInterval(0, 0);
						}
					}
				}
			}
		});
	 // ��ư Ŭ���� ������� ������ (DB�� ����Ǿ� �ִ� ������ ����)
		invoice_delete = new JButton("���� [F4]");
		invoice_delete.setFont(basicfont);
		invoice_delete.setBounds(870, 520, 100, 25);
		add(invoice_delete);
		invoice_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				// ���� ���õ��� ������ row�� -1��.
				
				if (row != -1) {
					int result = JOptionPane.showConfirmDialog(null, dialogfont + "�����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_OPTION);
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
		// ��ư Ŭ���� ����� ��� �˾��� ��
		invoice_print_btn = new JButton("����� ���");
		invoice_print_btn.setFont(basicfont);
		invoice_print_btn.setBounds(760, 520, 100, 25);
		add(invoice_print_btn);

		invoice_print_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				Invoice.getInvoicenumber = table.getValueAt(row, 0);
				// ���� ���õ��� ������ row�� -1�� ��.
				if (row < 0) {
					row = table.getRowCount() - 1;
				}
				new Invoice_print(Invoice.getInvoicenumber);
			}
		});
		
		//JTable�� scroll�޾Ƽ� add�ϱ�
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(960, 440));
		contentPane.add(scrollPane);
		setVisible(true);
	}
	public static void main(String[] args) {
		new InvoiceList();
	}
}

