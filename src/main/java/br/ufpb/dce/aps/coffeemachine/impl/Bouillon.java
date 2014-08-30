package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class Bouillon extends Bebida {
	
	public Bouillon (Button button) {
		this.button = Button.BUTTON_5;
		this.setAgua(100);
		this.setPoDeSopa(10);
	}

	@Override
	public void release(ComponentsFactory factory) {
		factory.getBouillonDispenser().release(this.poDeSopa);
		factory.getWaterDispenser().release(this.agua);
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(this.copo);
	}
	
	public boolean conferirIngredientes(ComponentsFactory factory, Button drink) {
		if (!factory.getCupDispenser().contains(this.copo)) {
				factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
		}
		
		if (!factory.getWaterDispenser().contains(this.agua)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		if (!factory.getBouillonDispenser().contains(poDeSopa)) {
			factory.getDisplay().warn(Messages.OUT_OF_BOUILLON_POWDER);
			return false;
		}
		return true;
	}
}
