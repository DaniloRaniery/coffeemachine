package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class Black extends Bebida {
	
	public Black(Button button) {
		if (button == Button.BUTTON_1) {
			this.button = Button.BUTTON_1;
		} else {
			this.button = Button.BUTTON_3;
		}
		this.setPoDeCafe(15);
		this.setAgua(100);
		this.setAcucar(5);

	}

	public void release(ComponentsFactory factory) {
		factory.getCoffeePowderDispenser().release(this.poDeCafe);
		factory.getWaterDispenser().release(this.agua);
		if (button == Button.BUTTON_3) {
			factory.getSugarDispenser().release(this.acucar);
		}
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(this.copo);
	}
}
