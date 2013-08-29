package com.cristhian.calculadorcredito.listener;

import com.cristhian.calculadorcredito.R;

import android.app.Activity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;

public class TiempoItemSelectedListener implements OnItemSelectedListener {
	
	private Activity actividad;
	
	public TiempoItemSelectedListener(Activity actividad){
		this.actividad = actividad;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		Spinner sp = (Spinner)parent;
		String item = sp.getSelectedItem().toString().substring(0, 1);
		EditText tiempo = (EditText)actividad.findViewById(R.id.etTiempo);
		tiempo.setText("");
		if (item.equalsIgnoreCase("A")){
			tiempo.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
		}else{
			tiempo.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(3)});
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}


}
