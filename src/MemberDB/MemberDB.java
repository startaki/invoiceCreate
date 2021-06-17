package MemberDB;
import java.sql.Timestamp;

public class MemberDB {
	private Long no;
	private String id;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String postnum;
	private String addr;
	private String addr_detail;
	private Timestamp createDate;
	
	public MemberDB() {
	
	}
	
	public MemberDB(Long no, String id, String password, String name, String email, String phone, String postnum, String addr, String addr_detail,
			Timestamp createDate) {	
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.postnum = postnum;
		this.addr = addr;
		this.addr_detail = addr_detail;
		this.createDate = createDate;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPostnum() {
		return postnum;
	}

	public void setPostnum(String postnum) {
		this.postnum = postnum;
	}
	
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public String getAddr_detail() {
		return addr_detail;
	}

	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}

