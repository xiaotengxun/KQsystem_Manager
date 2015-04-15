package edu.sdjzu.fragment;

import java.util.ArrayList;
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
import edu.sdjzu.adapter.KQCheckClassAdapter;
import edu.sdjzu.manager.R;
import edu.sdjzu.managetools.ManageDtTool;
import edu.sdjzu.model.KQStuClass;

public class KQCheckClassFrag extends Fragment {
	private String className = "";
	private String courseName = "";
	private ListView listView;
	private TextView tvCourse, tvClass, tvQingjia, tvChidao, tvQueqing;
	private ManageDtTool manageTool;
	private Handler mHandler;
	private final static int CLASS_KQ_INFO_GET = 0;
	private List<KQStuClass> listStu = new ArrayList<KQStuClass>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		className = getArguments().getString("class");
		courseName = getArguments().getString("courseName");
		initView();
	}

	private void initView() {
		tvCourse = (TextView) getView().findViewById(R.id.kq_check_class_course);
		tvClass = (TextView) getView().findViewById(R.id.kq_check_class_class);
		tvChidao = (TextView) getView().findViewById(R.id.kq_check_class_chidao);
		tvQingjia = (TextView) getView().findViewById(R.id.kq_check_class_qinjia);
		tvQueqing = (TextView) getView().findViewById(R.id.kq_check_class_queqing);
		listView = (ListView) getView().findViewById(R.id.kq_check_class_lsv);

		tvCourse.setText(courseName);
		tvClass.setText(className);

		manageTool = new ManageDtTool(getActivity());
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == CLASS_KQ_INFO_GET) {
					int countsChidao = 0;
					int countsQingjia = 0;
					int countsQueqing = 0;
					for (KQStuClass kqStuClass : listStu) {
						countsChidao += kqStuClass.getChidao();
						countsQingjia += kqStuClass.getQingjia();
						countsQueqing += kqStuClass.getQueqing();
					}
					tvChidao.setText(countsChidao + " ´Î");
					tvQingjia.setText(countsQingjia + " ´Î");
					tvQueqing.setText(countsQueqing + " ´Î");
					KQCheckClassAdapter adater = new KQCheckClassAdapter(getActivity(), listStu);
					listView.setAdapter(adater);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				listStu = manageTool.getStuPrivateKQByUno(courseName, className);
				mHandler.sendEmptyMessage(CLASS_KQ_INFO_GET);
			}
		}.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.kq_check_class, null);
	}

}
