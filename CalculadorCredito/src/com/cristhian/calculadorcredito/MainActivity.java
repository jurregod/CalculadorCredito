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
import android.annotation.SuppressLint;
import android.app.Activity;
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

public class MainActivity extends Activity implements OnKeyListener {
	
	private EditText etValor;
	private EditText etInteres;
	private EditText etTiempo;
	private Spinner spInteres;
	private Spinner spTiempo;
	private AdView anuncio;
	private LocationManager locManager;
	private LinearLayout lyDetalle;
	private int monto;
	private int tiempo;
	private double interes;
	private double cuota;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spInteres = (Spinner)findViewById(R.id.spTipoInteres);
		spTiempo = (Spinner)findViewById(R.id.spTiempo);
		lyDetalle = (LinearLayout)findViewById(R.id.lyDetalleCuota);
		lyDetalle.setVisibility(LinearLayout.INVISIBLE);
		etValor = (EditText)findViewById(R.id.etMonto);
		etValor.setOnFocusChangeListener(new MoneyFormatFocusChange());
		etInteres = (EditText)findViewById(R.id.etInteres);
		etInteres.setOnFocusChangeListener(new PercentFormatFocusChange());
		etTiempo = (EditText)findViewById(R.id.etTiempo);
		etTiempo.setOnKeyListener(this);
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
		monto = Integer.parseInt(etValor.getText().toString().replaceAll("\\D", ""));
		interes = Double.parseDouble(etInteres.getText().toString().replace("%", "")) / 100;
		tiempo = Integer.parseInt(etTiempo.getText().toString());
		String tipoInteres = spInteres.getSelectedItem().toString().substring(0, 1);
		String tipoTiempo = spTiempo.getSelectedItem().toString().substring(0, 1);
		if (tipoInteres.equals("A"))
			interes = Math.pow((interes + 1), (1.0/12.0)) - 1;
		if (tipoTiempo.equals("A"))
			tiempo = tiempo * 12;
		cuota = (interes * monto)/(1- Math.pow((1 + interes), (-1.0 * tiempo)));
		TextView tvCuota = (TextView)findViewById(R.id.tvValorCuota);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(0);
		tvCuota.setText(nf.format(cuota));
		lyDetalle.setVisibility(LinearLayout.VISIBLE);
	}
	
	public void mostrarTablaAmortizacion(View view){
		Intent i = new Intent(this, ActTablaAmortizaion.class);
		i.putExtra("monto", monto);
		i.putExtra("interes", interes);
		i.putExtra("tiempo", tiempo);
		i.putExtra("cuota", cuota);
		startActivity(i);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		String text = this.etTiempo.getText().toString();
		String tmpTiempo = spTiempo.getSelectedItem().toString().substring(0, 1);
		if (tmpTiempo.equals("A") && text.length() > 0 && Integer.parseInt(text) > 20){
			this.etTiempo.setText("");
			Toast.makeText(this, this.getString(R.string.validacionAno), Toast.LENGTH_SHORT).show();
		}else if(tmpTiempo.equals("M") && text.length() > 0 && Integer.parseInt(text) > 240){
			this.etTiempo.setText("");
			Toast.makeText(this, this.getString(R.string.validacionMes), Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	
}
