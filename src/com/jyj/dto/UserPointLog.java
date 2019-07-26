package com.jyj.dto;
import java.sql.Date;

public class UserPointLog {
	private Date givendate; // �α� ����� 
	private int pointval; // �� ����Ʈ
	private String why; // ����Ʈ ���� ����
	public UserPointLog(Date givendate, int pointval, String why) {
		this.givendate = givendate;
		this.pointval = pointval;
		this.why = why;
	}
	public Date getGivendate() {
		return givendate;
	}
	public void setGivendate(Date givendate) {
		this.givendate = givendate;
	}
	public int getPointval() {
		return pointval;
	}
	public void setPointval(int pointval) {
		this.pointval = pointval;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	@Override
	public String toString() {
		return "UserPointLog [givendate=" + givendate + ", pointval=" + pointval + ", why=" + why + "]";
	}
	
	
	
	
}