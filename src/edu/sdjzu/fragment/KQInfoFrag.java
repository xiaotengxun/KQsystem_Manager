package edu.sdjzu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kqsystem_manager.R;

import edu.sdjzu.adapter.KQInfoAdapter;
import edu.sdjzu.manager.ManagerIndexAct;
import edu.sdjzu.managetools.ManageTool;
import edu.sdjzu.model.KQInfo;

public class KQInfoFrag extends Fragment {
	private NewKqInfoReceiver newKqInfoReceiver = null;
	private ListView listView;
	private KQInfoAdapter adapter;
	private List<KQInfo> listKq = new ArrayList<KQInfo>();
	private Handler mHandler;
	private final static int NEW_KQ_INFO = 0;
	private ManageTool managerTool = null;
	private View menuView;
	private TextView menuDelete, menuCancel;
	private DataSetObserver dataSetObserver=null;
	String tag="chen";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		registerReceiver();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.kq_kqinfo, null);
	}

	private void registerReceiver() {
		IntentFilter intentFilter = new IntentFilter(getString(R.string.ACTION_KQ_LATEST_INFO));
		intentFilter.setPriority(800);
		newKqInfoReceiver = new NewKqInfoReceiver();
		getActivity().registerReceiver(newKqInfoReceiver, intentFilter);
	}
	private void initView() {
		menuView = getView().findViewById(R.id.rel_menu);
		menuCancel = (TextView) getView().findViewById(R.id.kq_info_menu_cancel);
		menuDelete = (TextView) getView().findViewById(R.id.kq_info_menu_delete);
		listView = (ListView) getView().findViewById(R.id.kq_kqinfo_lsv);
		adapter = new KQInfoAdapter(getActivity(), listKq);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (!adapter.isShowDeleteBtn()) {
					adapter.setShowDeleteBtn(true);
					adapter.notifyDataSetChanged();
					menuView.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		menuCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuView.setVisibility(View.GONE);
				adapter.setShowDeleteBtn(false);
				adapter.notifyDataSetChanged();
				
			}
		});
		menuDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuView.setVisibility(View.GONE);
				adapter.deleteSelectedData();
				listKq = managerTool.getKqInfo();
				adapter.setKqInfo(listKq);
				adapter.notifyDataSetChanged();
			}
		});
		managerTool = new ManageTool(getActivity());
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == NEW_KQ_INFO) {
					String msgContent=(String) msg.obj;
					if(null==msgContent){
						msgContent="";
					}
					managerTool.noticeNewKq(msgContent);
					adapter.setKqInfo(listKq);
					adapter.notifyDataSetChanged();
				}
			}
		};
		if(null == dataSetObserver){
			dataSetObserver=new DataSetObserver() {

				@Override
				public void onChanged() {
					super.onChanged();
					if(listKq.size()>0){
						listView.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onInvalidated() {
					// TODO Auto-generated method stub
					super.onInvalidated();
				}
			};
			adapter.registerDataSetObserver(dataSetObserver);
		}
	
		
	}

	private class NewKqInfoReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String msgContent=intent.getStringExtra("info");
			listKq = managerTool.getKqInfo();
			Message msg=new Message();
			msg.what=NEW_KQ_INFO;
			msg.obj=msgContent;
			mHandler.sendMessage(msg);
			abortBroadcast();
		}

	}

	@Override
	public void onDestroy() {
		if (newKqInfoReceiver != null) {
			getActivity().unregisterReceiver(newKqInfoReceiver);
			newKqInfoReceiver = null;
		}
		if(null == dataSetObserver){
			adapter.registerDataSetObserver(dataSetObserver);
			dataSetObserver=null;
		}
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(tag, "1111111>>>onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDetach() {
		Log.i(tag, "1111111>>>onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i(tag, "1111111>>>onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i(tag, "1111111>>>onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.i(tag, "1111111>>>onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i(tag, "1111111>>>onActivityCreated");
		super.onStop();
	}


}
