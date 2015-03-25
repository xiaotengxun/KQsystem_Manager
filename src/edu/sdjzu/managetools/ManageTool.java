package edu.sdjzu.managetools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.localtool.DatabaseManager;
import edu.sdjzu.localtool.InternetStatus;
import edu.sdjzu.localtool.LocalSqlTool;
import edu.sdjzu.model.KQInfo;
import edu.sdjzu.model.KQStuClass;
import edu.sdjzu.model.KQStuPerson;
import edu.sdjzu.model.KQresult;
import edu.sdjzu.model.Students;
import edu.sdjzu.model.TeachProgress;

public class ManageTool {
	private Context context;

	/**
	 * 根据Spinner的选择项,用户名密码和来启动指定的Activity，并保存登陆信息
	 * 
	 * @param context
	 *            当前Acitivity的上下文
	 * @param username
	 *            用户名
	 * @param password
	 *            用户密码
	 */
	public boolean LoginStartActivity(String username, String password, SharedPreferences sp) {
		boolean isSuccess = false;
		InternetStatus is = new InternetStatus(context);
		boolean isF = sp.getBoolean(Attr.isFirstLogin, true);
		if (is.isNetworkConnected()) {
			if (WebLoginSuccess(username, password)) {
				ManagerLoginTool teaWebTool = new ManagerLoginTool(context);
				if (isF) {// 第一次获得所有数据操作
					try {
						teaWebTool.firstLogin(username);
						isSuccess = true;
						sp.edit().putBoolean(Attr.isFirstLogin, false).commit();
					} catch (Exception e) {
						sp.edit().putBoolean(Attr.isFirstLogin, true);
					} catch (Error ex) {
						sp.edit().putBoolean(Attr.isFirstLogin, true);
					}
				} else {// 更新数据操作
					try {
						teaWebTool.secondLogin(username);
						isSuccess = true;
					} catch (Error ex) {
					}
				}
			}
		} else {
			return LocalLoginSuccess(username, password);
		}
		return isSuccess;
	}

	public ManageTool(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 根据用户类型进行登录，成功则返回true
	 * 
	 * @param userType
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean WebLoginSuccess(String username, String password) {
		WebTool web = null;
		try {
			web = new WebTool(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (web != null) {
			return web.LoginSuccess(Attr.userType, username, password);
		} else
			return false;
	}

	/**
	 * 无网络连接状态下从本地登陆
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean LocalLoginSuccess(String username, String password) {
		return false;// new LocalSqlTool(context).localLogin(username,
						// password);
	}

	/**
	 * 根据学生学号获得学生图片
	 * 
	 * @param sno
	 * @return
	 */
	public Bitmap getPhotoBySno(String sno) {
		return new LocalSqlTool(context).getPhotoBySno(sno);
	}

	/**
	 * 获得所管理的所有班级
	 * 
	 * @return
	 */
	public List<String> getAllClass() {
		return new LocalSqlTool(context).getAllClass();
	}

	/**
	 * 根据班级获得所有课程名称
	 * 
	 * @return
	 */
	public List<String> getAllCourseByCla(String cla) {
		return new LocalSqlTool(context).getAllCourseByCla(cla);
	}

	/**
	 * 根据学生姓名获得其相关信息
	 * 
	 * @param name
	 * @return
	 */
	public HashMap<String, String> getStuInfoByName(String name) {
		return new LocalSqlTool(context).getStuInfoByName(name);
	}

	/**
	 * 
	 * 根据学生编号获得学生的考勤详细信息
	 * 
	 * @param sno
	 * @return
	 */
	public List<KQStuPerson> getKQStuPerson(String sno) {
		List<KQStuPerson> list = new ArrayList<KQStuPerson>();
		try {
			WebTool web = new WebTool(context);
			list = web.getKQStuPerson(sno);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 根据班级 和 课程获得其相应学生的考勤情况
	 * 
	 * @param uno
	 * @return
	 */
	public List<KQStuClass> getStuPrivateKQByUno(String course, String cla) {
		List<KQStuClass> list = new ArrayList<KQStuClass>();
		WebTool web;
		try {
			web = new WebTool(context);
			list = web.getStuPrivateKQByUno(course, cla);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据导员编号获得最新的考勤通知信息
	 * 
	 * @param uno
	 * @return
	 */
	public List<String> getLatestKqInfoByUno(String uno) {
		try {
			WebTool web = new WebTool(context);
			return web.getLatestKqInfoByUno(uno);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	/**
	 * 向本地插入最新的考勤信息
	 * 
	 * @param list
	 */
	public void insertKqInfo(List<KQInfo> list) {
		new LocalSqlTool(context).insertKqInfo(list);
	}

	/**
	 * 获得本地考勤信息
	 * 
	 * @return
	 */
	public List<KQInfo> getKqInfo() {
		return new LocalSqlTool(context).getKqInfo();
	}

	/**
	 * 更新考勤信息被阅读过
	 * 
	 * @param id
	 */
	public void updateKqInfo(List<Integer> listId) {
		new LocalSqlTool(context).updateKqInfo(listId);
	}
}
