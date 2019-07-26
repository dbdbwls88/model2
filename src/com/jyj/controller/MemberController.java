package com.jyj.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyj.dto.MemberVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import util.EncryptStr;

/**
 * Servlet implementation class MemberController
 */
@WebServlet({"/MemberController", "/control.do"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("get���! : " + request.getParameter("uid"));
		
		String uid = request.getParameter("uid");
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			if(ActionFactory.getInstance().idIsDuplicate(uid)) {
				out.print("{\"resultCode\": \"true\"}");
			} else {
				out.print("{\"resultCode\": \"false\"\"}");
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ActionFactory��ü�� ���� idIsDuplicate()�� ȣ��
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post���!");
		
//		String uid = request.getParameter("uid"); // ���̵�
//		String pwd = request.getParameter("pwd1"); // �н�����
		
		String path = "uploads";
		ServletContext context = getServletContext(); 
		String upfilePath = context.getRealPath(path);  // ������ ���ε� �� ���� ��� (WAS���� ����)
		int sizeLimit = 5 * 1024 * 1024; // �ϳ��� ������ ���ε� �� �� �ִ�  maximum ������
		String encodingType = "UTF-8"; // ���� �̸��� ���ڵ� Ÿ��
		// ������ ���� ���ε� ��
		MultipartRequest mr = new MultipartRequest(request, upfilePath, sizeLimit, encodingType, new DefaultFileRenamePolicy());
		
		String uid = mr.getParameter("uid"); // ���̵�
		String pwd = mr.getParameter("pwd1"); // �н�����
		
		// �Ѱܹ��� �н����带 SHA256 ������� ��ȣȭ �غ���
		EncryptStr es = new EncryptStr();
		String encPwd = es.encryptionStr(pwd);
		
		System.out.println("��ȣȭ �Ǳ� �� ��ȣ : " + pwd);
		System.out.println("��ȣȭ �� ��ȣ : " + encPwd);
		
		System.out.println(mr.getFilesystemName("userImg"));
		String upFileName = "";
		if (mr.getFilesystemName("userImg") == null) { // ������ �̹����� �ø��� ���� ���
			upFileName = "uploads/users.png"; // �⺻ �̹�����..
		} else { // ������ �̹����� �ø� ���
			upFileName = "uploads/" + mr.getFilesystemName("userImg");
		}
		MemberVO member = new MemberVO(uid, encPwd, upFileName);
		System.out.println("���� : " + member);
		
		response.setContentType("text/html; charset=utf-8"); // ���ڵ� ����
		PrintWriter out = response.getWriter();
		
		ActionFactory af = ActionFactory.getInstance(); // ActionFactory ��ü�� ����
		try {
			af.registerMember(member);
			
			out.print("<script> alert('ȸ������ ����!'); location.href='index.jsp';</script>");
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				out.print("<script>alert('ȸ������ ����! ���̵� �ߺ���'); history.back();</script>");
				e.printStackTrace();
			} else {
				out.print("<script>alert('ȸ������ ����!'); history.back();</script>");
			}
		}
		
	}

}