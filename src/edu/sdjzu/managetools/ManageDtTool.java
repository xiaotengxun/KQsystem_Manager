package edu.sdjzu.managetools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Vibrator;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.localtool.InternetStatus;
import edu.sdjzu.localtool.LocalSqlTool;
import edu.sdjzu.manager.LoginAct;
import edu.sdjzu.manager.ManagerIndexAct;
import edu.sdjzu.manager.R;
import edu.sdjzu.model.ChatInfo;
import edu.sdjzu.model.KQInfo;
import edu.sdjzu.model.KQStuClass;
import edu.sdjzu.model.KQStuPerson;
import edu.sdjzu.model.Students;
import edu.sdjzu.xmpp.AmackManage;

public class ManageDtTool {
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
		boolean isF = sp.getBoolean(Attr.isFirstLogin, true);
		ManagerLgTool teaWebTool = new ManagerLgTool(context);
		String lastUserName = sp.getString(Attr.loginUserName, null);
		boolean isCacheClear = false;
		if (null != lastUserName && !lastUserName.equals(username)) {
			isCacheClear = true;
			teaWebTool.clearCache();
			isF = true;
		}
		InternetStatus is = new InternetStatus(context);
		if (is.isNetworkConnected()) {
			AmackManage am = new AmackManage();
			boolean isRegistered = sp.getBoolean(Attr.userRegisterKey, false);
			if (WebLoginSuccess(username, password) && AmackManage.login(username, password, isRegistered)) {
				isRegistered = true;
				sp.edit().putBoolean(Attr.userRegisterKey, isRegistered).commit();
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
			context.startService(new Intent(context.getString(R.string.ACTION_NEW_KQ_INFO)));
		} else {
			return LocalLoginSuccess(username, password);
		}
		// sp.edit().putString("ServiceLoginUserName", username).commit();
		return isSuccess;
	}

	public ManageDtTool(Context context) {
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
		return new LocalSqlTool(context).localLogin(username, password);
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

	/**
	 * 向本地插入聊天消息
	 * 
	 * @param list
	 */
	public void insertNewChatInfo(List<ChatInfo> list) {
		new LocalSqlTool(context).insertNewChatInfo(list);
	}

	/**
	 * 删除聊天消息
	 * 
	 * @param list
	 */
	public void deleteChatInfo(List<ChatInfo> list) {
		new LocalSqlTool(context).deleteChatInfo(list);
	}

	/**
	 * 根据聊天消息的id更新消息的阅读状态
	 * 
	 * @param id
	 */
	public void updateChatInfoReadState(String id) {
		new LocalSqlTool(context).updateChatInfoReadState(id);
	}

	/**
	 * 获得聊天的种类条目
	 * 
	 * @param no
	 * @return
	 */
	public List<ChatInfo> getChatGroup(String no) {
		return new LocalSqlTool(context).getChatGroup(no);
	}

	/**
	 * 获得全部学生
	 * 
	 * @return
	 */
	public List<Students> getAllStu() {
		return new LocalSqlTool(context).getAllStu();
	}

	public String getManageName(String no) {
		return new LocalSqlTool(context).getManageName(no);
	}

	/**
	 * 返回详细的聊天信息
	 * 
	 * @param pSno
	 * @return
	 */
	public List<ChatInfo> getChatDetail(String pSno) {
		return new LocalSqlTool(context).getChatDetail(pSno);
	}

	public void deleteChatGroup(List<String> list, String tno) {
		new LocalSqlTool(context).deleteChatGroup(list, tno);
	}

}
