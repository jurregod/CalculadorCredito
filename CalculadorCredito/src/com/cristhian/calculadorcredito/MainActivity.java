package com.cristhian.calculadorcredito;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.cristhian.calculadorcredito.listener.MoneyFormatFocusChange;
import com.cristhian.calculadorcredito.listener.PercentFormatFocusChange;
import com.cristhian.calculadorcredito.listener.TiempoItemSelectedListener;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.*;
import com.google.ads.AdRequest.ErrorCode;

public class MainActivity extends Activity implements AdListener {
	
	private InterstitialAd anuncioInter;
	private EditText etValor;
	private EditText etInteres;
	private EditText etTiempo;
	private Spinner spInteres;
	private Spinner spTiempo;
	private AdView anuncio;
	private LocationManager locManager;
	private LinearLayout lyDetalle;
	private double monto;
	private int tiempo;
	private double interes;
	private double cuota;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spInteres = (Spinner)findViewById(R.id.spTipoInteres);
		spTiempo = (Spinner)findViewById(R.id.spTiempo);
		spTiempo.setOnItemSelectedListener(new TiempoItemSelectedListener(this));
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
		//request.addTestDevice(AdRequest.TEST_EMULATOR);
		//request.addTestDevice("2AB3408AF84416D0B0DB200867D11643");
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
		if (etTiempo.getText().length() == 0 || etTiempo.getText().toString().equals("0")){
			etTiempo.setText("");
			Toast.makeText(getApplicationContext(), "Ingrese el tiempo del crédito", Toast.LENGTH_SHORT).show();
			etTiempo.requestFocus();
			return;
		}
		quitarTeclado(boton);
		monto = Double.parseDouble(etValor.getText().toString().replaceAll("\\D", ""));
		interes = Double.parseDouble(etInteres.getText().toString().replace("%", "")) / 100;
		tiempo = Integer.parseInt(etTiempo.getText().toString());
		String tipoInteres = spInteres.getSelectedItem().toString().substring(0, 1);
		String tipoTiempo = spTiempo.getSelectedItem().toString().substring(0, 1);
		if (tipoInteres.equals("A"))
			interes = Math.pow((interes + 1), (1.0/12.0)) - 1;
		if (tipoTiempo.equals("A"))
			tiempo = tiempo * 12;
		double cuotaXinteres = monto;
		if (interes == 0){
			cuota = monto / tiempo;
		}else{
			cuota = (interes * monto) /(1- Math.pow((1 + interes), (-1.0 * tiempo)));
		}
		TextView tvCuota = (TextView)findViewById(R.id.tvValorCuota);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(0);
		tvCuota.setText(nf.format(cuota));
		lyDetalle.setVisibility(LinearLayout.VISIBLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	public void mostrarTablaAmortizacion(View view){
		anuncioInter = new InterstitialAd(this, "ca-app-pub-7618114390015000/1130576373");
		AdRequest request = new AdRequest();
		Location ubicacion = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (ubicacion != null){
			request.setLocation(ubicacion);
		}
		//request.addTestDevice(AdRequest.TEST_EMULATOR);
		//request.addTestDevice("2AB3408AF84416D0B0DB200867D11643");
		anuncioInter.setAdListener(this);
		anuncioInter.loadAd(request);
		Intent i = new Intent(this, ActTablaAmortizaion.class);
		i.putExtra("monto", monto);
		i.putExtra("interes", interes);
		i.putExtra("tiempo", tiempo);
		i.putExtra("cuota", cuota);
		startActivity(i);
	}
	
	private void quitarTeclado(View v) {
		InputMethodManager teclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		teclado.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		if (anuncioInter.isReady()){
			anuncioInter.show();
		}
	}
}
