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
	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(80);
		factory.getCreamerDispenser().release(20);
		if (this.button == Button.BUTTON_4) {
			factory.getSugarDispenser().release(5);
		}
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
	
	
}
