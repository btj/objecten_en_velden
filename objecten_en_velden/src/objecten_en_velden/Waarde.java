package objecten_en_velden;

public abstract class Waarde {
	
	// Gedragssubtypering
	
	/**
	 * @post | obj instanceof Waarde || result == false
	 */
	@Override
	public abstract boolean equals(Object obj);

}
