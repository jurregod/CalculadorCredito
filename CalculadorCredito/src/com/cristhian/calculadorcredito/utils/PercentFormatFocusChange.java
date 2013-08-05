package com.cristhian.calculadorcredito.utils;

import java.util.ResourceBundle.Control;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class PercentFormatFocusChange implements OnFocusChangeListener {

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText control = (EditText)v;
		String text = control.getText().toString();
		if (hasFocus){
			text = text.replace("%", "");
			control.setText(text);
		}else{
			control.setText(text + "%");
		}
	}
}
