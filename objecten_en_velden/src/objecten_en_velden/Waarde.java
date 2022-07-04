package objecten_en_velden;

public abstract class Waarde {
	
	// Gedragssubtypering
	
	/**
	 * @post If `obj` is not a Waarde object, the result equals `false`.
	 *     | obj instanceof Waarde || result == false
	 */
	@Override
	public abstract boolean equals(Object obj);

}
