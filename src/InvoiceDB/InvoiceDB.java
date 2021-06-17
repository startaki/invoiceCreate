package InvoiceDB;

import java.sql.Timestamp;

public class InvoiceDB {
	private String invoice_num;
	private String mem_id, send_name, send_postnum, send_addr, send_addr_detail, send_phone, send_item, reci_name, reci_postnum,
			reci_addr, reci_addr_detail, reci_phone1, reci_phone2;
	private Timestamp invoice_date;
	public InvoiceDB() {

	}

	public InvoiceDB(String invoice_num, String mem_id, String send_name, String send_item, String send_phone,
			String send_postnum, String send_addr, String send_addr_detail, String reci_name, String reci_postnum,
			String reci_addr, String reci_addr_detail, String reci_phone1, String reci_phone2, Timestamp invoice_date) {
		this.invoice_num = invoice_num;
		this.mem_id = mem_id;
		this.send_name = send_name;
		this.send_phone = send_phone;
		this.send_item = send_item;
		this.send_postnum = send_postnum;
		this.send_addr = send_addr;
		this.send_addr_detail = send_addr_detail;
		this.reci_name = reci_name;
		this.reci_phone1 = reci_phone1;
		this.reci_phone2 = reci_phone2;
		this.reci_postnum = reci_postnum;
		this.reci_addr = reci_addr;
		this.reci_addr_detail = reci_addr_detail;
		this.invoice_date = invoice_date;
	}

	public String getInvoice_num() {
		return invoice_num;
	}
	
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	
	public String getMem_id() {
		return mem_id;
	}
	
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
	public String getSend_name() {
		return send_name;
	}
	
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	
	public String getSend_phone() {
		return send_phone;
	}
	
	public void setSend_phone(String send_phone) {
		this.send_phone = send_phone;
	}
	
	public String getSend_item() {
		return send_item;
	}
	
	public void setSend_item(String send_item) {
		this.send_item = send_item;
	}
	
	public String getSend_postnum() {
		return send_postnum;
	}
	
	public void setSend_postnum(String send_postnum) {
		this.send_postnum = send_postnum;
	}

	public String getSend_addr() {
		return send_addr;
	}
	
	public void setSend_addr(String send_addr) {
		this.send_addr = send_addr;
	}

	
	public String getSend_addr_detail() {
		return send_addr_detail;
	}
	
	public void setSend_addr_detail(String send_addr_detail) {
		this.send_addr_detail = send_addr_detail;
	}

	
	public String getReci_name() {
		return reci_name;
	}
	
	public void setReci_name(String reci_name) {
		this.reci_name = reci_name;
	}

	
	public String getReci_phone1() {
		return reci_phone1;
	}
	
	public void setReci_phone1(String reci_phone1) {
		this.reci_phone1 = reci_phone1;
	}

	public String getReci_phone2() {
		return reci_phone2;
	}
	
	public void setReci_phone2(String reci_phone2) {
		this.reci_phone2 = reci_phone2;
	}
	
	public String getReci_postnum() {
		return reci_postnum;
	}
	
	public void setReci_postnum(String reci_postnum) {
		this.reci_postnum = reci_postnum;
	}
	
	public String getReci_addr() {
		return reci_addr;
	}
	
	public void setReci_addr(String reci_addr) {
		this.reci_addr = reci_addr;
	}
	
	public String getReci_addr_detail() {
		return reci_addr_detail;
	}
	
	public void setReci_addr_detail(String reci_addr_detail) {
		this.reci_addr_detail = reci_addr_detail;
	}

	public Timestamp getInvoice_date() {
		return invoice_date;
	}
	
	public void setInvoice_date(Timestamp invoice_date) {
		this.invoice_date = invoice_date;
	}

}
