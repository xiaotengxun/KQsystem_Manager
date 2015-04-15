package edu.sdjzu.manager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import edu.sdjzu.fragment.KQCheckClassFrag;
import edu.sdjzu.fragment.KQCheckPersonFrag;

public class KQCheckDetailAct extends FragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.kq_check_detail);
		choseView();
	}

	private void choseView() {
		String id = getIntent().getStringExtra("id");
		if (id != null) {
			if (id.equals("person")) {
				String stuName = getIntent().getStringExtra("stuName");
				KQCheckPersonFrag frag = new KQCheckPersonFrag();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				Bundle b = new Bundle();
				b.putString("stuName", stuName);
				frag.setArguments(b);
				transaction.replace(R.id.kq_check_detail_container, frag);
				transaction.commit();

			} else if (id.equals("class")) {
				String classStr = getIntent().getStringExtra("class");
				String courseName = getIntent().getStringExtra("courseName");
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				KQCheckClassFrag frag = new KQCheckClassFrag();
				Bundle b = new Bundle();
				b.putString("class", classStr);
				b.putString("courseName", courseName);
				frag.setArguments(b);
				transaction.replace(R.id.kq_check_detail_container, frag);
				transaction.commit();
			}
		}
	}

}
