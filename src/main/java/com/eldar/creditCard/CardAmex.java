package com.eldar.creditCard;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CardAmex extends Card {
	Double monto ;
	public CardAmex(Long id, String marca, String number, String name, String lastName, String month, String year) {
		super(id, marca, number, name, lastName, month, year);
		this.monto = 30000D;
	}

	public CardAmex() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public Double obtainMonto() {
		// TODO Auto-generated method stub
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Override
	public Double tasaCalculated(Integer resultYear, Integer resultMonth, Integer day) {
		BigDecimal mon = new BigDecimal(resultMonth);
		BigDecimal por = new BigDecimal(0.1);
		BigDecimal res = mon.multiply(por).setScale(9);
		return Double.parseDouble(res.toString());
	}

}
