package com.jyj.controller;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.jyj.dto.MemberVO;
import com.jyj.dto.UserPointLog;

public interface IActionFactory {
	// ȸ������ - ���̺� ȸ����ü�� �μ�Ʈ �Ѵ�. insert�۾�, 
	void registerMember(MemberVO vo) throws NamingException, SQLException;
	
	// ȸ�����Խ� �ߺ��Ǵ� ȸ������ �˻�
	boolean idIsDuplicate(String uid) throws NamingException, SQLException;
	
	// �α��� ó�� 
	MemberVO loginProcess(String uid, String pwd) throws NamingException, SQLException;
	
	// ��ü ȸ�� ������ ��ȯ�ϴ� �޼��� select
	List<MemberVO> entireMember() throws NamingException, SQLException;
	
	// �Ѹ��� ȸ�� ������ ��ȯ�ϴ� �޼��� 
	MemberVO getMember(String uid) throws NamingException, SQLException;
	
	// ȸ���� ����Ʈ �α׸� ��ȯ�ϴ� �޼���
	List<UserPointLog> getPointLog(String uid) throws NamingException, SQLException;
	
}