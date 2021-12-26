package AppCore;

import jade.core.behaviours.Behaviour;

public abstract class SearchBehaviour extends Behaviour{

	private static final long serialVersionUID = -5629564652869134644L;

	@Override
	public abstract void action();
	@Override
	public abstract boolean done();

}
