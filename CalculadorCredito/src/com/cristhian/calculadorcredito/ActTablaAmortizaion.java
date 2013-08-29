package com.cristhian.calculadorcredito;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import com.cristhian.calculadorcredito.utils.Amortizacion;
import com.cristhian.calculadorcredito.utils.TextAdapter;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;

import android.R.integer;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class ActTablaAmortizaion extends ListActivity {
	
	
	private LocationManager locManager;
	private double monto;
	private int tiempo;
	private double interes;
	private double cuota;
	public List<Amortizacion> tablaAmortizacion = new ArrayList<Amortizacion>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_tabla_amortizaion);
		Bundle extras = getIntent().getExtras();
		monto = extras.getDouble("monto");
		tiempo = extras.getInt("tiempo");
		interes = extras.getDouble("interes");
		cuota = extras.getDouble("cuota");
		tablaAmortizacion.add(new Amortizacion(0, 0, 0, 0, monto)); //El primer registro en el cual esta el monto total | mes 0
		setListAdapter(new TextAdapter(this, this.tablaAmortizacion));
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		((TextView)findViewById(R.id.tvCabeceraCuota)).setText(getResources().getString(R.string.CabeceraCuota) + ": " + Amortizacion.currentFormat(this.cuota));
		((TextView)findViewById(R.id.tvCabeceraInteres)).setText(getResources().getString(R.string.CabeceraInteres) + ": " + df.format(this.interes * 100) + "%");
		((TextView)findViewById(R.id.tvCabeceraMonto)).setText(getResources().getString(R.string.CabeceraMonto) + ": " + Amortizacion.currentFormat(this.monto));
		((TextView)findViewById(R.id.tvCabeceraNoCuotas)).setText(getResources().getString(R.string.CabeceraNoCuotas) + ": " + String.valueOf(this.tiempo));
		//Ejecuto la tarea que realizara la tabla de amortizacion
		new TaskAmortizacion(this).execute(extras);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_tabla_amortizaion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/****
	 * 
	 * @author CSALAZAR
	 * Tarea asincrona la cual se encarga de crear la tabla de amortizacion
	 */
	private class TaskAmortizacion extends AsyncTask<Bundle, Amortizacion, Integer>{
		
		private ListActivity activity;
		
		public TaskAmortizacion(ListActivity activity){
			this.activity = activity;
		}
		
		@Override
		protected Integer doInBackground(Bundle... params) {
			// TODO Auto-generated method stub
			Bundle extras = params[0];
			double tmpTiempo = extras.getInt("tiempo");
			double tmpInteres = extras.getDouble("interes");
			double tmpCuota = extras.getDouble("cuota");
			double deudaRestante = monto;
			double totalInteres = 0;
			for(int indice = 1; indice <= tmpTiempo;indice ++){
				Amortizacion amortizacion = new Amortizacion();
				if (tmpInteres == 0)
					amortizacion.setAbonoInteres(0);
				else
					amortizacion.setAbonoInteres(deudaRestante * tmpInteres);
				amortizacion.setAbonoCapital(tmpCuota - amortizacion.getAbonoInteres());
				totalInteres += amortizacion.getAbonoInteres();
				deudaRestante -= amortizacion.getAbonoCapital();
				amortizacion.setSaldoCredito(Math.abs(deudaRestante));
				amortizacion.setValorCuota(tmpCuota);
				amortizacion.setNumero(indice);
				publishProgress(amortizacion);
			}
			publishProgress(new Amortizacion(0, 0, monto, totalInteres, 0)); //ingreso el registro totalizado
			return 1;
		}

		@Override
		protected void onProgressUpdate(Amortizacion... values) {
			tablaAmortizacion.add(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			activity.setListAdapter(new TextAdapter(activity, tablaAmortizacion));
		}
		
		
	}
	
	
}