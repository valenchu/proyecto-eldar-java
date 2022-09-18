package com.eldar.creditCard;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.management.ObjectInstance;

public class MainWindows extends App {
	private List<Card> cardA = new ArrayList<>();
	private List<Card> cardN = new ArrayList<>();
	private List<Card> cardV = new ArrayList<>();
	private List<String> marcas = new ArrayList<>();
	private List<Object> cards = new ArrayList<>();
	public CardVisa vis;
	public CardNaranja naran;
	public CardAmex ame;
	private String[] menuInternoTarjeta = { "Informacion de la tarjeta", "Validar operacion", "Validar tarjeta para operar",
			"Obtener tasa de operacion","REGRESAR MENU PRINCIPAL" };

	public MainWindows() {
		this.llenarDataBaseCard();
		Scanner lectura = new Scanner(System.in);
		int numero = -1;
		Integer n = null;
		do {
			System.out.println("MENU CREADIT CARD");
			String number;
			System.out.println("Selecciones una tarjeta por su numero");
			Object[] cardMain = selectorTarjetas(lectura);
			if(Objects.nonNull(cardMain)) {
				twoOptionsMenu(lectura, cardMain);
				boolean proccess;
				do {
					System.out.println("0 - Salir");
					System.out.println("1 - Seguir proceso");
					number = lectura.nextLine();
					n = UtilsPersonalizate.isAdigit(number);
					proccess = Objects.nonNull(n) ? (n.equals(0) || n.equals(1)) ? true : false : false;
				} while (!proccess);
				numero = n;
			}else {
				System.out.println("INSERTO DATO ERRONEO RESETEANDO SISTEMA0");
			}
			
			

		} while (numero != 0);

	}

	private Object[] selectorTarjetas(Scanner lectura) {
		Object[] cardMain = null;
		for (int i = 0; this.marcas.size() > i; i++) {
			System.out.println(i + " " + this.marcas.get(i));
		}
		String number = lectura.nextLine();
		String dato = (String) UtilsPersonalizate.validateSelectedCard(number, this.marcas);
		cardMain = menuCards(lectura, dato);
		return cardMain;
	}

	private void twoOptionsMenu(Scanner lectura, Object[] cardMain) {
		String number;
		depuradorDeTarjetas(cardMain, false);
		int nu = -1;

		do {
			System.out.println("¿Que desea hacer?");
			System.out.println("OPCIONES");
			System.out.println("1 - Comparar con otra tarjeta a seleccionar?");
			System.out.println("2 - Ingresar al menu de la tarjeta seleccionada?");
			System.out.println("0 - VOLVER MENU PRINCIPAL");
			number = lectura.nextLine();
			if (Objects.nonNull(number)) {
				switch (number) {
				case "1":
					boolean exit = false;
					do {
					System.out.println("Selecciones una tarjeta por su numero a comparar con la anterior");
					if(menuCompare()) {
						exit = true;
					}else {
						System.out.println("Seleccion erronea");
						System.out.println("¿DESEA SALIR DE ESTE MENU?");
						System.out.println("SELECCINE 0");
						String data = lectura.nextLine();
						Integer result = UtilsPersonalizate.isAdigit(data);
						if(Objects.nonNull(number)) {
							exit = number.equals(0)?true:false;
							if(!exit) {
								System.out.println("NO SALDRA");
							}
						}
					}
					}while(!exit);
					break;
				case "2":
					if(Objects.nonNull(cardMain)) {
						WorckWithCard.getInstance().menuCard(cardMain,this.menuInternoTarjeta);
					}
					break;
				case "0":
					nu = 0;
					break;
				default:
					System.out.println("Opcion erronea");
					break;
				}
			}
		} while (nu != 0);
	}

	private Object[] menuCards(Scanner lectura, String dato) {
		String number;
		if (Objects.nonNull(dato)) {
			System.out.println("Seleccione 1 tarjeta dentro del grupo de " + dato + " [POR SU ID] ");

			if (dato.equals("VISA")) {
				System.out.println(Arrays.asList(cardV));
				number = lectura.nextLine();
				CardVisa visa = (CardVisa) UtilsPersonalizate.getCardsList(number, this.cardV);
				Object[] data = new Object[2];
				if(Objects.nonNull(visa)) {
				data[0] = visa;
				data[1] = 1;
				return data;
				}
			}

			if (dato.equals("AMEX")) {
				System.out.println(Arrays.asList(cardA));
				number = lectura.nextLine();
				CardAmex amex = (CardAmex) UtilsPersonalizate.getCardsList(number, this.cardA);
				Object[] data = new Object[2];
				if(Objects.nonNull(amex)) {
				data[0] = amex;
				data[1] = 2;
				return data;
				}
			}

			if (dato.equals("NARANJA")) {
				System.out.println(Arrays.asList(cardN));
				number = lectura.nextLine();
				CardNaranja naranja = (CardNaranja) UtilsPersonalizate.getCardsList(number, this.cardN);
				Object[] data = new Object[2];
				if(Objects.nonNull(naranja)) {
				data[0] = naranja;
				data[1] = 3;
				return data;
				}
			}

		}
		System.out.println("No se encontraron tarjetas seleccionadas");
		return null;
	}

	public boolean menuCompare() {
		CardVisa v;
		CardNaranja n;
		CardAmex a;

		Scanner lectura = new Scanner(System.in);
		Object[] cardMain = selectorTarjetas(lectura);
		if(Objects.nonNull(cardMain)) {
		if (Objects.nonNull(this.ame)) {
			a = this.ame;
			depuradorDeTarjetas(cardMain, true);
			System.out.println(determinarCompare(a));
			return true;
		} else if (Objects.nonNull(this.vis)) {
			v = this.vis;
			depuradorDeTarjetas(cardMain, true);
			System.out.println(determinarCompare(v));
			return true;
		} else if (Objects.nonNull(this.naran)) {
			n = this.naran;
			depuradorDeTarjetas(cardMain, true);
			System.out.println(determinarCompare(n));
			return true;
		}
		}
		return false;
	}

	public String determinarCompare(Object a) {
		String result = null;
		CardAmex amexCompare = null;
		CardVisa visaCompare = null;
		CardNaranja naranjaCompare = null;
		Object obj = null;
		if (!this.cards.isEmpty()) {
			obj = this.cards.get(0);
		}
		if (Objects.nonNull(obj)) {
			if (obj.getClass().isInstance(new CardAmex())) {
				amexCompare = (CardAmex) obj;
			} else if (obj.getClass().isInstance(new CardVisa())) {
				visaCompare = (CardVisa) obj;
			} else if (obj.getClass().isInstance(new CardNaranja())) {
				naranjaCompare = (CardNaranja) obj;
			}

			if (Objects.nonNull(a)) {
				if (a instanceof CardAmex) {
					CardAmex compare = (CardAmex) a;
					if (Objects.nonNull(amexCompare)) {
						if (compare.equals(amexCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(visaCompare)) {
						if (compare.equals(visaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(naranjaCompare)) {
						if (compare.equals(naranjaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), false);
						}
					}
				}
				if (a instanceof CardVisa) {
					CardVisa compare = (CardVisa) a;
					if (Objects.nonNull(amexCompare)) {
						if (compare.equals(amexCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(visaCompare)) {
						if (compare.equals(visaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(naranjaCompare)) {
						if (compare.equals(naranjaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), false);
						}
					}
				}
				if (a instanceof CardNaranja) {
					CardNaranja compare = (CardNaranja) a;
					if (Objects.nonNull(amexCompare)) {
						if (compare.equals(amexCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									amexCompare.getMarca(), amexCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(visaCompare)) {
						if (compare.equals(visaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									visaCompare.getMarca(), visaCompare.getNumber(), false);
						}
					} else if (Objects.nonNull(naranjaCompare)) {
						if (compare.equals(naranjaCompare)) {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), true);
						} else {
							result = UtilsPersonalizate.constructResult(compare.getMarca(), compare.getNumber(),
									naranjaCompare.getMarca(), naranjaCompare.getNumber(), false);
						}
					}
				}
			}
		}

		return result;

	}

	public void depuradorDeTarjetas(Object[] cardMain, boolean addlist) {
		this.ame = null;
		this.vis = null;
		this.naran = null;
		if (addlist) {
			this.cards.clear();
		}

		new UtilsPersonalizate(cardMain, new DepureCards() {

			@Override
			public void returnVisa(CardVisa card) {
				// TODO Auto-generated method stub
				setVisa(card, addlist);
			}

			@Override
			public void returnNaranja(CardNaranja card) {
				// TODO Auto-generated method stub
				setNara(card, addlist);
			}

			@Override
			public void returnAmex(CardAmex card) {
				// TODO Auto-generated method stub
				setAme(card, addlist);
			}
		});

	}

	public void setVisa(CardVisa card, boolean addlist) {
		this.vis = card;
		if (addlist) {
			this.cards.add(card);
		}
	}

	public void setNara(CardNaranja card, boolean addlist) {
		this.naran = card;
		if (addlist) {
			this.cards.add(card);
		}
	}

	public void setAme(CardAmex card, boolean addlist) {
		this.ame = card;
		if (addlist) {
			this.cards.add(card);
		}
	}

	public void llenarDataBaseCard() {
		this.cardV.add(new CardVisa(0L, "VISA", "4917484589897107", "Pedro", "Escamoso", "01", "2021"));
		this.cardV.add(new CardVisa(1L, "VISA", "4001919257537193", "Alex", "Hugardo", "03", "2023"));
		this.cardN.add(new CardNaranja(2L, "NARANJA", "4917484589897107", "Valerico", "Casanova", "05", "2024"));
		this.cardN.add(new CardNaranja(3L, "NARANJA", "4917484589897107", "Tincho", "Jofredo", "09", "2025"));
		this.cardA.add(new CardAmex(4L, "AMEX", "374245455400126", "Lituan", "Marlon", "10", "2026"));
		this.cardA.add(new CardAmex(5L, "AMEX", "378282246310005", "Trulio", "Escanoso", "12", "2025"));
		this.marcas.add("VISA");
		this.marcas.add("NARANJA");
		this.marcas.add("AMEX");
	}
}
