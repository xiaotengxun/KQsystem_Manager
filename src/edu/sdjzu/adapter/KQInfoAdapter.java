package edu.sdjzu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sdjzu.manager.R;
import edu.sdjzu.managetools.ManageDtTool;
import edu.sdjzu.model.KQInfo;

public class KQInfoAdapter extends BaseAdapter {
	private boolean isDeleteBtnShow = false;
	private List<KQInfo> list = new ArrayList<KQInfo>();
	private LayoutInflater mInflater;
	private HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
	private Context context;

	public KQInfoAdapter(Context context, List<KQInfo> list) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void setKqInfo(List<KQInfo> list) {
		this.list = list;
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
		convertView = mInflater.inflate(R.layout.kq_kqinfo_item, null);
		ViewHolder vh = new ViewHolder();
		vh.tvTname = (TextView) convertView.findViewById(R.id.kq_kqinfo_teacher);
		vh.tvMsg = (TextView) convertView.findViewById(R.id.kq_kqinfo_msg);
		vh.tvDateTime = (TextView) convertView.findViewById(R.id.kq_kqinfo_time);
		vh.btnDelete = (ImageView) convertView.findViewById(R.id.kq_kqinfo_delete);
		KQInfo kqInfo = list.get(position);
		vh.tvTname.setText(kqInfo.getTname());
		vh.tvMsg.setText(kqInfo.getMsg());
		vh.tvDateTime.setText(kqInfo.getDateTime());
		Constant constant = new Constant();
		constant.sqlId = kqInfo.getId();
		constant.imgId = R.drawable.kq_info_delete_no;
		vh.btnDelete.setTag(constant);
		if (kqInfo.getIsRead() == 0) {
			vh.tvTname.setTextColor(Color.RED);
		}
		if (isDeleteBtnShow == true) {
			vh.btnDelete.setVisibility(View.VISIBLE);
			vh.btnDelete.setImageResource(R.drawable.kq_info_delete_no);
			vh.btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Constant con = (Constant) v.getTag();
					if (con.imgId == R.drawable.kq_info_delete_no) {
						con.imgId = R.drawable.kq_info_delete_yes;
						hashMap.put(con.sqlId, 1);
					} else {
						con.imgId = R.drawable.kq_info_delete_no;
						hashMap.remove(con.sqlId);
					}
					((ImageView) v).setImageResource(con.imgId);
					v.setTag(con);
				}
			});
		} else {
			vh.btnDelete.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	/**
	 * 删除被选择的内容
	 */
	public void deleteSelectedData() {
		isDeleteBtnShow = false;
		Set<Entry<Integer, Integer>> listSet = hashMap.entrySet();
		List<Integer> listId = new ArrayList<Integer>();
		for (Entry<Integer, Integer> entry : listSet) {
			listId.add(entry.getKey());
		}
		ManageDtTool managerTool = new ManageDtTool(context);
		managerTool.updateKqInfo(listId);
	}

	public void setShowDeleteBtn(boolean b) {
		isDeleteBtnShow = b;
		if (isDeleteBtnShow == false) {
			hashMap.clear();
		}
	}

	public boolean isShowDeleteBtn() {
		return isDeleteBtnShow;
	}

	private class ViewHolder {
		TextView tvTname, tvMsg, tvDateTime;
		ImageView btnDelete;
	};

	private class Constant {
		int sqlId;
		int imgId;
	};

}
