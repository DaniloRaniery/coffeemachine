package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Drink;

public class White extends Drinks {

	public White(Drink drink) {
		if (drink == drink.WHITE) {
			this.drink = drink.WHITE;
		} else {
			this.drink = drink.WHITE_SUGAR;
		}
	}

	public void verificaAcucar() {
		if (!this.factory.getCreamerDispenser().contains(150)){
			this.drink = null;
		}
		else {
			if (!this.factory.getSugarDispenser().contains(200)) {
				this.factory.getDisplay().warn("Out of Sugar");
				this.drink = null;
			}
			else{
				this.drink = drink.WHITE_SUGAR;
			}
		}
	}

	public void release() {
		if (drink == drink.WHITE_SUGAR) {
			this.factory.getSugarDispenser().release(200);
		}
		this.releaseFinal();
	}
}
