package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeBebidas {

	private Bebida bebida;
	private int valor = 35;
	private int valorBouillon = 25;

	public void iniciarDrink(ComponentsFactory factory, Drink drink) {
		if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.bebida = new Black(drink);
		} else if (drink == drink.WHITE || drink == drink.WHITE_SUGAR) {
			this.bebida = new White(drink);
		} else {
			this.bebida = new Bouillon(drink);
			this.valor = valorBouillon;
		}
	}

	public boolean conferirIngredientes(ComponentsFactory factory, Drink drink) {
		if (this.bebida.getDrink() == drink.BLACK
				|| this.bebida.getDrink() == Drink.BLACK_SUGAR) {
			return (this.conferirIngredientes(factory, drink, 1, 100, 15, 0, 0));

		} else if (this.bebida.getDrink() == drink.WHITE
				|| this.bebida.getDrink() == drink.WHITE_SUGAR) {
			return (this.conferirIngredientes(factory, drink, 1, 80, 15, 20, 0));
		} else {
			return (this.conferirIngredientes(factory, drink, 1, 100, 0, 0, 10));
		}
	}

	public boolean verificaAcucar(ComponentsFactory factory) {

		if (this.bebida.getDrink() == Drink.BLACK_SUGAR
				|| this.bebida.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getSugarDispenser().contains(5)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void Mix(ComponentsFactory factory, Drink drink) {
		factory.getDisplay().info(Messages.MIXING);
		if (this.bebida.getDrink() == drink.BOUILLON) {
			factory.getBouillonDispenser().release(10);
		} 
		else {
			factory.getCoffeePowderDispenser().release(15);
		}

	}

	public void release(ComponentsFactory factory) {
		this.bebida.release(factory);
		factory.getDrinkDispenser().release(100.0);
		factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	public int getValorDaBebida() {
		return this.valor;
	}

	public boolean conferirIngredientes(ComponentsFactory factory, Drink drink,
			int copo, int agua, int po, int creme, int poDeSopa) {
		if (copo > 0) {
			if (!factory.getCupDispenser().contains(copo)) {
				factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
			}
		}
		if (!factory.getWaterDispenser().contains(agua)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		if (po > 0) {
			if (!factory.getCoffeePowderDispenser().contains(po)) {
				factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				return false;
			}
		}
		if (this.bebida.getDrink() == Drink.WHITE
				|| this.bebida.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getCreamerDispenser().contains(creme)) {
				factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
		if (poDeSopa > 0) {
			if (!factory.getBouillonDispenser().contains(poDeSopa)) {
				factory.getDisplay().warn(Messages.OUT_OF_BOUILLON_POWDER);
				return false;
			}
		}
		return true;
	}
}
