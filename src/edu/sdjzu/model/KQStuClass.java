package edu.sdjzu.model;

/*
 * 以班级为单位查看相应课程的学生到课情况
 * 
 */

public class KQStuClass {
	private int Queqing, Chidao, Qingjia;
	private String Sno;
	private String Sname;

	public String getSname() {
		return Sname;
	}

	public void setSname(String sname) {
		Sname = sname;
	}

	public int getQueqing() {
		return Queqing;
	}

	public void setQueqing(int queqing) {
		Queqing = queqing;
	}

	public int getChidao() {
		return Chidao;
	}

	public void setChidao(int chidao) {
		Chidao = chidao;
	}

	public int getQingjia() {
		return Qingjia;
	}

	public void setQingjia(int qingjia) {
		Qingjia = qingjia;
	}

	public String getSno() {
		return Sno;
	}

	public void setSno(String sno) {
		Sno = sno;
	}

	public KQStuClass() {
		super();
	}

}
