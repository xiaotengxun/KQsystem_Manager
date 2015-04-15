package edu.sdjzu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sdjzu.manager.R;
import edu.sdjzu.model.KQStuPerson;

public class KQCheckPersonAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<KQStuPerson> list = new ArrayList<KQStuPerson>();;

	public KQCheckPersonAdapter(Context ctx, List<KQStuPerson> list) {

		this.context = ctx;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.kq_check_person_item, null);
		ImageView icon = (ImageView) convertView.findViewById(R.id.kq_check_person_kq_icon);
		TextView txt = (TextView) convertView.findViewById(R.id.kq_check_person_kq_tv);
		KQStuPerson stu = list.get(position);
		String s = stu.getDate() + " " + "(" + stu.getWeek() + "周周" + stu.getWeekDay() + "  " + stu.getjClass() + "节)";

		txt.setText(s);
		if (stu.getType().equals("请假")) {
			icon.setImageResource(R.drawable.stu_kqlist_kqstatus_qingjia);
		} else if (stu.getType().equals("缺勤")) {
			icon.setImageResource(R.drawable.stu_kqlist_kqstatus_disabsent);
		} else if (stu.getType().equals("迟到")) {
			icon.setImageResource(R.drawable.stu_kqlist_kqstatus_late);
		}
		return convertView;
	}

}
