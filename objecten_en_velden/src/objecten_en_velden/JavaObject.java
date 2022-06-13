package objecten_en_velden;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @invar | getVelden() != null
 * @invar | getVelden().keySet().stream().allMatch(n -> n != null && getVelden().get(n) != null && getVelden().get(n).getNaam() == n && getVelden().get(n).getObject() == this)
 * @invar | getVerwijzendeVelden().stream().allMatch(v -> v != null && v.getWaarde() == this)
 */
public class JavaObject extends Waarde {
	
	/**
	 * @invar | velden != null
	 * @invar | velden.keySet().stream().allMatch(n -> n != null && velden.get(n) != null && velden.get(n).naam == n && velden.get(n).object == this)
	 * @invar | verwijzendeVelden.stream().allMatch(v -> v != null && v.waarde == this)
	 * @representationObject
	 * @peerObjects | velden.values()
	 */
	HashMap<String, Veld> velden = new HashMap<>();
	/**
	 * @representationObject
	 * @peerObjects
	 */
	HashSet<Veld> verwijzendeVelden = new HashSet<>();
	
	/**
	 * @creates | result
	 * @peerObjects | result.values()
	 */
	public Map<String, Veld> getVelden() { return Map.copyOf(velden); }
	
	/**
	 * @creates | result
	 * @peerObjects
	 */
	public Set<Veld> getVerwijzendeVelden() { return Set.copyOf(verwijzendeVelden); }
	
	/**
	 * @post | getVelden().isEmpty()
	 * @post | getVerwijzendeVelden().isEmpty()
	 */
	public JavaObject() {}
	
	/**
	 * @post | result == (this == obj)
	 */
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	public Iterator<JavaObject> getNextIterator() {
		return new Iterator<JavaObject>() {
			JavaObject object = JavaObject.this;
			
			@Override
			public boolean hasNext() {
				return object != null;
			}
			
			@Override
			public JavaObject next() {
				JavaObject result = object;
				if (object.velden.containsKey("next") && object.velden.get("next").waarde instanceof JavaObject o)
					object = o;
				else
					object = null;
				return result;
			}
		};
	}
	
	public void forEachBereikbaarObject(Consumer<? super JavaObject> consumer) {
		consumer.accept(this);
		for (Veld veld : velden.values()) {
			if (veld.waarde instanceof JavaObject o)
				o.forEachBereikbaarObject(consumer);
		}
	}
	
	public Stream<String> getNamenVanObjectenVanVerwijzendeVelden() {
		return verwijzendeVelden.stream().flatMap(v -> v.object.velden.keySet().stream());
	}

}
