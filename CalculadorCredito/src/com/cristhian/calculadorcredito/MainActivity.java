package com.cristhian.calculadorcredito;

import com.cristhian.calculadorcredito.utils.MoneyTextWatcher;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EditText valor = (EditText)findViewById(R.id.etMonto);
		valor.setInputType(InputType.TYPE_CLASS_NUMBER);
		valor.addTextChangedListener(new MoneyTextWatcher());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
