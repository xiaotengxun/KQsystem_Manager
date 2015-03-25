package edu.sdjzu.model;

public class Students {
	private String stuId = null;
	private String stuNo = null;
	private String stuPassword = null;
	private String parPassword = null;
	private String stuName = null;
	private String stuSex = null;
	private String stuSdept = null;
	private String stuClass = null;
	private String stuState = null;
	private String stuTel = null;
	private String parTel = null;
	private String stuPic = null;

	/**
	 * StudentsBean¹¹Ôìº¯Êý
	 * 
	 * @param stuId
	 * @param stuNo
	 * @param stuPassword
	 * @param parPassword
	 * @param stuName
	 * @param stuSex
	 * @param stuSdept
	 * @param stuClass
	 * @param stuState
	 * @param stuTel
	 * @param parTel
	 * @param stuPic
	 * @param coursePercent
	 */
	public Students() {
	}

	public Students(String stuId, String stuNo, String stuPassword, String parPassword, String stuName, String stuSex,
			String stuSdept, String stuClass, String stuState, String stuTel, String parTel, String stuPic) {
		this.stuId = stuId;
		this.stuNo = stuNo;
		this.stuPassword = stuPassword;
		this.parPassword = parPassword;
		this.stuName = stuName;
		this.stuSex = stuSex;
		this.stuSdept = stuSdept;
		this.stuClass = stuClass;
		this.stuState = stuState;
		this.stuTel = stuTel;
		this.parTel = parTel;
		this.stuPic = stuPic;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuPassword() {
		return stuPassword;
	}

	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}

	public String getParPassword() {
		return parPassword;
	}

	public void setParPassword(String parPassword) {
		this.parPassword = parPassword;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

	public String getStuSdept() {
		return stuSdept;
	}

	public void setStuSdept(String stuSdept) {
		this.stuSdept = stuSdept;
	}

	public String getStuClass() {
		return stuClass;
	}

	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}

	public String getStuState() {
		return stuState;
	}

	public void setStuState(String stuState) {
		this.stuState = stuState;
	}

	public String getStuTel() {
		return stuTel;
	}

	public void setStuTel(String stuTel) {
		this.stuTel = stuTel;
	}

	public String getParTel() {
		return parTel;
	}

	public void setParTel(String parTel) {
		this.parTel = parTel;
	}

	public String getStuPic() {
		return stuPic;
	}

	public void setStuPic(String stuPic) {
		this.stuPic = stuPic;
	}

}
