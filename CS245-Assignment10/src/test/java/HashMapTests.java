import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;



@SuppressWarnings("rawtypes")
class HashMapTests {

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
	
	@Test
	public void testSize() {
		try {
			assertEquals(0, hm.size(), "size method is not working correctly (check when empty)");
			size.set(hm, 1);
			assertEquals(1, hm.size(), "size method is not working correctly (check when non-empty)");
		} catch (Exception e) {
			fail("size method is not working correctly");
		}
	}
	
	@Test
	public void testClear() {
		try {
			size.set(hm, 3);
			HashMap.HashEntry[] data = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "one", 1), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "two", 2), (HashMap.HashEntry)REMOVED.get(hm), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "three", 3), null};
			elementData.set(hm, data);
			hm.clear();
			assertEquals(0, size.get(hm), "clear method is not working correctly (check size)");
			for(int i=0; i<data.length; ++i) {
				assertNull(((Object[])elementData.get(hm))[i], "clear method is not working correctly (non-null elements should not exist)");
			}
		} catch (Exception e) {
			fail("clear method is not working correctly");
		}
	}
	
	@Test
	public void testPut() {
		try {
			//put without rehash
			HashMap.HashEntry[] data = new HashMap.HashEntry[10];
			hm.put("2", 2);				//hashes to 0 [2,n,n,n,n,n,n,n,n,n]
			data[0] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested without collision, wrapping, removals, duplicate key, or rehash)");
			assertEquals(1, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("13", 13);			//hashes to 0 [2,13,n,n,n,n,n,n,n,n]
			data[1] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "13", 13);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision but without wrapping, removals, duplicate key, or rehash)");
			assertEquals(2, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("1", 1);				//hashes to 9 [2,13,n,n,n,n,n,n,n,1]
			data[9] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "1", 1);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested without collision, wrapping, removals, duplicate key, or rehash)");
			assertEquals(3, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("12", 12);			//hashes to 9 [2,13,12,n,n,n,n,n,n,1]
			data[2] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "12", 12);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision and wrapping but without removals, duplicate key, or rehash)");
			assertEquals(4, size.get(hm), "put method is not working correctly (check size)");
			
			data[1] = (HashMap.HashEntry)REMOVED.get(hm);	//removes index 1 [2,r,12,n,n,n,n,n,n,1]
			elementData.set(hm, data);	
			size.set(hm, 3);
			hm.put("21", 21);		//hashes to 9 [2,21,12,n,n,n,n,n,n,1]
			data[1] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "21", 21);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision, wrapping, and removals but without duplicate key or rehash)");
			assertEquals(4, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("22", 22);			//hashes to 0 [2,21,12,22,n,n,n,n,n,1]
			data[3] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "22", 22);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision but without wrapping, removals, duplicate key, or rehash)");
			assertEquals(5, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("3", 3);			//hashes to 1 [2,21,12,22,3,n,n,n,n,1]
			data[4] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "3", 3);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision but without wrapping, removals, duplicate key, or rehash)");
			assertEquals(6, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("4", 4);			//hashes to 2 [2,21,12,22,3,4,n,n,n,1]
			data[5] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "4", 4);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision but without wrapping, removals, duplicate key, or rehash)");
			assertEquals(7, size.get(hm), "put method is not working correctly (check size)");
			
			hm.put("5", 55);			//hashes to 3 [2,21,12,22,3,4,55,n,n,1]
			data[6] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "5", 55);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision but without wrapping, removals, duplicate key, or rehash)");
			assertEquals(8, size.get(hm), "put method is not working correctly (check size)");
			
			//put duplicate without rehash
			HashMap.HashEntry he = ((HashMap.HashEntry[])(elementData.get(hm)))[6];
			hm.put("5", 5);			//hashes to 3 [2,21,12,22,3,4,5,n,n,1]
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), data), "put method is not working correctly (tested with collision and duplicate key, but without wrapping, removals, or rehash)");
			assertEquals(8, size.get(hm), "put method is not working correctly (check size)");
			assertTrue(he == ((HashMap.HashEntry[])(elementData.get(hm)))[6], "put method is not working correctly");
			
			//put with rehash and removal
			data[7] = (HashMap.HashEntry)REMOVED.get(hm);	//[2,21,12,22,3,4,5,r,n,1]
			elementData.set(hm, data);
			hm.put("6", 6);			//hashes to 14 [22,n,n,n,n,n,n,n,n,12,2,3,4,5,1,6,n,n,n,21]
			HashMap.HashEntry[] newData = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "22", 22), null, null, null, null, null, null, null, null, (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "12", 12), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "3", 3), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "4", 4), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "5", 5), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "1", 1), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "6", 6), null, null, null, (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "21", 21)};
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), newData), "put method (and/or rehash) is not working correctly (tested with collision, removals, and rehash but without wrapping)");
			assertEquals(9, size.get(hm), "put method (and/or rehash) is not working correctly (check size)");
			
			//put with removals
			newData[7] = (HashMap.HashEntry)REMOVED.get(hm);	//[22,n,n,n,n,n,n,r,n,12,2,3,4,5,1,6,n,n,n,21]
			elementData.set(hm, newData);	
			hm.put("10", 10);		//hashes to 7 [22,n,n,n,n,n,n,10,n,12,2,3,4,5,1,6,n,n,n,21]
			newData[7] = (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "10", 10);
			assertTrue(equalLists((HashMap.HashEntry[])elementData.get(hm), newData), "put method (and/or rehash) is not working correctly (tested without collision, duplicate key, wrapping, or removals but after rehash)");
			assertEquals(10, size.get(hm), "put method (and/or rehash) is not working correctly (check size)");
		} catch (Exception e) {
			fail("put method (and/or rehash) is not working correctly");
		}
	}
	
	@Test
	public void testRemove() {
		try {
			assertFalse(hm.containsKey("2"), "remove method is not working correctly");
			//[2,13,12,n,n,n,n,n,n,1]
			HashMap.HashEntry[] newData = {(HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "2", 2), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "13", 13), (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "12", 12), null, null, null, null, null, null, (HashMap.HashEntry)hashEntryConstructor.newInstance(hm, "1", 1)};
			elementData.set(hm, newData);
			size.set(hm, 4);
			assertEquals(new Integer(2), hm.remove("2"), "remove method is not working correctly (returned value incorrect)");
			assertEquals(3, size.get(hm), "remove method is not working correctly (check size)");
			assertEquals(new Integer(13), hm.remove("13"), "remove method is not working correctly (returned value incorrect)");
			assertEquals(2, size.get(hm), "remove method is not working correctly (check size)");
			assertEquals(new Integer(1), hm.remove("1"), "remove method is not working correctly (returned value incorrect)");
			assertEquals(1, size.get(hm), "remove method is not working correctly (check size)");
			assertEquals(new Integer(12), hm.remove("12"), "remove method is not working correctly (returned value incorrect)");
			assertEquals(0, size.get(hm), "remove method is not working correctly (check size)");
			assertNull(hm.remove("12"), "remove method is not working correctly (didn't return null when appropriate)");
			assertEquals(0, size.get(hm), "remove method is not working correctly (check size)");
		} catch (Exception e) {
			fail("remove method is not working correctly");
		}
	}
	
	private boolean equalLists(HashMap.HashEntry[] a, HashMap.HashEntry[] b) {
		if(a.length != b.length) {
			return false;
		}
		try {
			for(int i=0; i<a.length; ++i) {
				if(a[i] == null && b[i] == null) {
					continue;
				} else if(a[i] == REMOVED.get(hm) && b[i] == REMOVED.get(hm)) {
					continue;
				}
				if((a[i] == null && b[i] != null) || (a[i] != null && b[i] == null) || (a[i] == REMOVED.get(hm) && b[i] != REMOVED.get(hm)) || (a[i] != REMOVED.get(hm) && b[i] == REMOVED.get(hm))) {
					return false;
				}
				if(!a[i].getKey().equals(b[i].getKey()) || !a[i].getValue().equals(b[i].getValue())) {
					return false;
				}
			}
		} catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return true;
	}
	
	private int h(Object k, int l) {
		return Math.abs(k.hashCode()) % l;
	}
}
