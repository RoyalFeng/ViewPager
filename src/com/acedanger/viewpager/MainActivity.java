package com.acedanger.viewpager;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
		
		Log.i(this.getClass().getSimpleName(), "onCreate method; just set the mViewPager adapter to mSectionsPagerAdapter");
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
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			Log.i(this.getClass().getSimpleName(), "SectionsPagerAdapter constructor");
		}
		
		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putParcelable(DummySectionFragment.SUM_ITEM, summary.get(i));
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
			CharSequence pageTitle = monthName + " " + sumItem.getYear();
			Configuration config = getResources().getConfiguration();
			if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				pageTitle = pageTitle + "(" + sumItem.getCount() + ")";
			}
			Log.i(this.getClass().getSimpleName(), "in the getPageTitle method with position = " +position+"; pageTitle is set to " +pageTitle);
			return pageTitle;
		}
	}
 
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public static final String SUM_ITEM = "summary_items";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			Bundle args = getArguments();
			DataSummary sumItem = args.getParcelable(SUM_ITEM);
			ArrayList<History> wods = mDbHelper.getHistoryByMonth(sumItem.getYear(), 
					Utility.pad(Integer.valueOf(sumItem.getMonth())), Constants.SORT_BY_DATE);
			View mFragmentView = inflater.inflate(R.layout.wod_history, container, false);
//			Log.i(this.getClass().getSimpleName(), 
//					"in the onCreateView method with year/month/(array)wods.size = " +strYear+"/"+Utility.pad(iMonth)+"/"+wods.size());
			ListView listHistory = (ListView) mFragmentView.findViewById(R.id.listHistory);
			listHistory.setAdapter(new CustomHistoryView(container.getContext().getApplicationContext(), wods));
			return mFragmentView;
		}
	}
}
