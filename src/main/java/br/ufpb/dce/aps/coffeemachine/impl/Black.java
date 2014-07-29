package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class Black extends Drinks {

	public Black (Drink drink) {
		if (drink == drink.BLACK) {
			this.drink = drink.BLACK;
		} else {
			this.drink = drink.BLACK_SUGAR;
		}
	}

	public void verificaAcucar() {
		if (!this.factory.getSugarDispenser().contains(200)) {
			this.factory.getDisplay().warn("Out of Sugar");
			this.drink = null;
		} else {
			this.drink = drink.BLACK_SUGAR;
		}
	}

	@Override
	public void release() {
		if (drink == drink.BLACK_SUGAR) {
			this.factory.getSugarDispenser().release(200);
		}
		this.releaseFinal();

	}

}
