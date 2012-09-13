package com.acedanger.viewpager.support;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSummary implements Parcelable {
	public String year;
	public String month;
	public String count;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(year);
		parcel.writeString(month);
		parcel.writeString(count);
	}
	
	public DataSummary() {
	}
	
	private DataSummary(Parcel in) {
		this.year = in.readString();
		this.month = in.readString();
		this.count = in.readString();
	}
	
	public static final Parcelable.Creator<DataSummary> CREATOR = new Parcelable.Creator<DataSummary>() {
		public DataSummary createFromParcel(Parcel in) {
			return new DataSummary(in);
		}

		public DataSummary[] newArray(int size) {
			return new DataSummary[size];
		}
	};
}
