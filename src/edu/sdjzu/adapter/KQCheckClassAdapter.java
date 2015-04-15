package edu.sdjzu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.sdjzu.manager.R;
import edu.sdjzu.model.KQStuClass;

public class KQCheckClassAdapter extends BaseAdapter {
	private List<KQStuClass> list = new ArrayList<KQStuClass>();
	private LayoutInflater mInflater;

	public KQCheckClassAdapter(Context context, List<KQStuClass> list) {
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
		convertView = mInflater.inflate(R.layout.kq_check_class_item, null);
		ViewHolder vd = new ViewHolder();
		vd.name = (TextView) convertView.findViewById(R.id.kq_check_class_stu_name);
		vd.sno = (TextView) convertView.findViewById(R.id.kq_check_class_stu_sno);
		vd.chidao = (TextView) convertView.findViewById(R.id.kq_check_class_stu_chidao);
		vd.qingjia = (TextView) convertView.findViewById(R.id.kq_check_class_stu_qingjia);
		vd.queqing = (TextView) convertView.findViewById(R.id.kq_check_class_stu_queqing);
		KQStuClass stu = list.get(position);
		vd.name.setText(stu.getSname());
		vd.sno.setText(stu.getSno());
		vd.qingjia.setText(stu.getQingjia() + "´Î");
		vd.chidao.setText(stu.getChidao() + "´Î");
		vd.queqing.setText(stu.getQueqing() + "´Î");
		return convertView;
	}

	class ViewHolder {
		TextView name, sno, qingjia, chidao, queqing;
	};

}
