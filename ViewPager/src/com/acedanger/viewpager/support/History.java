package com.acedanger.viewpager.support;

import android.os.Parcel;
import android.os.Parcelable;

public class History {
	private String id;
	private String createTime;
	private String description;
	private String date;
	private String time;
	private String rx;
	private String results;
	private String webSyncTime;
	private String pr;
	private String notes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRx() {
		return rx;
	}

	public void setRx(String rx) {
		this.rx = rx;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWebSyncTime() {
		return webSyncTime;
	}

	public void setWebSyncTime(String webSyncTime) {
		this.webSyncTime = webSyncTime;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(id);
		parcel.writeString(createTime);
		parcel.writeString(description);
		parcel.writeString(date);
		parcel.writeString(time);
		parcel.writeString(rx);
		parcel.writeString(results);
		parcel.writeString(pr);
		parcel.writeString(notes);
		parcel.writeString(webSyncTime);
	}

	public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
		public History createFromParcel(Parcel in) {
			return new History(in);
		}

		public History[] newArray(int size) {
			return new History[size];
		}
	};

	public History() {
		// auto populate createTime?
		// this.createTime =
	}

	public History(String description, String results, String date,
			String time, String rx, String pr, String id) {
		this.description = description;
		this.results = results;
		this.date = date;
		this.time = time;
		this.rx = rx;
		this.pr = pr;
		this.id = id;
	}

	private History(Parcel in) {
		this.id = in.readString();
		this.createTime = in.readString();
		this.description = in.readString();
		this.date = in.readString();
		this.time = in.readString();
		this.rx = in.readString();
		this.results = in.readString();
		this.pr = in.readString();
		this.notes = in.readString();
		this.webSyncTime = in.readString();
	}
}
