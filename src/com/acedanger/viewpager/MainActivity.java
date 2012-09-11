package com.acedanger.viewpager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.acedanger.viewpager.support.Constants;
import com.acedanger.viewpager.support.CustomHistoryView;
import com.acedanger.viewpager.support.DataSummary;
import com.acedanger.viewpager.support.DbHelper;
import com.acedanger.viewpager.support.History;
import com.acedanger.viewpager.support.Utility;

public class MainActivity extends FragmentActivity {
	ArrayList<DataSummary> summary;
	int mMonth = 0, mYear = 0;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	static DbHelper mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDbHelper = DbHelper.getInstance(this);

		summary = mDbHelper.getDataSummary(DbHelper.TABLE_HISTORY);

		setContentView(R.layout.activity_main);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			DataSummary sumItem = summary.get(i);
			args.putString(DummySectionFragment.YEAR, sumItem.getYear());
			args.putString(DummySectionFragment.MONTH, sumItem.getMonth());
			args.putString(DummySectionFragment.COUNT, sumItem.getCount());
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return summary.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			DataSummary sumItem = summary.get(position);
			String monthName = Utility.getMonth(Integer.valueOf(sumItem.getMonth())).toUpperCase();
			CharSequence pageTitle = monthName + ' ' + sumItem.getYear();
			return pageTitle;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public static final String YEAR = "year";
		public static final String MONTH = "month";
		public static final String COUNT = "count";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			Bundle args = getArguments();
			String strYear = args.getString(YEAR);
			String strMonth = args.getString(MONTH);
			Integer iMonth = Integer.valueOf(strMonth);
			ArrayList<History> wods = mDbHelper.getHistoryByMonth(strYear, 
					Utility.pad(iMonth), Constants.SORT_BY_DATE);
			
			View mFragmentView = inflater.inflate(R.layout.wod_history, container, false);
			ListView listHistory = (ListView) mFragmentView.findViewById(R.id.listHistory);
			listHistory.setAdapter(new CustomHistoryView(container.getContext().getApplicationContext(), wods));
			return mFragmentView;
		}
	}
}
