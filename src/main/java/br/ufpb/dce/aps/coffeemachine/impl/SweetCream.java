package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class SweetCream extends Bebida {

	public SweetCream (Button button) {
		this.button = Button.BUTTON_6;
		this.setAgua(100);
		this.setCreme(25);
		this.setAcucar(15);
	}

	@Override
	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(this.agua);
		factory.getCreamerDispenser().release(this.creme);
		factory.getSugarDispenser().release(this.acucar);
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(this.copo);
	}

}
