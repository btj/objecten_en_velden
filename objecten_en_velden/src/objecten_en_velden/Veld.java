package objecten_en_velden;

import java.util.Set;
import logicalcollections.LogicalMap;
import logicalcollections.LogicalSet;

/**
 * @invar | getObject() != null
 * @invar | getNaam() != null
 * @invar | getWaarde() != null
 * @invar | getObject().getVelden().get(getNaam()) == this
 * @invar | getWaarde() instanceof JavaObject o ? o.getVerwijzendeVelden().contains(this) : true
 */
public class Veld {
	
	/**
	 * @invar | object != null
	 * @invar | naam != null
	 * @invar | waarde != null
	 * @invar | object.velden.get(naam) == this
	 * @invar | waarde instanceof JavaObject o ? o.verwijzendeVelden.contains(this) : true
	 * @peerObject
	 */
	JavaObject object;
	String naam;
	/**
	 * @peerObjects | List.of(waarde).stream().filter(w -> w instanceof JavaObject).toList()
	 */
	Waarde waarde;
	
	/**
	 * @immutable
	 * @peerObject
	 */
	public JavaObject getObject() { return object; }
	
	/**
	 * @immutable
	 */
	public String getNaam() { return naam; }
	
	/**
	 * @peerObjects | List.of(result).stream().filter(w -> w instanceof JavaObject).toList()
	 */
	public Waarde getWaarde() { return waarde; }
	
	/**
	 * @throws IllegalArgumentException | object == null
	 * @throws IllegalArgumentException | naam == null
	 * @throws IllegalArgumentException | waarde == null
	 * @throws IllegalArgumentException | object.getVelden().containsKey(naam)
	 *
	 * @mutates_properties | object.getVelden()
	 * @mutates_properties | (...(waarde instanceof JavaObject o ? Set.<JavaObject>of(o) : Set.<JavaObject>of())).getVerwijzendeVelden()
	 * 
	 * @post | getObject() == object
	 * @post | getNaam() == naam
	 * @post | getWaarde() == waarde
	 * @post | getObject().getVelden().equals(LogicalMap.plus(old(object.getVelden()), naam, this))
	 * @post | waarde instanceof JavaObject ?
	 *       |     ((JavaObject)waarde).getVerwijzendeVelden().equals(LogicalSet.plus(old(((JavaObject)waarde).getVerwijzendeVelden()), this))
	 *       | :
	 *       |     true
	 */
	public Veld(JavaObject object, String naam, Waarde waarde) {
		if (object == null)
			throw new IllegalArgumentException("`object` is null");
		if (naam == null)
			throw new IllegalArgumentException("`naam` is null");
		if (waarde == null)
			throw new IllegalArgumentException("`waarde` is null");
		if (object.velden.containsKey(naam))
			throw new IllegalArgumentException("Het gegeven object heeft al een veld met de gegeven naam");
		
		this.object = object;
		this.naam = naam;
		this.waarde = waarde;
		
		object.velden.put(naam, this);
		
		if (waarde instanceof JavaObject o)
			o.verwijzendeVelden.add(this);
	}
	
	/**
	 * @pre | waarde != null
	 * 
	 * @mutates_properties | (...(getWaarde() instanceof JavaObject o ? Set.<JavaObject>of(o) : Set.<JavaObject>of())).getVerwijzendeVelden()
	 * @mutates_properties | (...(waarde instanceof JavaObject o ? Set.<JavaObject>of(o) : Set.<JavaObject>of())).getVerwijzendeVelden()
	 * 
	 * @post | getWaarde() == waarde
	 * @post | waarde == old(getWaarde()) ?
	 *       |    waarde instanceof JavaObject ?
	 *       |        ((JavaObject)waarde).getVerwijzendeVelden().equals(old(((JavaObject)waarde).getVerwijzendeVelden()))
	 *       |    :
	 *       |        true
	 *       | :
	 *       |    (old(getWaarde()) instanceof JavaObject ?
	 *       |        ((JavaObject)old(getWaarde())).getVerwijzendeVelden().equals(LogicalSet.minus(old(((JavaObject)getWaarde()).getVerwijzendeVelden()), this))
	 *       |    :
	 *       |        true) &&
	 *       |    (waarde instanceof JavaObject ?
	 *       |        ((JavaObject)waarde).getVerwijzendeVelden().equals(LogicalSet.plus(old(((JavaObject)waarde).getVerwijzendeVelden()), this)) 
	 *       |    :
	 *       |        true)
	 */
	public void setWaarde(Waarde waarde) {
		if (this.waarde instanceof JavaObject o)
			o.verwijzendeVelden.remove(this);
		this.waarde = waarde;
		if (this.waarde instanceof JavaObject o)
			o.verwijzendeVelden.add(this);
	}

}
