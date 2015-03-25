package edu.sdjzu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kqsystem_manager.R;

import edu.sdjzu.manager.KQCheckDetailAct;
import edu.sdjzu.managetools.ManageTool;

public class KQCheckFrag extends Fragment {
	private Spinner spinnerCourse, spinnerClass;
	private String classStr = "", courseStr = "";
	private Handler mHandler;
	private final static int CLASS_UPDATE = 0;
	private ManageTool manageTool;
	private List<String> list;
	private Button personSearchBtn, classSearchBtn;
	private EditText stuNameEdit;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.kq_check, null);
	}

	private void initView() {
		personSearchBtn = (Button) getView().findViewById(R.id.kq_check_person_btn);
		classSearchBtn = (Button) getView().findViewById(R.id.kq_check_class_btn);
		stuNameEdit = (EditText) getView().findViewById(R.id.kq_check_person_name);
		spinnerClass = (Spinner) getView().findViewById(R.id.kq_class);
		spinnerCourse = (Spinner) getView().findViewById(R.id.kq_course);

		list = new ArrayList<String>();
		manageTool = new ManageTool(getActivity());
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == CLASS_UPDATE) {
					list = manageTool.getAllCourseByCla(classStr);
					Log.i("chen", "handler");
					spinnerCourse.setAdapter(new SpinAdapter(getActivity(), list));
				}
			}
		};
		list = manageTool.getAllClass();
		spinnerClass.setAdapter(new SpinAdapter(getActivity(), list));
		spinnerClass.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				classStr = parent.getSelectedItem().toString();
				mHandler.sendEmptyMessage(CLASS_UPDATE);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spinnerCourse.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				courseStr = parent.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				courseStr = "";
				Log.i("chen", "no course selected");

			}
		});

		personSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String str = stuNameEdit.getText().toString();
				if (str != null && !str.equals("")) {
					Intent intent = new Intent(getActivity(), KQCheckDetailAct.class);
					intent.putExtra("id", "person");
					intent.putExtra("stuName", str);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), getActivity().getString(R.string.kq_check_tip1), 1000).show();
				}

			}
		});
		classSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (courseStr != null && classStr != null && !courseStr.equals("") && !classStr.equals("")) {
					Intent intent = new Intent(getActivity(), KQCheckDetailAct.class);
					intent.putExtra("id", "class");
					intent.putExtra("class", classStr);
					intent.putExtra("courseName", courseStr);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), getActivity().getString(R.string.kq_check_tip2), 1000).show();
				}
			}
		});

	}

	private class SpinAdapter extends BaseAdapter implements SpinnerAdapter {
		private LayoutInflater mInflater;
		private List<String> list;
		private Context ctx;

		public SpinAdapter(Context context, List<String> list) {
			ctx = context;
			mInflater = LayoutInflater.from(ctx);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.kq_check_spinner_item, null);
			TextView tv = (TextView) convertView.findViewById(R.id.kq_spinner_tv);
			tv.setText(list.get(position));
			return convertView;
		}

	}
}
