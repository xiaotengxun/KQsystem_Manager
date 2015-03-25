package edu.sdjzu.model;

public class Teachers {
	private String teaNo = null;
	private String teaPassword = null;
	private String teaName = null;
	private String teaSex = null;
	private String teaSdept = null;
	private String teaEmail = null;
	private String teaTel = null;

	/**
	 * TeachersBean¹¹Ôìº¯Êý
	 * @param teaNo
	 * @param teaPassword
	 * @param teaName
	 * @param teaSex
	 * @param teaSdept
	 * @param teaEmail
	 * @param teaTel
	 */
	public Teachers(String teaNo, String teaPassword, String teaName,
			String teaSex, String teaSdept, String teaEmail, String teaTel) {
		this.teaNo = teaNo;
		this.teaPassword = teaPassword;
		this.teaName = teaName;
		this.teaSex = teaSex;
		this.teaSdept = teaSdept;
		this.teaEmail = teaEmail;
		this.teaTel = teaTel;
	}
	public Teachers(){}

	public String getTeaNo() {
		return teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	public String getTeaPassword() {
		return teaPassword;
	}

	public void setTeaPassword(String teaPassword) {
		this.teaPassword = teaPassword;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getTeaSex() {
		return teaSex;
	}

	public void setTeaSex(String teaSex) {
		this.teaSex = teaSex;
	}

	public String getTeaSdept() {
		return teaSdept;
	}

	public void setTeaSdept(String teaSdept) {
		this.teaSdept = teaSdept;
	}

	public String getTeaEmail() {
		return teaEmail;
	}

	public void setTeaEmail(String teaEmail) {
		this.teaEmail = teaEmail;
	}

	public String getTeaTel() {
		return teaTel;
	}

	public void setTeaTel(String teaTel) {
		this.teaTel = teaTel;
	}

}
