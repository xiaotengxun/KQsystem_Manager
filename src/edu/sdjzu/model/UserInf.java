package edu.sdjzu.model;

public class UserInf {
	private String userNo =null;
	private String userPassword =null;
	private String userName =null;
	private String userSex =null;
	private String userSdept =null;
	private String userClass =null;
	private String userTel =null;
	private String userType =null;

	public UserInf(){}
	/**
	 * UserInfBean¹¹Ôìº¯Êý
	 * @param userNo
	 * @param userPassword
	 * @param userName
	 * @param userSex
	 * @param userSdept
	 * @param userClass
	 * @param userTel
	 * @param userType
	 */
	public UserInf(String userNo, String userPassword, String userName,
			String userSex, String userSdept, String userClass, String userTel,
			String userType) {
		this.userNo = userNo;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userSex = userSex;
		this.userSdept = userSdept;
		this.userClass = userClass;
		this.userTel = userTel;
		this.userType = userType;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserSdept() {
		return userSdept;
	}

	public void setUserSdept(String userSdept) {
		this.userSdept = userSdept;
	}

	public String getUserClass() {
		return userClass;
	}

	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
