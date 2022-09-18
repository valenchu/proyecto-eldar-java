package com.eldar.creditCard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public abstract class Card {

	protected Long id;
	protected String marca;
	protected String number;
	protected String name;
	protected String lastName;
	protected String month;
	protected String year;

	public Card(Long id, String marca, String number, String name, String lastName, String month, String year) {
		super();
		this.id = id;
		this.marca = marca;
		this.number = number;
		this.name = name;
		this.lastName = lastName;
		this.month = month;
		this.year = year;
	}


	public Card() {
		// TODO Auto-generated constructor stub
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public abstract Double tasaCalculated(Integer resultYear, Integer resultMonth, Integer day);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract Double obtainMonto();

	@Override
	public String toString() {
		return "\n CredidtCard ID = "+id+"\n Marca Tarjeta = " + marca + ", Numero de tarjeta = " + number + ", Nombre = " + name
				+ ", Apellido = " + lastName + ", Fecha de expiracion = " + month + "/" + year + " Dineros = "+ obtainMonto() + "\n";
	}


	@Override
	public int hashCode() {
		return Objects.hash(lastName, marca, month, name, number, year);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(lastName, other.lastName) && Objects.equals(marca, other.marca)
				&& Objects.equals(month, other.month) && Objects.equals(name, other.name)
				&& Objects.equals(number, other.number) && Objects.equals(year, other.year);
	}
	
	public boolean operacionInvalida(Double mont, Double montoPermitido) {
		boolean isValid = false;
		if(isValidToOperated()) {
		if(Objects.nonNull(mont)) {
			if(mont < montoPermitido) {
				isValid = true;
			}else {
				isValid = false;
			}
		}else {
			System.out.println("No ingreso un digito correcto");
		}
		}else {
			System.out.println("Tarjeta vencida, no puede realizar esta operacion");
		}
		return isValid;
	}
	
	public boolean isValidToOperated() {
		boolean isValid = false;
		LocalDate local = LocalDate.now();
		int mont = local.getMonthValue();
		int year = local.getYear();
		int montClase = Integer.parseInt(this.month);
		int yearClase = Integer.parseInt(this.year);
		if(year < yearClase) {
			isValid = true;
		}else if(year == yearClase) {
			if(mont < montClase) {
				isValid = true;
			}else if(mont == montClase) {
				isValid = true;
			}
		}
		return isValid;
	}

}
