package InvoiceDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DBConnection.DBConnection;
import Frame.Invoice_print;

public class InvoiceDAO {
	private InvoiceDAO() {
	}

	private static InvoiceDAO instance = new InvoiceDAO();
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

	public static InvoiceDAO getInstance() {
		return instance;
	}

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement st;
	private ResultSet rs;

	// 성공 Vector<Member>, 실패 null
	public Vector<InvoiceDB> findByAll() {
		conn = DBConnection.getConnection();
		Vector<InvoiceDB> invoices = new Vector<>();
		try {
			pstmt = conn.prepareStatement("select * from invoice");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InvoiceDB invoice = new InvoiceDB();
				invoice.setInvoice_num(rs.getString("invoice_num"));
				invoice.setMem_id(rs.getString("mem_id"));
				invoice.setSend_name(rs.getString("send_name"));
				invoice.setSend_phone(rs.getString("send_phone"));
				invoice.setSend_item(rs.getString("send_item"));
				invoice.setSend_postnum(rs.getString("send_postnum"));
				invoice.setSend_addr(rs.getString("send_addr"));
				invoice.setSend_addr_detail(rs.getString("send_addr_detail"));
				invoice.setReci_name(rs.getString("reci_name"));
				invoice.setReci_phone1(rs.getString("reci_phone1"));
				invoice.setReci_phone2(rs.getString("reci_phone2"));
				invoice.setReci_postnum(rs.getString("reci_postnum"));
				invoice.setReci_addr(rs.getString("reci_addr"));
				invoice.setReci_addr_detail(rs.getString("reci_addr_detail"));
				invoice.setInvoice_date(rs.getTimestamp("invoice_date"));
				invoices.add(invoice);
				conn.commit();
			}
			return invoices;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 성공 1, 실패 -1,
	public int invoice_save(InvoiceDB invoice) {
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement("insert into invoice values(?,?,?,?,?,?,?,?,?,?,?,?,?,?, sysdate) ");
			pstmt.setString(1, invoice.getInvoice_num());
			pstmt.setString(2, invoice.getMem_id());
			pstmt.setString(3, invoice.getSend_name());
			pstmt.setString(4, invoice.getSend_phone());
			pstmt.setString(5, invoice.getSend_item());
			pstmt.setString(6, invoice.getSend_postnum());
			pstmt.setString(7, invoice.getSend_addr());
			pstmt.setString(8, invoice.getSend_addr_detail());

			pstmt.setString(9, invoice.getReci_name());
			pstmt.setString(10, invoice.getReci_phone1());
			pstmt.setString(11, invoice.getReci_phone2());
			pstmt.setString(12, invoice.getReci_postnum());
			pstmt.setString(13, invoice.getReci_addr());
			pstmt.setString(14, invoice.getReci_addr_detail());
			pstmt.executeUpdate(); // return값은 처리된 레코드의 개수
			conn.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int invoice_delete(Object id) {
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement("delete from Invoice where invoice_num = ?");
			pstmt.setString(1, (String) id);
			pstmt.executeUpdate();
			conn.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void invoiceTable(DefaultTableModel t_model, String id) {
		try {
			pstmt = conn.prepareStatement("select * from invoice where mem_id = ? order by invoice_num");
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			rs = pstmt.executeQuery();

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(9), rs.getString(10), rs.getString(11),
						rs.getString(12), rs.getString(13), rs.getString(14), rs.getDate(15) };
				t_model.addRow(data); // DefaultTableModel에 레코드 추가
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getInvoiceSearch(DefaultTableModel dt, String fieldName, String word, String id) {
		String sql = "select * from invoice where " + fieldName.trim() + " like '%" + word.trim() + "%'"
				+ " and mem_id = '" + id + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < dt.getRowCount();) {
				dt.removeRow(0);
			}
			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(9), rs.getString(10), rs.getString(11),
						rs.getString(12), rs.getString(13), rs.getString(14), rs.getDate(15) };
				dt.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delivery_man_search(String addr) {
		String sql = "select * from delivery_man where delman_name " + "like '%" + addr.substring(0, 2) + "%'";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Invoice_print.delivery_man_name.setText(rs.getString(1));
				Invoice_print.delivery_man_phone.setText(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
