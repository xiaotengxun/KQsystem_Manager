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
	 * ����Spinner��ѡ����,�û��������������ָ����Activity���������½��Ϣ
	 * 
	 * @param context
	 *            ��ǰAcitivity��������
	 * @param username
	 *            �û���
	 * @param password
	 *            �û�����
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
				if (isF) {// ��һ�λ���������ݲ���
					try {
						teaWebTool.firstLogin(username);
						isSuccess = true;
						sp.edit().putBoolean(Attr.isFirstLogin, false).commit();
					} catch (Exception e) {
						sp.edit().putBoolean(Attr.isFirstLogin, true);
					} catch (Error ex) {
						sp.edit().putBoolean(Attr.isFirstLogin, true);
					}
				} else {// �������ݲ���
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
	 * �����û����ͽ��е�¼���ɹ��򷵻�true
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
	 * ����������״̬�´ӱ��ص�½
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean LocalLoginSuccess(String username, String password) {
		return new LocalSqlTool(context).localLogin(username, password);
	}

	/**
	 * ����ѧ��ѧ�Ż��ѧ��ͼƬ
	 * 
	 * @param sno
	 * @return
	 */
	public Bitmap getPhotoBySno(String sno) {
		return new LocalSqlTool(context).getPhotoBySno(sno);
	}

	/**
	 * �������������а༶
	 * 
	 * @return
	 */
	public List<String> getAllClass() {
		return new LocalSqlTool(context).getAllClass();
	}

	/**
	 * ���ݰ༶������пγ�����
	 * 
	 * @return
	 */
	public List<String> getAllCourseByCla(String cla) {
		return new LocalSqlTool(context).getAllCourseByCla(cla);
	}

	/**
	 * ����ѧ����������������Ϣ
	 * 
	 * @param name
	 * @return
	 */
	public HashMap<String, String> getStuInfoByName(String name) {
		return new LocalSqlTool(context).getStuInfoByName(name);
	}

	/**
	 * 
	 * ����ѧ����Ż��ѧ���Ŀ�����ϸ��Ϣ
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
	 * ���ݰ༶ �� �γ̻������Ӧѧ���Ŀ������
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
	 * ���ݵ�Ա��Ż�����µĿ���֪ͨ��Ϣ
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
	 * �򱾵ز������µĿ�����Ϣ
	 * 
	 * @param list
	 */
	public void insertKqInfo(List<KQInfo> list) {
		new LocalSqlTool(context).insertKqInfo(list);
	}

	/**
	 * ��ñ��ؿ�����Ϣ
	 * 
	 * @return
	 */
	public List<KQInfo> getKqInfo() {
		return new LocalSqlTool(context).getKqInfo();
	}

	/**
	 * ���¿�����Ϣ���Ķ���
	 * 
	 * @param id
	 */
	public void updateKqInfo(List<Integer> listId) {
		new LocalSqlTool(context).updateKqInfo(listId);
	}

	/**
	 * �򱾵ز���������Ϣ
	 * 
	 * @param list
	 */
	public void insertNewChatInfo(List<ChatInfo> list) {
		new LocalSqlTool(context).insertNewChatInfo(list);
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param list
	 */
	public void deleteChatInfo(List<ChatInfo> list) {
		new LocalSqlTool(context).deleteChatInfo(list);
	}

	/**
	 * ����������Ϣ��id������Ϣ���Ķ�״̬
	 * 
	 * @param id
	 */
	public void updateChatInfoReadState(String id) {
		new LocalSqlTool(context).updateChatInfoReadState(id);
	}

	/**
	 * ��������������Ŀ
	 * 
	 * @param no
	 * @return
	 */
	public List<ChatInfo> getChatGroup(String no) {
		return new LocalSqlTool(context).getChatGroup(no);
	}

	/**
	 * ���ȫ��ѧ��
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
	 * ������ϸ��������Ϣ
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
