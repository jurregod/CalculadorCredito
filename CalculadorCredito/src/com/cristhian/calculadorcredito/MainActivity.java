package com.cristhian.calculadorcredito;

import java.text.NumberFormat;

import com.cristhian.calculadorcredito.utils.MoneyFormatFocusChange;
import com.cristhian.calculadorcredito.utils.PercentFormatFocusChange;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	
	private EditText etValor;
	private EditText etInteres;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etValor = (EditText)findViewById(R.id.etMonto);
		etValor.setOnFocusChangeListener(new MoneyFormatFocusChange());
		etInteres = (EditText)findViewById(R.id.etInteres);
		etInteres.setOnFocusChangeListener(new PercentFormatFocusChange());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
