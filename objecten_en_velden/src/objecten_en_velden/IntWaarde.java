package objecten_en_velden;

/**
 * @immutable
 */
public class IntWaarde extends Waarde {
	
	private final int waarde;
	
	public int getWaarde() {
		return waarde;
	}
	
	/**
	 * @post | getWaarde() == waarde
	 */
	public IntWaarde(int waarde) {
		this.waarde = waarde;
	}
	
	/**
	 * @post | result == (obj instanceof IntWaarde w ? getWaarde() == w.getWaarde() : false)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IntWaarde w ? waarde == w.waarde : false;
	}

}
