package edu.sdjzu.adapter;

import java.util.List;

import edu.sdjzu.attr.Attr;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

	private static String userName;
	private static List<Fragment> listFrag = null;

	public TabFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public TabFragmentPagerAdapter(FragmentManager fm, String userName, List<Fragment> listF) {
		super(fm);
		this.userName = userName;
		this.listFrag = listF;
	}

	public static void setUserName(String uName) {
		userName = uName;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fg = null;
		Bundle args = new Bundle();
//		args.putString(Attr.userKey, userName);
		if (arg0 < listFrag.size()) {
			fg = listFrag.get(arg0);
			fg.setArguments(args);
		}
		return fg;
	}

	@Override
	public int getCount() {
		return listFrag.size();
	}
}
