package com.cristhian.calculadorcredito.utils;

import java.text.NumberFormat;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class MoneyFormatFocusChange implements OnFocusChangeListener {

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText control = (EditText)v;
		String digits = control.getText().toString();
		if (hasFocus){
			digits = digits.replace("$ ", "");
			control.setText(digits);
			control.setFocusable(true);
		}else if (digits.length() > 0){
			digits = digits.replaceAll("\\D", "");
	        NumberFormat nf = NumberFormat.getCurrencyInstance();
	        nf.setMaximumFractionDigits(0);
            String formatted = nf.format(Double.parseDouble(digits));
            control.setText(formatted.toString());
		}		
	}
}
