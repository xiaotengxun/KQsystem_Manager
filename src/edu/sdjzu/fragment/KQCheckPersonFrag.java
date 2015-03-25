package edu.sdjzu.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kqsystem_manager.R;

import edu.sdjzu.adapter.KQCheckPersonAdapter;
import edu.sdjzu.managetools.ManageTool;
import edu.sdjzu.model.KQStuPerson;

public class KQCheckPersonFrag extends Fragment {
	private String stuName = "";
	private String stuClass = "", stuSno = "";
	private TextView tvStuName, tvStuClass, tvStuSno;
	private ListView recordListView;
	private ManageTool manageTool;
	private HashMap<String, String> hashStuInfo = new HashMap<String, String>();
	private Handler mHandler;
	private static final int KQ_PERSON_INFO = 1;
	private List<KQStuPerson> listKqStuPerson = new ArrayList<KQStuPerson>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		stuName = getArguments().getString("stuName");
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.kq_check_person, null);
	}

	private void initView() {
		manageTool = new ManageTool(getActivity());
		tvStuClass = (TextView) getView().findViewById(R.id.kq_check_person_stuclass);
		tvStuName = (TextView) getView().findViewById(R.id.kq_check_person_stuname);
		tvStuSno = (TextView) getView().findViewById(R.id.kq_check_person_stusno);
		recordListView = (ListView) getView().findViewById(R.id.kq_check_person_lsv);
		hashStuInfo = manageTool.getStuInfoByName(stuName);
		stuClass = hashStuInfo.get("class");
		stuSno = hashStuInfo.get("sno");
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == KQ_PERSON_INFO) {
					recordListView.setAdapter(new KQCheckPersonAdapter(getActivity(), listKqStuPerson));
				}
			}
		};
		if (stuSno == null || stuClass == null) {
			Toast.makeText(getActivity(), getActivity().getString(R.string.kq_check_person_tip1), 1000).show();
		} else {
			tvStuClass.setText(stuClass);
			tvStuSno.setText(stuSno);
			tvStuName.setText(stuName);
			new Thread() {
				@Override
				public void run() {
					listKqStuPerson = new ArrayList<KQStuPerson>();
					listKqStuPerson = manageTool.getKQStuPerson(stuSno);
					mHandler.sendEmptyMessage(KQ_PERSON_INFO);
				}
			}.start();

		}
	}

}
