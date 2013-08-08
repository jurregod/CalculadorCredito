package com.cristhian.calculadorcredito;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.cristhian.calculadorcredito.utils.MoneyFormatFocusChange;
import com.cristhian.calculadorcredito.utils.PercentFormatFocusChange;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Visibility;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;

public class MainActivity extends Activity {
	
	private EditText etValor;
	private EditText etInteres;
	private EditText etTiempo;
	private AdView anuncio;
	private LocationManager locManager;
	private LinearLayout lyDetalle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lyDetalle = (LinearLayout)findViewById(R.id.lyDetalleCuota);
		lyDetalle.setVisibility(LinearLayout.INVISIBLE);
		etValor = (EditText)findViewById(R.id.etMonto);
		etValor.setOnFocusChangeListener(new MoneyFormatFocusChange());
		etInteres = (EditText)findViewById(R.id.etInteres);
		etInteres.setOnFocusChangeListener(new PercentFormatFocusChange());
		etTiempo = (EditText)findViewById(R.id.etTiempo);
		//Obtenemos una referencia al servicio de localizacion
		locManager = (LocationManager)getSystemService(LOCATION_SERVICE);		
		anuncio = new AdView(this, AdSize.BANNER, "ca-app-pub-7618114390015000/8160452378");
		LinearLayout lyAdd = (LinearLayout)findViewById(R.id.lyMain);
		lyAdd.addView(anuncio);
		AdRequest request = new AdRequest();
		Location ubicacion = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (ubicacion != null){
			request.setLocation(ubicacion);
		}
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice("2AB3408AF84416D0B0DB200867D11643");
		anuncio.loadAd(request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onDestroy() {
		anuncio.destroy();
	  	super.onDestroy();
	}
	
	public void calcularCuota(View boton){
		if (etValor.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Ingrese el valor del crédito", Toast.LENGTH_SHORT).show();
			etValor.requestFocus();
			return;
		}
		if (etInteres.getText().length() == 0){
			Toast.makeText(getApplicationContext(), "Ingrese el interés del crédito", Toast.LENGTH_SHORT).show();
			etInteres.requestFocus();
			return;
		}
		if (etTiempo.getText().length() == 0){
			Toast.makeText(getApplicationContext(), "Ingrese el tiempo del crédito", Toast.LENGTH_SHORT).show();
			etTiempo.requestFocus();
			return;
		}
		lyDetalle.setVisibility(LinearLayout.VISIBLE);
	}
	
}
