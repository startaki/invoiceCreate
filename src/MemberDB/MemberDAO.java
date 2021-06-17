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
	
	private Connection conn; //DB 연결 객체
	private PreparedStatement pstmt; //Query 작성 객체
	private ResultSet rs; //Query 결과 커서
	
	//성공 1, 실패 -1, 없음 0
	public int findByIdAndPassword(String id, String password) {
		//1. DB 연결
		conn = DBConnection.getConnection();
		
		try {
			//2. Query 작성
			pstmt = conn.prepareStatement("select * from member where mem_id = ? and mem_pwd = ?");
			
			//3. Query ? 완성 (index 1번 부터 시작)
			//setString, setInt, setDouble, setTimeStamp 등이 있음.
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			//4. Query 실행
			//(1) executeQuery() = select = ResultSet 리턴
			//(2) executeUpdate() = insert, update, delete = 리턴 없음.
			rs = pstmt.executeQuery();
			
			//5. rs는 query한 결과의 첫번째 행(레코드) 직전에 대기중
			//결과가 count(*) 그룹함수이기 때문에 1개의 행이 리턴됨. while문이 필요 없음.
			if(rs.next()) { 
				//next()함수는 커서를 한칸 내리면서 해당 행에 데이터가 있으면 true, 없으면 false 반환
				//결과가 있다는 것은 해당 아이디와 비번에 매칭되는 값이 있다는 뜻.
				return 1; //로그인 성공
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return -1; //로그인 실패
	}
	
	
	//성공 1, 실패 -1, 
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
			pstmt.executeUpdate(); //return값은 처리된 레코드의 개수
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
	
	//성공 Vector<Member>, 실패 null
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



