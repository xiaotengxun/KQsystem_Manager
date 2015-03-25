package edu.sdjzu.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.kqsystem_manager.R;

import edu.sdjzu.adapter.TabFragmentPagerAdapter;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.fragment.KQCheckFrag;
import edu.sdjzu.fragment.KQInfoFrag;

/**
 * 主页界面
 * 
 * @author Administrator
 *
 */
public class ManagerIndexAct extends FragmentActivity {
	public int jno = -1;
	public int rno = -1;
	private String userName = "";
	protected static float currentIndicatorLeft = 0;
	private TabFragmentPagerAdapter framPageAdapter;
	private ViewPager mViewPage;
	private RadioGroup radioGroupTab;
	private ImageView tabIndictor;
	private int tabIndictorWidth = 0;
	private int tabIndictorCurrentLeft = 0;
	private String[] tabTitle = { "考勤查看", "消息" };
	private List<Fragment> listFrag = new ArrayList<Fragment>();
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_manager_index);
		intent = new Intent();
		intent.putExtra(Attr.jnoKey, String.valueOf(jno));
		intent.putExtra(Attr.rnoKey, String.valueOf(rno));
		userName = LoginAct.userName;
		findView();
		initView();
		setListener();
		initActionBar();
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("");
	}

	private void findView() {
		mViewPage = (ViewPager) findViewById(R.id.mViewPager);
		radioGroupTab = (RadioGroup) findViewById(R.id.group_tab);
		tabIndictor = (ImageView) findViewById(R.id.tab_indictor);
	}

	private void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		tabIndictorWidth = dm.widthPixels / tabTitle.length;
		LayoutParams cursor_Params = tabIndictor.getLayoutParams();
		cursor_Params.width = tabIndictorWidth;
		tabIndictor.setLayoutParams(cursor_Params);
		LayoutInflater mInflater = LayoutInflater.from(this);
		for (int i = 0; i < tabTitle.length; i++) {
			RadioButton rb = (RadioButton) mInflater.inflate(R.layout.radiogroup_item, null);
			rb.setId(i);
			rb.setText(tabTitle[i]);
			rb.setLayoutParams(new LayoutParams(tabIndictorWidth, LayoutParams.MATCH_PARENT));
			radioGroupTab.addView(rb);
		}
		listFrag.add(new KQCheckFrag());
		listFrag.add(new KQInfoFrag());
		framPageAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), userName, listFrag);
		mViewPage.setAdapter(framPageAdapter);
		mViewPage.setCurrentItem(0);
	}

	private void setListener() {
		mViewPage.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if (radioGroupTab.getChildCount() > arg0) {
					((RadioButton) radioGroupTab.getChildAt(arg0)).performClick();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		radioGroupTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (radioGroupTab.getChildAt(checkedId) != null) {

					TranslateAnimation animation = new TranslateAnimation(currentIndicatorLeft,
							((RadioButton) radioGroupTab.getChildAt(checkedId)).getLeft(), 0f, 0f);
					animation.setInterpolator(new LinearInterpolator());
					animation.setDuration(100);
					animation.setFillAfter(true);
					// 执行位移动画
					tabIndictor.startAnimation(animation);
					mViewPage.setCurrentItem(checkedId); // ViewPager 跟随一起 切换
					// 记录当前 下标的距最左侧的 距离
					currentIndicatorLeft = ((RadioButton) radioGroupTab.getChildAt(checkedId)).getLeft();
				}
			}
		});
	}

}
