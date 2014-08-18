package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class Black extends Bebida {

	public Black (Drink drink) {
		if (drink == drink.BLACK) {
			this.drink = drink.BLACK;
		} else {
			this.drink = drink.BLACK_SUGAR;
		}
	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
		if (drink == drink.BLACK_SUGAR) {
			factory.getSugarDispenser().release(5);
		}
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
}
