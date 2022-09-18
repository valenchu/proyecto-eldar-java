package com.eldar.creditCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class WorckWithCard {

	public CardAmex amex;
	public CardVisa visa;
	public CardNaranja naranja;
	public Double montoPermitido = 1000D;

	private static class WorckWithCardHold {
		public static WorckWithCard instance = new WorckWithCard();
	}

	private WorckWithCard() {
	}

	public static WorckWithCard getInstance() {
		return WorckWithCardHold.instance;
	}

	public Card menuCard(Object[] cardMain, String[] data) {
		Scanner leer = new Scanner(System.in);
		depuroCard(cardMain);
		int numero = -1;
		do {
			System.out.println("MENU INTERNO TARJETA SELECCIONADA");
			System.out.println("QUE DESEA HACER");
			System.out.println("SELECCIONE UNA OPCION");
			for (int i = 0; data.length > i; i++) {
				System.out.println(i + " " + data[i]);
			}
			String seleccion = leer.nextLine();
			Integer selectNum = UtilsPersonalizate.isAdigit(seleccion);
			if (Objects.nonNull(selectNum)) {
				switch (selectNum) {
				case 0:
					infoCard();
					break;
				case 1:
					operacionValida();
					break;
				case 2:
					validarTarjetaOperar();
					break;
				case 3:
					tasaServicioCalculated();
					break;
				case 4:
					numero = 0;
					break;

				default:
					System.out.println("SELECCION ERRONEA");
					break;
				}

			}

		} while (numero != 0);

		return null;
	}

	public void infoCard() {
		if (Objects.nonNull(this.amex)) {
			System.out.println(Arrays.asList(amex));
		} else if (Objects.nonNull(this.visa)) {
			System.out.println(Arrays.asList(visa));
		} else if (Objects.nonNull(this.naranja)) {
			System.out.println(Arrays.asList(this.amex));
		}
	}

	public void operacionValida() {
		System.out.println("Ingrese Monto de la operacion");
		Scanner lectura = new Scanner(System.in);
		Double mont = UtilsPersonalizate.isDouble(lectura.nextLine());
		if (Objects.nonNull(mont)) {
			if (Objects.nonNull(this.amex)) {
				String result = this.amex.operacionInvalida(mont, this.montoPermitido)
						? "OPERACION DE " + mont + " VALIDA"
						: "OPERACION " + mont + " INVALIDA";
				System.out.println(result);
			} else if (Objects.nonNull(this.visa)) {
				String result = this.visa.operacionInvalida(mont, this.montoPermitido)
						? "OPERACION DE " + mont + " VALIDA"
						: "OPERACION " + mont + " INVALIDA";
				System.out.println(result);
			} else if (Objects.nonNull(this.naranja)) {
				String result = this.naranja.operacionInvalida(mont, this.montoPermitido)
						? "OPERACION DE " + mont + " VALIDA"
						: "OPERACION " + mont + " INVALIDA";
				System.out.println(result);
			}
		} else {
			System.out.println("No ingreso un digito correcto");
		}
	};

	public void validarTarjetaOperar() {
		if (Objects.nonNull(this.amex)) {
			String result = this.amex.isValidToOperated() ? "TARJETA VALIDA PARA OPERAR"
					: "TARJETA INVALIDA PARA OPERAR";
			System.out.println(result);
		} else if (Objects.nonNull(this.visa)) {
			String result = this.visa.isValidToOperated() ? "TARJETA VALIDA PARA OPERAR"
					: "TARJETA INVALIDA PARA OPERAR";
			System.out.println(result);
		} else if (Objects.nonNull(this.naranja)) {
			String result = this.naranja.isValidToOperated() ? "TARJETA VALIDA PARA OPERAR"
					: "TARJETA INVALIDA PARA OPERAR";
			System.out.println(result);
		}
	}

	public void tasaServicioCalculated() {
		System.out.println("Ingrese monto de la operacion para calcular tasa");
		Scanner lectura = new Scanner(System.in);
		Double mo = UtilsPersonalizate.isDouble(lectura.nextLine());
		if(Objects.nonNull(mo)) {
		boolean montValidateResult = expertValidateMont(mo);
		if(montValidateResult) {
		List<Integer> rsult = validateInsercion();
		Double tasa = null;
		if (Objects.nonNull(rsult) && !rsult.isEmpty()) {
			double sumTa;
			if (Objects.nonNull(this.amex)) {
				if (this.amex.isValidToOperated()) {

					tasa = this.amex.tasaCalculated(rsult.get(0), rsult.get(1), rsult.get(2));
					sumTa = (tasa/100)*mo;
					sumTa = sumTa+mo;
					System.out.println("El importe total de la tarjeta " + this.amex.getMarca() + " con tasa de "+ tasa+" ES = " + sumTa+mo);

				} else {
					System.out.println("Tarjeta vencida, no puede realizar esta operacion");
				}
			} else if (Objects.nonNull(this.visa)) {
				if (this.visa.isValidToOperated()) {

					tasa = this.visa.tasaCalculated(rsult.get(0), rsult.get(1), rsult.get(2));
					sumTa = (tasa/100)*mo;
					sumTa = sumTa+mo;
					System.out.println("El importe total de la tarjeta " + this.visa.getMarca() + " con tasa de "+ tasa+" ES = " + sumTa);

				} else {
					System.out.println("Tarjeta vencida, no puede realizar esta operacion");
				}
			} else if (Objects.nonNull(this.naranja)) {
				if (this.naranja.isValidToOperated()) {

					tasa = this.naranja.tasaCalculated(rsult.get(0), rsult.get(1), rsult.get(2));
					sumTa = (tasa/100)*mo;
					sumTa = sumTa+mo;
					System.out.println("El importe total de la tarjeta " + this.naranja.getMarca() + " con tasa de "+ tasa+" ES = " +  sumTa+mo);

				} else {
					System.out.println("Tarjeta vencida, no puede realizar esta operacion");
				}
			}
		} else {
			System.out.println("No ingreso datos de fecha correcto, no se pudo calcular tasa, volviendo al menu de tarjeta");
		}
		}else {
			System.out.println("El monto de "+ mo +" supera al monto permitido "+this.montoPermitido);
		}
		}else {
			System.out.println("Insercion erronea de datos!");
		}
	}

	public List<Integer> validateInsercion() {
		boolean isValidDate = false;
		Integer ano = null,mes=null,day = null;
		List<Integer> objetI = new ArrayList<Integer>();
		boolean salir = false;
		do {
		System.out.println("INGRESE LOS SIGUIENTES DATOS PARA CALCULAR TASA DE TARJETA");
		Scanner lectura = new Scanner(System.in);
		System.out.println("Ingrese año");
		 ano = UtilsPersonalizate.isAdigit(lectura.nextLine());
		System.out.println("Ingrese mes del año");
		 mes = UtilsPersonalizate.isAdigit(lectura.nextLine());
		System.out.println("Ingrese día del mes");
		 day = UtilsPersonalizate.isAdigit(lectura.nextLine());
		if(Objects.nonNull(ano) && Objects.nonNull(mes) && Objects.nonNull(day)) {
		isValidDate = UtilsPersonalizate.verifyDate(ano, mes, day);
		salir = true;
		}else {
			System.out.println("Ingreso alguno de los 3 valores de fecha mal");
			System.out.println("Si desea terminar operacion marque 0, si no cualquier otro numero para continuar");
			Integer data = UtilsPersonalizate.isAdigit(lectura.nextLine());
			if(Objects.nonNull(data)&& data.equals(0)) {
				salir = true;
			}
		}
		}while(!salir);
		if (isValidDate) {
			objetI.add(ano);
			objetI.add(mes);
			objetI.add(day);
			return objetI;
		} else {
			return objetI;
		}
	}

	public void depuroCard(Object[] cardMain) {
		new UtilsPersonalizate(cardMain, new DepureCards() {

			@Override
			public void returnVisa(CardVisa card) {
				// TODO Auto-generated method stub
				setVisa(card);
			}

			@Override
			public void returnNaranja(CardNaranja card) {
				// TODO Auto-generated method stub
				setNaranja(card);
			}

			@Override
			public void returnAmex(CardAmex card) {
				// TODO Auto-generated method stub
				setAmex(card);
			}
		});
	}
	
	public boolean expertValidateMont(Double mont) {
		boolean r = false;
		if (Objects.nonNull(this.amex)) {
			r = this.amex.operacionInvalida(mont, this.montoPermitido);
		} else if (Objects.nonNull(this.visa)) {
			r = this.visa.operacionInvalida(mont, this.montoPermitido);
		} else if (Objects.nonNull(this.naranja)) {
			r = this.naranja.operacionInvalida(mont, this.montoPermitido);
		}
		return r;
	}

	public void setAmex(CardAmex amex) {
		this.amex = amex;
	}

	public void setVisa(CardVisa visa) {
		this.visa = visa;
	}

	public void setNaranja(CardNaranja naranja) {
		this.naranja = naranja;
	};

}
