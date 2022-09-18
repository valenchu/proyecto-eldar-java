package com.eldar.creditCard;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class CardVisa extends Card {
	Double monto ;
	public CardVisa(Long id, String marca, String number, String name, String lastName, String month, String year) {
		super(id, marca, number, name, lastName, month, year);
		this.monto = 20000D;
	}

	public CardVisa() {
		super();

	}
	

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	@Override
	public Double obtainMonto() {
		// TODO Auto-generated method stub
		return monto;
	}
	

	@Override
	public Double tasaCalculated(Integer resultYear, Integer resultMonth, Integer day) {
		resultYear = resultYear % 100;	
		BigDecimal year = new BigDecimal(resultYear);
		BigDecimal mon = new BigDecimal(resultMonth);
		BigDecimal res = year.divide(mon).setScale(9);
		return Double.parseDouble(res.toString());
	}

	
	
	

}
