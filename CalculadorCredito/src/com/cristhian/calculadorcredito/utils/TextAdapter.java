package com.cristhian.calculadorcredito.utils;

import java.util.List;

import com.cristhian.calculadorcredito.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
/****
 * 
 * @author CSALAZAR
 * Adapter para mostrar la informacion contenida en un array de String
 */
public class TextAdapter extends BaseAdapter {

	private Activity context;
	private List<Amortizacion> valores;
	
	public TextAdapter(Activity context, List<Amortizacion> valores){
		this.context = context;
		this.valores = valores;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return valores.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return valores.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return valores.get(index).getNumero();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista, null,true);
        Amortizacion amortizacion = valores.get(position);
        TextView tvNumero =(TextView)view.findViewById(R.id.tvNumero);
        TextView tvAbonoCapital =(TextView)view.findViewById(R.id.tvAbonoCapital);
        TextView tvAbonoInteres =(TextView)view.findViewById(R.id.tvAbonoInteres);
        TextView tvSaldo =(TextView)view.findViewById(R.id.tvSaldo);
        if(position % 2 == 0){
        	view.setBackgroundColor(context.getResources().getColor(R.color.registro1));
        }
        else{
        	view.setBackgroundColor(context.getResources().getColor(R.color.registro2));
        }
        if (position == 0){
        	tvSaldo.setText(Amortizacion.currentFormat(amortizacion.getSaldoCredito()));
        }else if(position == valores.size() - 1){
        	tvNumero.setText("Total:");
        	tvNumero.setTextColor(Color.BLACK);
        	tvNumero.setTypeface(null, Typeface.BOLD);
	        tvAbonoCapital.setText(Amortizacion.currentFormat(amortizacion.getAbonoCapital()));
	        tvAbonoCapital.setTextColor(Color.BLACK);
	        tvAbonoCapital.setTypeface(null, Typeface.BOLD);
	        tvAbonoInteres.setText(Amortizacion.currentFormat(amortizacion.getAbonoInteres()));
	        tvAbonoInteres.setTextColor(Color.BLACK);
	        tvAbonoInteres.setTypeface(null, Typeface.BOLD);
	        tvSaldo.setText("");
        }else{
	        tvNumero.setText(String.valueOf(amortizacion.getNumero()));
	        tvAbonoCapital.setText(Amortizacion.currentFormat(amortizacion.getAbonoCapital()));
	        tvAbonoInteres.setText(Amortizacion.currentFormat(amortizacion.getAbonoInteres()));
	        tvSaldo.setText(Amortizacion.currentFormat(amortizacion.getSaldoCredito()));
        }
        return view;
	}

}
