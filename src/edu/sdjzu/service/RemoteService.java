package edu.sdjzu.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.kqsystem_manager.R;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.localtool.InternetStatus;
import edu.sdjzu.manager.LoginAct;
import edu.sdjzu.managetools.ManageTool;
import edu.sdjzu.model.KQInfo;

public class RemoteService extends Service {
	private InternetStatus internetStatus = null;
	private final int INFO_GET_TIME = 5000;// ���������Ƿ��п�����Ϣ������ʱ����
	private ManageTool manageTool = null;
	private Runnable getInfoTask;// �ӷ�������ÿ�����Ϣ������
	private boolean isInfoGetting = false;// �Ƿ����ڴӷ�������ÿ�����Ϣ�ı�ʶ

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		startGetNewKqInfo();
		return super.onStartCommand(intent, flags, startId);
	}

	private void startGetNewKqInfo() {
		isInfoGetting = true;
		new Thread(getInfoTask).start();
	}

	private void init() {
		manageTool = new ManageTool(getApplicationContext());
		internetStatus = new InternetStatus(getApplicationContext());
		getInfoTask = new Runnable() {
			@Override
			public void run() {
				while (isInfoGetting) {
//					Log.i("chen", "getInfo ing");
					List<String> list = manageTool.getLatestKqInfoByUno(LoginAct.userName);
					List<KQInfo> listKq = new ArrayList<KQInfo>();
					String tipMsg="";
					for (String s : list) {
						tipMsg+=s;
						String[] sArray = s.split("��");
						Log.i("chen", "msg=" + s);
						if (sArray.length >= 2) {
							KQInfo kqInfo = new KQInfo();
							kqInfo.setTname(sArray[1]);
							kqInfo.setIsRead(0);
							kqInfo.setDateTime(CurrentDateTimeSec());
							kqInfo.setMsg(sArray[0]);
							listKq.add(kqInfo);
						}
					}
					if (listKq.size() > 0) {
						manageTool.insertKqInfo(listKq);
						Intent intent = new Intent(getString(R.string.ACTION_KQ_LATEST_INFO));
						intent.putExtra("info", tipMsg);
						sendBroadcast(intent);
					}
					try {
						Thread.sleep(INFO_GET_TIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};
	}

	/**
	 * ��ȡ��ǰ��ʱ��
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private String CurrentDateTimeSec() {
		Calendar ca = Calendar.getInstance();
		Date nowTime = ca.getTime();
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetimeString = datetime.format(nowTime);
		Log.i("chen", "time->" + datetimeString);
		return datetimeString;
	}

	@Override
	public void onCreate() {
		init();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
