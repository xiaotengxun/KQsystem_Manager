package edu.sdjzu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kqsystem_manager.R;

import edu.sdjzu.adapter.KQInfoAdapter;
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
					adapter.setKqInfo(listKq);
					adapter.notifyDataSetChanged();
				}
			}
		};
	}

	private class NewKqInfoReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			listKq = managerTool.getKqInfo();
			mHandler.sendEmptyMessage(NEW_KQ_INFO);
		}

	}

	@Override
	public void onDestroy() {
		if (newKqInfoReceiver != null) {
			getActivity().unregisterReceiver(newKqInfoReceiver);
			newKqInfoReceiver = null;
		}
		super.onDestroy();
	}

}
