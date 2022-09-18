package com.eldar.creditCard;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class UtilsPersonalizate {

	DepureCards callBackCards;

	public UtilsPersonalizate(Object[] cardMain, DepureCards callback) {
		this.callBackCards = callback;
		returnCorrectCreditCard(cardMain);
		// TODO Auto-generated constructor stub
	}

	public UtilsPersonalizate() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * 1 = VISA 2 = AMEX 3 = NARANJA
	 */
	public void returnCorrectCreditCard(Object[] cardMain) {
		int value = Integer.parseInt(cardMain[1].toString());
		switch (value) {
		case 1:
			callBackCards.returnVisa((CardVisa) cardMain[0]);
			break;
		case 2:
			callBackCards.returnAmex((CardAmex) cardMain[0]);
			break;
		case 3:
			callBackCards.returnNaranja((CardNaranja) cardMain[0]);
			break;
		}

	}

	public static String constructResult(String marcaA, String numberA, String marcaB, String numberB,
			boolean isIgual) {
		StringBuilder resultString = null;
		if (isIgual) {
			resultString = new StringBuilder("Tarjeta ").append(marcaA).append(" - ").append(numberB)
					.append(" ES IGUAL A ").append(marcaB).append(" - ").append(numberB);
		} else {
			resultString = new StringBuilder("Tarjeta ").append(marcaA).append(" - ").append(numberB)
					.append(" NO ES IGUAL A ").append(marcaB).append(" - ").append(numberB);

		}
		return resultString.toString();
	}

	public static Integer isAdigit(String data) {
		Integer result = null;
		Pattern p = Pattern.compile("\\d");
		boolean paternResult = Objects.nonNull(data) && p.matcher(data).find();
		if ((paternResult)) {
			result = Integer.parseInt(data);
		}
		return result;
	}
	
	
	public static Double isDouble(String str) {
		Double result = null;
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Boolean verifyDate(Integer ano, Integer mes, Integer day) {
		
		try {
			LocalDate rsu =  LocalDate.of(ano, mes, day);
			return true;
		} catch (DateTimeException e) {
			return false;
		}
		
	}

	public static Object validateSelectedCard(String number, List<?> lista) {
		if (Objects.nonNull(lista) && Objects.nonNull(number)) {
			Integer num = isAdigit(number);
			if (Objects.nonNull(num)) {
				Integer re = num > lista.size() - 1 ? null : num;
				return Objects.nonNull(re) ? lista.get(re) : null;
			}
		}
		return null;
	}

	public static Card getCardsList(String number, List<Card> lista) {
		if (Objects.nonNull(lista) && Objects.nonNull(number)) {
			Integer num = isAdigit(number);

			if (Objects.nonNull(num)) {
				long n = num.longValue();
				for (Card c : lista) {
					if (c.getId().equals(n)) {
						return c;
					}
				}
			}
		}
		return null;

	}

}
