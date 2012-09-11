package com.acedanger.viewpager.support;

import java.text.DateFormatSymbols;

import android.content.Context;
import android.widget.Toast;

public class Utility {
	public static String pad(int c) {
		return c >= 10 ? String.valueOf(c) : "0" + String.valueOf(c);
	}

	public static void showToast(Context ctx, CharSequence msg, int length) {
		Toast.makeText(ctx, msg, length).show();
	}
	
	public static String getMonth(int month) {
	    return (month >= 1 && month <= 12) ? new DateFormatSymbols().getMonths()[month-1] : "nada month";
	}
}