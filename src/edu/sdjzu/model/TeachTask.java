package edu.sdjzu.model;

public class TeachTask {
	private int taskNo;
	private String courseNo = null;
	private String courseName = null;
	private String teaName = null;
	private String taskClass = null;
	private String courseType = null;
	private String taskWeek = null;
	private String taskTerms = null;
	
	public TeachTask(){}
	/**
	 * TeachTaskBean¹¹Ôìº¯Êý
	 * 
	 * @param taskNo
	 * @param courseNo
	 * @param courseName
	 * @param teaName
	 * @param taskClass
	 * @param courseType
	 * @param taskWeek
	 * @param taskTerms
	 */
	public TeachTask(int taskNo, String courseNo, String courseName,
			String teaName, String taskClass, String courseType,
			String taskWeek, String taskTerms) {
		this.taskNo = taskNo;
		this.courseNo = courseNo;
		this.courseName = courseName;
		this.teaName = teaName;
		this.taskClass = taskClass;
		this.courseType = courseType;
		this.taskWeek = taskWeek;
		this.taskTerms = taskTerms;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
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

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getTaskWeek() {
		return taskWeek;
	}

	public void setTaskWeek(String taskWeek) {
		this.taskWeek = taskWeek;
	}

	public String getTaskTerms() {
		return taskTerms;
	}

	public void setTaskTerms(String taskTerms) {
		this.taskTerms = taskTerms;
	}
}
