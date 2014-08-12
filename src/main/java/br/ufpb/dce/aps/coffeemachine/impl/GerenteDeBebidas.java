package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeBebidas {

	private Bebida bebida;
	private double valor = 35;

	public void iniciarDrink(ComponentsFactory factory, Drink drink) {
		if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.bebida = new Black(drink, factory);
		} else {
			this.bebida = new White(drink, factory);
		}
	}

	public boolean conferirIngredientes(ComponentsFactory factory, Drink drink) {
		if (!factory.getCupDispenser().contains(1)) {
			factory.getDisplay().warn(Messages.OUT_OF_CUP);
			return false;
		} 
		if(drink == drink.BLACK || drink == drink.BLACK_SUGAR){
			if (!factory.getWaterDispenser().contains(100)) {
				factory.getDisplay().warn(Messages.OUT_OF_WATER);
				return false;
			}
		}
		else{
			if(!factory.getWaterDispenser().contains(80)) {
				factory.getDisplay().warn(Messages.OUT_OF_WATER);
				return false;
			}
		}
		if (!factory.getCoffeePowderDispenser().contains(15)) {
			factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			return false;
		} else if (this.bebida.getDrink() == Drink.WHITE
				|| this.bebida.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getCreamerDispenser().contains(20)) {
				factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
		return true;
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

	public void Mix(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.MIXING);
		factory.getCoffeePowderDispenser().release(15);
	}

	public void release(ComponentsFactory factory) {
		this.bebida.release();
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(100.0);
		factory.getDisplay().info(Messages.TAKE_DRINK);

	}

	public double getValorDaBebida() {
		return this.valor;
	}
}
