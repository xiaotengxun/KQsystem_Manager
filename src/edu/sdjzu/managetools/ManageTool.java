package edu.sdjzu.managetools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.kqsystem_manager.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.Log;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.localtool.DatabaseManager;
import edu.sdjzu.localtool.InternetStatus;
import edu.sdjzu.localtool.LocalSqlTool;
import edu.sdjzu.manager.ManagerIndexAct;
import edu.sdjzu.model.KQInfo;
import edu.sdjzu.model.KQStuClass;
import edu.sdjzu.model.KQStuPerson;
import edu.sdjzu.model.KQresult;
import edu.sdjzu.model.Students;
import edu.sdjzu.model.TeachProgress;

public class ManageTool {
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
		InternetStatus is = new InternetStatus(context);
		boolean isF = sp.getBoolean(Attr.isFirstLogin, true);
		Log.i("chen", "net is connected >>" + is.isNetworkConnected());
		if (is.isNetworkConnected()) {
			if (WebLoginSuccess(username, password)) {
				ManagerLoginTool teaWebTool = new ManagerLoginTool(context);
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

	private static int id = 1;

	/**
	 * �µĿ�����Ϣ֪ͨ
	 * 
	 * @param msgContent
	 */
	public void noticeNewKq(String msgContent) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.xinxin1, context.getString(R.string.kq_new_kq_tip),
				System.currentTimeMillis());
		n.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
		n.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(context, ManagerIndexAct.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("id", "info");
		PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo(context, "", msgContent, contentIntent);
		id++;
		nm.notify(id, n);
		VibratorPhone(2000);
	}
	public void VibratorPhone(int times){
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(times);
	}
}
