package com.jyj.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jyj.dto.MemberVO;

import util.EncryptStr;

/**
 * Servlet implementation class MemberLoginServlet
 */
@WebServlet({ "/MemberLoginServlet", "/login.do" })
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String pwd1 = request.getParameter("pwd1");
		
		EncryptStr es = new EncryptStr();
		String encPwd = es.encryptionStr(pwd1);
		
		ActionFactory af = ActionFactory.getInstance(); // ActionFactory�� ��ü ���
		try {
			MemberVO loginMember = af.loginProcess(uid, encPwd);
			
			// ���ǿ����� �α��� ���� ����
			HttpSession ses = request.getSession();
			ses.setAttribute("loginMember", loginMember);
			
			response.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			if (loginMember != null) {
				out.print("<script>");
				out.print("alert('�α��� ����!');");
				out.print("location.href='index.jsp';");
				out.print("</script>");
			} else {
				out.print("<script>");
				out.print("alert('ȸ�� ������ �ùٸ��� �ʽ��ϴ�. Ȯ�� �� �ٽ� �α��� ���ּ���!');");
				out.print("location.href='index.jsp';");
				out.print("</script>");
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}