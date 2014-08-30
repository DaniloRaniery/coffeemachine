package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeBebidas {

	private Bebida bebida;
	private int valor = 35;
	private int valorBouillon = 25;

	public void iniciarDrink(ComponentsFactory factory, Button button) {
		if (button == Button.BUTTON_1 || button == Button.BUTTON_3) {
			this.bebida = new Black(button);
		} else if (button == Button.BUTTON_2 || button == Button.BUTTON_4) {
			this.bebida = new White(button);
		} else {
			this.bebida = new Bouillon(button);
			this.valor = valorBouillon;
		}
	}

	public boolean conferirIngredientes(ComponentsFactory factory, Button drink) {
		if (this.bebida.getDrink() == drink.BUTTON_1
				|| this.bebida.getDrink() == Button.BUTTON_3) {
			return (this.conferirIngredientes(factory, drink, 1, 100, 15, 0, 0));

		} else if (this.bebida.getDrink() == drink.BUTTON_2
				|| this.bebida.getDrink() == drink.BUTTON_4) {
			return (this.conferirIngredientes(factory, drink, 1, 80, 15, 20, 0));
		} else {
			return (this.conferirIngredientes(factory, drink, 1, 100, 0, 0, 10));
		}
	}

	public boolean verificaAcucar(ComponentsFactory factory) {

		if (this.bebida.getDrink() == Button.BUTTON_3
				|| this.bebida.getDrink() == Button.BUTTON_4) {
			if (!factory.getSugarDispenser().contains(5)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void Mix(ComponentsFactory factory, Button drink) {
		factory.getDisplay().info(Messages.MIXING);
		if (this.bebida.getDrink() == drink.BUTTON_5) {
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

	public boolean conferirIngredientes(ComponentsFactory factory, Button drink,
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
		if (this.bebida.getDrink() == Button.BUTTON_2
				|| this.bebida.getDrink() == Button.BUTTON_4) {
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
