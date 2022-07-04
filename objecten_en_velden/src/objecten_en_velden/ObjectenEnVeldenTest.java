package objecten_en_velden;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ObjectenEnVeldenTest {

	@Test
	void test() {
		JavaObject p = new JavaObject();
		assertEquals(Map.of(), p.getVelden());
		assertEquals(Set.of(), p.getVerwijzendeVelden());
		
		IntWaarde w10 = new IntWaarde(10);
		assertEquals(10, w10.getWaarde());
		
		Veld p_x = new Veld(p, "x", w10);
		assertEquals(p, p_x.getObject());
		assertEquals("x", p_x.getNaam());
		assertEquals(w10, p_x.getWaarde());
		assertEquals(Map.of("x", p_x), p.getVelden());
		
		Veld p_y = new Veld(p, "y", new IntWaarde(20));
		JavaObject c = new JavaObject();
		Veld c_straal = new Veld(c, "straal", new IntWaarde(5));
		Veld c_middelpunt = new Veld(c, "middelpunt", p);
		assertEquals(Set.of(c_middelpunt), p.getVerwijzendeVelden());
		
		JavaObject p2 = new JavaObject();
		c_middelpunt.setWaarde(p2);
		assertEquals(Set.of(), p.getVerwijzendeVelden());
		assertEquals(Set.of(c_middelpunt), p2.getVerwijzendeVelden());
		
		c_middelpunt.setWaarde(w10);
		assertEquals(Set.of(), p2.getVerwijzendeVelden());
		
		c_middelpunt.setWaarde(p);
		assertEquals(Set.of(c_middelpunt), p.getVerwijzendeVelden());
		
		assertEquals(w10, new IntWaarde(10));
		assertEquals(p, p);
		assertNotEquals(p, p2);
		assertNotEquals(w10, new IntWaarde(11));
		
		JavaObject linkedListNode3 = new JavaObject();
		JavaObject linkedListNode2 = new JavaObject();
		new Veld(linkedListNode2, "next", linkedListNode3);
		JavaObject linkedListNode1 = new JavaObject();
		new Veld(linkedListNode1, "next", linkedListNode2);
		ArrayList<JavaObject> nodes = new ArrayList<>();
		for (var i = linkedListNode1.getNextIterator(); i.hasNext(); )
			nodes.add(i.next());
		assertEquals(List.of(linkedListNode1, linkedListNode2, linkedListNode3), nodes);
		
		Set<JavaObject> cReachableSet = new HashSet<>();
		c.forEachBereikbaarObject(o -> cReachableSet.add(o));
		assertEquals(Set.of(c, p), cReachableSet);
		
		Set<JavaObject> node1ReachableSet = new HashSet<>();
		linkedListNode1.forEachBereikbaarObject(o -> node1ReachableSet.add(o));
		assertEquals(Set.of(linkedListNode1, linkedListNode2, linkedListNode3), node1ReachableSet);
		
		assertEquals(Set.of("straal", "middelpunt"), p.getNamenVanObjectenVanVerwijzendeVelden().collect(Collectors.toSet()));
;	}

}
