import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

@SuppressWarnings("rawtypes")
class Lab12Tests {

	private HashMap<String, Integer> hm;
	private Field elementData;
	private Field REMOVED;
	private Field size;
	private Method hashFunction;
	private Constructor<?> hashEntryConstructor;
	
	@BeforeEach
	public void setUp() throws Exception {
		hm = new HashMap<String, Integer>();
		elementData = HashMap.class.getDeclaredField("elementData");
		elementData.setAccessible(true);
		REMOVED = HashMap.class.getDeclaredField("REMOVED");
		REMOVED.setAccessible(true);
		size = HashMap.class.getDeclaredField("size");
		size.setAccessible(true);
		hashFunction = HashMap.class.getDeclaredMethod("hashFunction", Object.class);
		hashFunction.setAccessible(true);
		Class<?> hashEntryClass = Class.forName("HashMap$HashEntry");
		hashEntryConstructor = hashEntryClass.getDeclaredConstructors()[0];
	}

	@Test
	public void testFields() {
		assertEquals(4, HashMap.class.getDeclaredFields().length, "HashMap should only have \"MAX_LOAD_FACTOR\", \"elementData\", \"REMOVED\", and \"size\" fields");
	}
	
	@Test
	public void testConstructor() {
		try {
			assertEquals(10, ((Object[])elementData.get(hm)).length, "HashMap constructor is not working correctly (check array length)");
			assertNotNull(REMOVED.get(hm), "HashMap constructor is not working correctly (check REMOVED)");
			assertEquals(0, size.get(hm), "HashMap constructor is not working correctly (check size)");
		} catch (Exception e) {
			fail("HashMap constructor is not working correctly");
		}
	}
	
	@Test
	public void testIsEmpty() {
		try {
			assertEquals(true, hm.isEmpty(), "isEmpty is not working correctly (not returning true when appropriate)");
			size.set(hm, 1);
			assertEquals(false, hm.isEmpty(), "isEmpty is not working correctly (not returning false when appropriate)");
		} catch (Exception e) {
			fail("isEmpty is not working correctly");
		}
	}
	
	@Test
	public void testHashFunction() {
		try {
			Random r = new Random();
			int capacity = 10;
			int randVal;
			for(int c=1; c<5; ++c) {
				HashMap.HashEntry[] data = new HashMap.HashEntry[capacity];
				elementData.set(hm, data);
				for(int i=0; i<100; ++i) {
					randVal = r.nextInt();
					assertEquals(h(randVal, capacity), hashFunction.invoke(hm, randVal), "hashFunction method is not working correctly (not returning correct value)");
				}
				capacity = capacity * 2;
			}
		} catch (Exception e) {
			fail("hashFunction method is not working correctly");
		}
	}
	
	@Test
	public void testContainsKey() {
		try {
			assertFalse(hm.containsKey("2"), "containsKey method is not working correctly");
			//[2,r,12,n,n,n,n,n,n,1]
			HashMap.HashEntry[] newData = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2), (HashMap.HashEntry)REMOVED.get(hm), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "12", 12), null, null, null, null, null, null, (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "1", 1)};
			elementData.set(hm, newData);
			size.set(hm, 3);
			assertTrue(hm.containsKey("2"), "containsKey method is not working correctly (not returning true when appropriate)");
			assertTrue(hm.containsKey("1"), "containsKey method is not working correctly (not returning true when appropriate)");
			assertTrue(hm.containsKey("12"), "containsKey method is not working correctly (not returning true when appropriate)");
			assertFalse(hm.containsKey("13"), "containsKey method is not working correctly (not returning false when appropriate)");
		} catch (Exception e) {
			fail("containsKey method is not working correctly");
		}
	}
	
	@Test
	public void testToString() {
		try {
			assertEquals("[]", hm.toString(), "toString method is not working correctly (check when empty)");
			
			HashMap.HashEntry[] data = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2)};
			size.set(hm, 1);
			elementData.set(hm, data);
			assertEquals("[2]", hm.toString(), "toString method is not working correctly (check with single element)");
			
			HashMap.HashEntry[] newData = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2), (HashMap.HashEntry)REMOVED.get(hm), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "12", 12), null, null, null, null, null, null, (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "1", 1)};
			elementData.set(hm, newData);
			size.set(hm, 3);
			assertEquals("[2, 12, 1]", hm.toString(), "toString method is not working correctly (check with multiple elements)");
		} catch (Exception e) {
			fail("toString method is not working correctly");
		}
	}
	
	private int h(Object k, int l) {
		return Math.abs(k.hashCode()) % l;
	}
}
