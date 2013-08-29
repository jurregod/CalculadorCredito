package com.cristhian.calculadorcredito;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ElementoLista extends LinearLayout {
	
	private TextView tvNumero;
	private TextView tvAbonoCapital;
	private TextView tvAbonoInteres;
	private TextView tvSaldo;
	
	
	
	public ElementoLista(Context context, AttributeSet attrs) {
		super(context, attrs);
		inicializar();
		TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.ElementoLista);
		tvNumero.setText(a.getString(R.styleable.ElementoLista_textNumero));
		tvAbonoCapital.setText(a.getString(R.styleable.ElementoLista_textAbonoCapital));
		tvAbonoInteres.setText(a.getString(R.styleable.ElementoLista_textAbonoInteres));
		tvSaldo.setText(a.getString(R.styleable.ElementoLista_textSaldo));
		setTextNegrilla(a.getBoolean(R.styleable.ElementoLista_textNegrilla, false));
	}

	public ElementoLista(Context context) {
		super(context);
		inicializar();
		// TODO Auto-generated constructor stub
	}

	private void inicializar()
	{
		//Utilizamos el layout 'control_login' como interfaz del control
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li =
		(LayoutInflater)getContext().getSystemService(infService);
		li.inflate(R.layout.elemento_lista, this, true);
		tvNumero = (TextView)findViewById(R.id.tvNumero);
		tvAbonoCapital = (TextView)findViewById(R.id.tvAbonoCapital);
		tvAbonoInteres = (TextView)findViewById(R.id.tvAbonoInteres);
		tvSaldo = (TextView)findViewById(R.id.tvSaldo);
	}

	public void setTextNumero(String value){
		tvNumero.setText(value);
	}
	
	public void setTextAbonoCapital(String value){
		tvAbonoCapital.setText(value);
	}
	
	public void setTextAbonoInteres(String value){
		tvAbonoInteres.setText(value);
	}
	
	public void setTextSaldo(String value){
		tvSaldo.setText(value);
	}
	
	public void setTextNegrilla(boolean value){
		if (value){
			tvNumero.setTypeface(null, Typeface.BOLD);
			tvAbonoCapital.setTypeface(null, Typeface.BOLD);
			tvAbonoInteres.setTypeface(null, Typeface.BOLD);
			tvSaldo.setTypeface(null, Typeface.BOLD);
		}
	}
}
