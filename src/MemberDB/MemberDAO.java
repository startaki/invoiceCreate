package MemberDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import DBConnection.DBConnection;


public class MemberDAO {
	private MemberDAO() {}
	private static MemberDAO instance=new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private Connection conn; //DB ���� ��ü
	private PreparedStatement pstmt; //Query �ۼ� ��ü
	private ResultSet rs; //Query ��� Ŀ��
	
	//���� 1, ���� -1, ���� 0
	public int findByIdAndPassword(String id, String password) {
		//1. DB ����
		conn = DBConnection.getConnection();
		
		try {
			//2. Query �ۼ�
			pstmt = conn.prepareStatement("select * from member where mem_id = ? and mem_pwd = ?");
			
			//3. Query ? �ϼ� (index 1�� ���� ����)
			//setString, setInt, setDouble, setTimeStamp ���� ����.
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			//4. Query ����
			//(1) executeQuery() = select = ResultSet ����
			//(2) executeUpdate() = insert, update, delete = ���� ����.
			rs = pstmt.executeQuery();
			
			//5. rs�� query�� ����� ù��° ��(���ڵ�) ������ �����
			//����� count(*) �׷��Լ��̱� ������ 1���� ���� ���ϵ�. while���� �ʿ� ����.
			if(rs.next()) { 
				//next()�Լ��� Ŀ���� ��ĭ �����鼭 �ش� �࿡ �����Ͱ� ������ true, ������ false ��ȯ
				//����� �ִٴ� ���� �ش� ���̵�� ����� ��Ī�Ǵ� ���� �ִٴ� ��.
				return 1; //�α��� ����
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return -1; //�α��� ����
	}
	
	
	//���� 1, ���� -1, 
	public int member_save(MemberDB member) {
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement("insert into member values(?,?,?,?,?,?,?,?, sysdate)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getPostnum());
			pstmt.setString(7, member.getAddr());
			pstmt.setString(8, member.getAddr_detail());
			pstmt.executeUpdate(); //return���� ó���� ���ڵ��� ����
			conn.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean findExistID(String id) {
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement("select count(*) cnt from member where mem_id  = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt("cnt");
				if(cnt > 0) {
					return true;
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	//���� Vector<Member>, ���� null
	public Vector<MemberDB> findByAll(){
		conn = DBConnection.getConnection();
		Vector<MemberDB> members = new Vector<>();
		try {
			pstmt = conn.prepareStatement("select * from member");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDB member = new MemberDB();
				member.setId(rs.getString("mem_id"));
				member.setPassword(rs.getString("mem_pwd"));
				member.setName(rs.getString("mem_name"));
				member.setEmail(rs.getString("mem_email"));
				member.setPhone(rs.getString("mem_phone"));
				member.setPostnum(rs.getString("mem_postnum"));
				member.setAddr(rs.getString("mem_addr"));
				member.setAddr_detail(rs.getString("mem_addr_detail"));
				member.setCreateDate(rs.getTimestamp("createDate"));
				members.add(member);
			}
			return members;
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}



