package edu.sdjzu.model;

/*
 * 学生考勤信息
 * 
 */

public class KQresult {
	private int kqNo;
	private int taskNo;
	private int progressNo;
	private String stuNo = null;
	private String kqState = null;
	private String kqMark = null;
	private String isSubmit = null;
	private String inMan = null;
	private String inTime = null;
	private String sName=null;
	private String sClass=null;

	/**
	 * KQresultBean构造函数
	 * 
	 * @param kqNo
	 * @param taskNo
	 * @param progressNo
	 * @param stuName
	 * @param stuNo
	 * @param kqState
	 * @param kqMark
	 * @param isSubmit
	 * @param inMan
	 * @param inTime
	 */
	public KQresult(int kqNo, int taskNo, int progressNo, String stuNo, String kqState, String kqMark, String isSubmit,
			String inMan, String inTime,String sName,String sClass) {
		// this.stuName=stuName;
		this.kqNo = kqNo;
		this.taskNo = taskNo;
		this.progressNo = progressNo;
		this.stuNo = stuNo;
		this.kqState = kqState;
		this.kqMark = kqMark;
		this.isSubmit = isSubmit;
		this.inMan = inMan;
		this.inTime = inTime;
		this.sName=sName;
		this.sClass=sClass;
	}

	public KQresult() {
	}

	public int getKqNo() {
		return kqNo;
	}

	public void setKqNo(int kqNo) {
		this.kqNo = kqNo;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	public int getProgressNo() {
		return progressNo;
	}

	public void setProgressNo(int progressNo) {
		this.progressNo = progressNo;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getKqState() {
		return kqState;
	}

	public void setKqState(String kqState) {
		this.kqState = kqState;
	}

	public String getKqMark() {
		return kqMark;
	}

	public void setKqMark(String kqMark) {
		this.kqMark = kqMark;
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getInMan() {
		return inMan;
	}

	public void setInMan(String inMan) {
		this.inMan = inMan;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsClass() {
		return sClass;
	}

	public void setsClass(String sClass) {
		this.sClass = sClass;
	}
}
