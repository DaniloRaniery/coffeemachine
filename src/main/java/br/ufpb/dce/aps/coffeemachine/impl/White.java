package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class White extends Bebida {
	
	public White(Button button) {
		if (button == Button.BUTTON_2) {
			this.button = Button.BUTTON_2;
		} else {
			this.button = Button.BUTTON_4;
		}
		this.setAgua(80);
		this.setCreme(20);
		this.setPoDeCafe(15);
		this.setAcucar(5);
	}

	public void release(ComponentsFactory factory) {
		factory.getCoffeePowderDispenser().release(this.poDeCafe);
		factory.getWaterDispenser().release(this.agua);
		factory.getCreamerDispenser().release(this.creme);
		if (this.button == Button.BUTTON_4) {
			factory.getSugarDispenser().release(this.acucar);
		}
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(this.copo);
	}
}
