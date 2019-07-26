package com.jyj.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.jyj.dto.MemberVO;
import com.jyj.dto.UserPointLog;

import util.DBManagement;

public class MemberDAO {
	//-------------------------------- MemberDAO �̱��� --------------------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() { }
	
	public static MemberDAO getInstance() {
		if (instance == null) {
			return new MemberDAO();
		}
		
		return instance;
	}
	//------------------------------------------------------------------------------------------------
	
	// �α��� ó�� -----------------------------------------------------------------------------------
	public MemberVO loginProcess(String uid, String pwd) throws NamingException, SQLException {
		// login�� �����ϸ� login�� ������ ����(MemberVO) ��ȯ, login�� ���� �ϸ� null ��ȯ
		Connection con = DBManagement.getConnection();
		String query = "select * from membervo where userid = ? and sha256pwd = ?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, uid);
		pstmt.setString(2, pwd);
		
		MemberVO loginMember = null;
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) { // row�� ���� ����
			
			// * �ֱ� �α��� ���ڰ� 1���� �Ѱ�ٸ�~ addLoginPoint(uid) ȣ��
			Date lastlogindate = rs.getDate("lastlogindate"); // �ֱ� �α��� ��¥ ������
			if (lastlogindate != null) {
				long lastloginTimeStamp = lastlogindate.getTime(); // �ֱ� �α��� ��¥�� TimeStampŸ������ ����
				long currentTimeStamp = System.currentTimeMillis(); // ���� �ð��� TimeStamp������ ����
				
				long different = currentTimeStamp - lastloginTimeStamp; 
				long onedayStamp = 1000 * 60 * 60 * 24; // �Ϸ��� �и������� �ð�
				
				if (different > onedayStamp) {
					System.out.println("�α��� ���� �Ϸ簡 ������");
					addLoginPoint(uid);  // �α��� ����Ʈ ����
				} else {
					System.out.println("�α��� ���� �Ϸ簡 �� ������");
				}
				
				// ���� �ð��� ���̺��� lastlogindate �÷��� update ���Ѿ� ��
				updateLastLoginDate(uid);
				
			}
			
			
			// �α��ο� ������ ������ ������ ��ȯ�ϱ� ���� ��ü ����
			loginMember = new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg"), 
					rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), lastlogindate);
		}
		
		System.out.println("DAO : " + loginMember);
		
		return loginMember;
	}
	
	private void updateLastLoginDate(String uid) throws NamingException, SQLException {
		// ���� �ð��� ���̺��� lastlogindate �÷��� update ���Ѿ� ��
		
		Connection con = DBManagement.getConnection();
		String query = "{call sp_updateLastLoginDate(?)}";
		
		CallableStatement cstmt = con.prepareCall(query);
		

		cstmt.setString(1, uid);
		
		cstmt.execute();
		
		cstmt.close();
		con.close();
		
	}

	// ����� ����Ʈ�� 1����(update) ��Ű�� pointlog���̺� ����Ʈ ���� insert()
	private void addLoginPoint(String uid) throws NamingException, SQLException {
		Connection con = DBManagement.getConnection();
		String query = "{call sp_addLoginPoint(?)}";
		
		CallableStatement cstmt = con.prepareCall(query);
		

		cstmt.setString(1, uid);
		
		cstmt.execute();
		
		cstmt.close();
		con.close();
	}

	// ------------- ȸ�� ������ ���� �޼��� -----------------------------------------------------------------------
	public void insertMember(MemberVO vo) throws NamingException, SQLException {
		boolean result = false;
		Connection con = DBManagement.getConnection();
		
		if (con != null) {
			String q = "{call sp_insertMemberVo(?, ?, ?)}";
			CallableStatement cstmt = con.prepareCall(q);
			
			cstmt.setString(1, vo.getUserid());
			cstmt.setString(2, vo.getUpwd());
			cstmt.setString(3, vo.getUserimg());
			
			cstmt.execute();
			
			
			cstmt.close();
			con.close();
		}
		
	}

	// --------------------------- ���̵� �ߺ� �˻� -------------------------------------------
	public boolean idIsDuplicate(String uid) throws NamingException, SQLException {
		boolean result = false;
		
		Connection con = DBManagement.getConnection();
		
		if (con != null) {
			
			String q = "{call idisduplicate(?, ?)}";
			
			CallableStatement cstmt = con.prepareCall(q);
			
			cstmt.setString(1, uid); // in �Ű�����
			cstmt.registerOutParameter(2, java.sql.Types.NVARCHAR); // out �Ű�����
			
			cstmt.execute();
			
			System.out.println(cstmt.getString(2));
			if (cstmt.getString(2).equals("true")) {
				result = true;
			}
//			result = Boolean.parseBoolean(cstmt.getString(2));
			
			cstmt.close();
			con.close();
		}
		
		return result;
	}

	public List<MemberVO> entireMember() throws NamingException, SQLException {
		List<MemberVO> members = new ArrayList<MemberVO>();
		
		Connection con = DBManagement.getConnection();
		String sql = "select * from membervo";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			members.add(new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg")
					, rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), rs.getDate("lastlogindate")));
		}
		
		return members;
		
	}

	// -------------- �Ѹ��� ȸ�� ������ ���� MemberVO�� ��ȯ�ϴ� �޼��� -----------------------
	public MemberVO getMember(String uid) throws NamingException, SQLException {
		MemberVO member = null;
		Connection con = DBManagement.getConnection();
		String sql = "select * from membervo where userid=?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, uid);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			member = new MemberVO(rs.getString("userid"), rs.getString("sha256pwd"), rs.getString("userimg")
					, rs.getDate("registerdate"), rs.getInt("point"), rs.getString("isadmin"), rs.getDate("lastlogindate"));
		}
		
		System.out.println("DAO :" + member);
		
		rs.close();
		pstmt.close();
		con.close();
		
		return member;
	}

	// ----------------------- ȸ���� ����Ʈ ������ ��ȯ�ϴ� �޼��� --------------------------------------
	public List<UserPointLog> getPointLog(String uid) throws NamingException, SQLException {
		List<UserPointLog> pointLogs = new ArrayList<UserPointLog>();
		Connection con = DBManagement.getConnection();
		String sql = "select l.givendate, p.pointval, p.why " + 
		 "from pointlog l, pointpolicy p where l.givenwhy = p.givenwhy and l.givenwho = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, uid);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			pointLogs.add(new UserPointLog(rs.getDate("givendate"), rs.getInt("pointval"), rs.getString("why")));
		}
		rs.close();
		pstmt.close();
		con.close();
		
		return pointLogs;
		
	}
	
	
}