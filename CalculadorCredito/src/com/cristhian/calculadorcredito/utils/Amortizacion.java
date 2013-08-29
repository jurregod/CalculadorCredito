package com.cristhian.calculadorcredito.utils;

import java.text.NumberFormat;

import com.cristhian.calculadorcredito.R.string;

/***
 * 
 * @author CSALAZAR
 * Clase para representar cada item de la amortizacion
 */
public class Amortizacion {

	private int numero;
	private double valorCuota;
	private double abonoCapital;
	private double abonoInteres;
	private double saldoCredito;
	
	public Amortizacion(int numero, double valorCuota, double abonoCapital,
			double abonoInteres, double saldoCredito) {
		super();
		this.numero = numero;
		this.valorCuota = valorCuota;
		this.abonoCapital = abonoCapital;
		this.abonoInteres = abonoInteres;
		this.saldoCredito = saldoCredito;
	}

	public Amortizacion() {
		super();
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(double valorCuota) {
		this.valorCuota = valorCuota;
	}

	public double getAbonoCapital() {
		return abonoCapital;
	}

	public void setAbonoCapital(double abonoCapital) {
		this.abonoCapital = abonoCapital;
	}

	public double getAbonoInteres() {
		return abonoInteres;
	}

	public void setAbonoInteres(double abonoInteres) {
		this.abonoInteres = abonoInteres;
	}

	public double getSaldoCredito() {
		return saldoCredito;
	}

	public void setSaldoCredito(double saldoCredito) {
		this.saldoCredito = saldoCredito;
	}
	
	public static String currentFormat(double value){
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(value);
	}
	
	public String toString(){
		return numero + " \t " + currentFormat(valorCuota) + " \t " + currentFormat(abonoCapital) + " \t " + currentFormat(abonoInteres) + " \t " + currentFormat(saldoCredito);
	}
}
