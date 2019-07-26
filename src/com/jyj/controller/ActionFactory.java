package com.jyj.controller;

import com.jyj.dto.MemberVO;
import com.jyj.dto.UserPointLog;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.jyj.dao.MemberDAO;

//Servlet ���� �޾ƿ� �����͸� Model������ �Ѱ��ֱ� ���� Controller �κ�
//�޸𸮸� ȿ�������� �����ϱ� ���� �̱��� ���
public class ActionFactory implements IActionFactory {
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() {
	}
	
	public static ActionFactory getInstance() {
		if (instance == null) {
			return new ActionFactory();
		}
		return instance;
	}
	
	@Override
	public void registerMember(MemberVO vo) throws NamingException, SQLException {
		System.out.println("ActionFactory : " + vo);
		MemberDAO dao = MemberDAO.getInstance();  // MemberDAO ��ü�� ����
		dao.insertMember(vo); // MemberDAO ��ü�� inserMember() �޼��� ȣ��, vo�� �Ѱ���
	}

	@Override
	public boolean idIsDuplicate(String uid) throws NamingException, SQLException {
		MemberDAO dao = MemberDAO.getInstance();
		return dao.idIsDuplicate(uid);
	}

	@Override
	public MemberVO loginProcess(String uid, String pwd) throws NamingException, SQLException {
		System.out.println("ActionFactory : " + uid + ", " + pwd);
		
		// MemberDAO�� loginProcess ȣ��
		
		
		return MemberDAO.getInstance().loginProcess(uid, pwd);
	}

	@Override
	public List<MemberVO> entireMember() throws NamingException, SQLException {
		MemberDAO mdao = MemberDAO.getInstance();
		
		return mdao.entireMember();
	}

	public MemberVO getMember(String uid) throws NamingException, SQLException {
		MemberDAO mdao = MemberDAO.getInstance();
		
		return mdao.getMember(uid);
	}

	@Override
	public List<UserPointLog> getPointLog(String uid) throws NamingException, SQLException {
		MemberDAO mdao = MemberDAO.getInstance();
		return mdao.getPointLog(uid);
	}

}