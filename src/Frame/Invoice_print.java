package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import InvoiceDB.InvoiceDAO;
import InvoiceDB.InvoiceDB;

public class Invoice_print extends JFrame {
	private JMenuBar mb;
	private JMenu mPrint;
	private JLabel invoice_num, invoice_date, invoice_reci_title, invoice_reci_name, invoice_reci_phone1,
			invoice_reci_phone2, invoice_send_title, invoice_info, invoice_send_addr_detail, delivery_man_namelb,
			delivery_man_phonelb;
	private JTextArea invoice_reci_addr, invoice_reci_addr_detail, invoice_send_item;
	private JPanel p_reci, p_send, p_bottom;
	private Font title_font = new Font("¸¼Àº °íµñ", Font.PLAIN, 11);
	private Font reci_font = new Font("¸¼Àº °íµñ", Font.PLAIN, 13);
	private Font send_font = new Font("¸¼Àº °íµñ", Font.PLAIN, 9);

	public static JLabel delivery_man_name, delivery_man_phone;

	public static InvoiceDAO dao = InvoiceDAO.getInstance();
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

	public Invoice_print() {

	}

	public Invoice_print(Object getInvoicenumber) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (InvoiceList.listopen == false) {
					int row = Invoice.row;
					Invoice.tableModel.removeRow(row);
				} else {

				}
			}
		});

		setSize(600, 350);
		setBackground(new Color(255, 255, 255));

		mb = new JMenuBar();
		mPrint = new JMenu(" ");
		mPrint.setEnabled(false);
		mb.add(mPrint);

		mPrint = new JMenu("¿î¼ÛÀå Ãâ·Â");
		mPrint.setFont(title_font);
		mb.add(mPrint);
		setJMenuBar(mb);

		mPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				printJavaComponent();
			}
		});

		invoice_num = new JLabel("¿î¼ÛÀå ¹øÈ£ : " + getInvoicenumber);
		invoice_num.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		invoice_num.setBounds(20, 15, 300, 25);
		add(invoice_num);

		invoice_date = new JLabel();
		invoice_date.setFont(title_font);
		invoice_date.setBounds(440, 15, 200, 25);
		add(invoice_date);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		p_reci = new JPanel();
		p_reci.setBounds(45, 40, 270, 100);
		p_reci.setBackground(new Color(255, 226, 13));
		p_reci.setLayout(null);
		add(p_reci);

		invoice_reci_title = new JLabel("<html>¹Þ<br>´Â<br>ºÐ</html>");
		invoice_reci_title.setFont(reci_font);
		invoice_reci_title.setOpaque(true);
		invoice_reci_title.setForeground(Color.WHITE);
		invoice_reci_title.setBackground(new Color(4, 70, 133));
		invoice_reci_title.setBounds(20, 40, 25, 100);
		add(invoice_reci_title);

		invoice_reci_name = new JLabel();
		invoice_reci_name.setFont(reci_font);
		invoice_reci_name.setForeground(Color.BLACK);
		invoice_reci_name.setBounds(0, -5, 80, 30);
		p_reci.add(invoice_reci_name);

		invoice_reci_phone1 = new JLabel();
		invoice_reci_phone1.setFont(reci_font);
		invoice_reci_phone1.setBounds(80, -5, 100, 30);
		p_reci.add(invoice_reci_phone1);

		invoice_reci_phone2 = new JLabel();
		invoice_reci_phone2.setFont(reci_font);
		invoice_reci_phone2.setBounds(180, -5, 100, 30);
		p_reci.add(invoice_reci_phone2);

		invoice_reci_addr = new JTextArea(2, 22);
		invoice_reci_addr.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 13));
		invoice_reci_addr.setWrapStyleWord(true);
		invoice_reci_addr.setLineWrap(true);
		invoice_reci_addr.setEditable(false);
		invoice_reci_addr.setFocusable(false);
		invoice_reci_addr.setBackground(new Color(255, 226, 13));
		invoice_reci_addr.setBounds(0, 20, 270, 40);
		p_reci.add(invoice_reci_addr);

		invoice_reci_addr_detail = new JTextArea(2, 22);
		invoice_reci_addr_detail.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		invoice_reci_addr_detail.setWrapStyleWord(true);
		invoice_reci_addr_detail.setLineWrap(true);
		invoice_reci_addr_detail.setEditable(false);
		invoice_reci_addr_detail.setFocusable(false);
		invoice_reci_addr_detail.setBackground(new Color(255, 226, 13));
		invoice_reci_addr_detail.setBounds(0, 60, 270, 50);
		p_reci.add(invoice_reci_addr_detail);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		p_send = new JPanel();
		p_send.setBounds(45, 150, 270, 35);
		p_send.setBackground(new Color(255, 226, 13));
		p_send.setLayout(null);
		add(p_send);

		invoice_send_title = new JLabel("<html>º¸ ³»<br>´Â ºÐ</html>");
		invoice_send_title.setFont(send_font);
		invoice_send_title.setOpaque(true);
		invoice_send_title.setForeground(Color.WHITE);
		invoice_send_title.setBackground(new Color(4, 70, 133));
		invoice_send_title.setBounds(20, 150, 25, 35);
		add(invoice_send_title);

		invoice_info = new JLabel();
		invoice_info.setFont(send_font);
		invoice_info.setBounds(0, -5, 270, 30);
		p_send.add(invoice_info);

		invoice_send_addr_detail = new JLabel();
		invoice_send_addr_detail.setFont(send_font);
		invoice_send_addr_detail.setBounds(0, 10, 270, 30);
		p_send.add(invoice_send_addr_detail);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		invoice_send_item = new JTextArea(2, 20);
		invoice_send_item.setWrapStyleWord(true);
		invoice_send_item.setLineWrap(true);
		invoice_send_item.setEditable(false);
		invoice_send_item.setFocusable(false);
		invoice_send_item.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		invoice_send_item.setBounds(20, 200, 300, 50);
		add(invoice_send_item);

		p_bottom = new JPanel();
		p_bottom.setBounds(0, 250, 600, 40);
		p_bottom.setBackground(new Color(255, 226, 13));
		p_bottom.setLayout(null);
		add(p_bottom);

		add(new barcodeCreate());

		Vector<InvoiceDB> invoice = dao.findByAll();
		for (int i = 0; i < invoice.size(); i++) {
			if (invoice.get(i).getInvoice_num().equals(getInvoicenumber)) {
				invoice_reci_name.setText(invoice.get(i).getReci_name());
				invoice_reci_phone1.setText(invoice.get(i).getReci_phone1().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3"));
				if(Objects.equals(invoice.get(i).getReci_phone2(), null)) {
					invoice_reci_phone2.setText(invoice.get(i).getReci_phone2());
				} else {
					invoice_reci_phone2.setText(invoice.get(i).getReci_phone2().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3"));
				}
				invoice_reci_addr.setText(invoice.get(i).getReci_addr());
				invoice_reci_addr_detail.setText(invoice.get(i).getReci_addr_detail());
				invoice_send_item.setText(invoice.get(i).getSend_item());
				invoice_info.setText(invoice.get(i).getSend_name() + " / " + invoice.get(i).getSend_phone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3"));
				invoice_send_addr_detail.setText(invoice.get(i).getSend_addr() + invoice.get(i).getSend_addr_detail());
				invoice_date.setText("Á¢¼ö ÀÏÀÚ : " + date.format(invoice.get(i).getInvoice_date()));
				break;
			}
		}

		delivery_man_name = new JLabel();
		delivery_man_phone = new JLabel();
		delivery_man_namelb = new JLabel("ÅÃ¹è ±â»ç´Ô ¼ºÇÔ : ");
		delivery_man_phonelb = new JLabel(" ÅÃ¹è ±â»ç´Ô ¿¬¶ôÃ³ : ");
		dao.delivery_man_search(invoice_reci_addr.getText());

		delivery_man_namelb.setBounds(10, 0, 120, 40);
		delivery_man_namelb.setFont(reci_font);
		p_bottom.add(delivery_man_namelb);

		delivery_man_name.setBounds(130, 0, 50, 40);
		delivery_man_name.setFont(reci_font);
		p_bottom.add(delivery_man_name);

		delivery_man_phonelb.setBounds(180, 0, 150, 40);
		delivery_man_phonelb.setFont(reci_font);
		p_bottom.add(delivery_man_phonelb);

		delivery_man_phone.setBounds(330, 0, 100, 40);
		delivery_man_phone.setFont(reci_font);
		p_bottom.add(delivery_man_phone);

		setVisible(true);
	}

	public void printJavaComponent() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setJobName("Print Java Component");
		job.setPrintable(new Printable() {
			public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
				if (pageIndex > 0) {
					return (NO_SUCH_PAGE);
				} else {
					Graphics2D g2d = (Graphics2D) g;
					g2d.translate(pageFormat.getImageableX() - 5, pageFormat.getImageableY() - 60); // -60Àº JMenuBar¸¦ ¾ø¾Ö±â
																								// À§ÇØ¼­ ÀÌ¹ÌÁö À§Ä¡¸¦ Á¶Àý
					g2d.setColor(Color.WHITE);
					g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
					paint(g2d);
					return (PAGE_EXISTS);
				}
			}
		});

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new Invoice_print();
	}
}
