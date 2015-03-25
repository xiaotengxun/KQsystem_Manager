package edu.sdjzu.model;

import java.io.Serializable;

public class TeachProgress implements Serializable {
	private int progressNo;
	private int taskNo;
	private String courseName = null;
	private String teaName = null;
	private String progressClass = null;
	private String progressWeek = null;
	private String progressJTime = null;
	private String progressAddress = null;
	private String startTime = null;
	private String endTime = null;
	private String isKQ = null;
	private String IsSaved = null;
	private String inMan = null;
	private String inTime = null;

	public int getProgressNo() {
		return progressNo;
	}

	public void setProgressNo(int progressNo) {
		this.progressNo = progressNo;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getProgressClass() {
		return progressClass;
	}

	public void setProgressClass(String progressClass) {
		this.progressClass = progressClass;
	}

	public String getProgressWeek() {
		return progressWeek;
	}

	public void setProgressWeek(String progressWeek) {
		this.progressWeek = progressWeek;
	}

	public String getProgressJTime() {
		return progressJTime;
	}

	public void setProgressJTime(String progressJTime) {
		this.progressJTime = progressJTime;
	}

	public String getProgressAddress() {
		return progressAddress;
	}

	public void setProgressAddress(String progressAddress) {
		this.progressAddress = progressAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsKQ() {
		return isKQ;
	}

	public void setIsKQ(String isKQ) {
		this.isKQ = isKQ;
	}

	public String getIsSaved() {
		return IsSaved;
	}

	public void setIsSaved(String isSaved) {
		IsSaved = isSaved;
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

	public TeachProgress() {
		super();
	}

}
