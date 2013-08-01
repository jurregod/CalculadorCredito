package com.cristhian.calculadorcredito.utils;

import java.awt.font.NumericShaper;
import java.text.NumberFormat;
import java.util.Locale;

import android.text.Editable;
import android.text.TextWatcher;

public class MoneyTextWatcher implements TextWatcher {

	boolean mEditing;
	
	public MoneyTextWatcher(){
		this.mEditing = false;
	}
	
	@Override
	public void afterTextChanged(Editable e) {
		if(!mEditing){
			mEditing = true;
			String digits = e.toString();
			NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
			try{
				String formatted = format.format(Double.parseDouble(digits)/100);
				e.replace(0, e.length(), formatted);
			}catch(Exception ex){
				e.clear();
			}
			mEditing = false;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

}
