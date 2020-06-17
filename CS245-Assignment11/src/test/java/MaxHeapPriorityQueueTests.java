import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

class MaxHeapPriorityQueueTests {
	
	private MaxHeapPriorityQueue<Integer> mhpq;
	private Field elementData;
	private Field size;

	@BeforeEach
	void setUp() throws Exception {
		mhpq = new MaxHeapPriorityQueue<Integer>();
		elementData = MaxHeapPriorityQueue.class.getDeclaredField("elementData");
		elementData.setAccessible(true);
		size = MaxHeapPriorityQueue.class.getDeclaredField("size");
		size.setAccessible(true);
	}

	@Test
	public void testFields() {
		assertEquals(2, MaxHeapPriorityQueue.class.getDeclaredFields().length, "MaxHeapPriorityQueue should only have \"elementData\" and \"size\" fields");
	}

	@Test
	public void testConstructor() {
		try {
			assertEquals(10, ((Object[])elementData.get(mhpq)).length, "MaxHeapPriorityQueue constructor is not working correctly (check array capacity)");
			assertEquals(0, size.get(mhpq), "MaxHeapPriorityQueue constructor is not working correctly (check size)");
		} catch (Exception e) {
			fail("MaxHeapPriorityQueue constructor is not working correctly");
		}
	}

	@Test
	public void testIsEmpty() {
		try {
			assertEquals(true, mhpq.isEmpty(), "isEmpty is not working correctly (not returning true when appropriate)");
			size.set(mhpq, 1);
			assertEquals(false, mhpq.isEmpty(), "isEmpty is not working correctly (not returing false when appropriate)");
		} catch (Exception e) {
			fail("isEmpty is not working correctly");
		}
	}

	@Test
	public void testSize() {
		try {
			assertEquals(0, mhpq.size(), "size method is not working correctly (check when empty)");
			size.set(mhpq, 1);
			assertEquals(1, mhpq.size(), "size method is not working correctly (check when non-empty)");
		} catch (Exception e) {
			fail("size method is not working correctly");
		}
	}

	@Test
	public void testClear() {
		try {
			size.set(mhpq, 8);
			Integer[] data = {null,8,7,6,5,4,3,2,1,null};
			elementData.set(mhpq, data);
			mhpq.clear();
			assertEquals(0, size.get(mhpq), "clear method is not working correctly (check size)");
		} catch (Exception e) {
			fail("clear method is not working correctly");
		}
	}

	@Test
	public void testPeek() {
		try {
			size.set(mhpq, 8);
			Integer[] data = {null,8,7,6,5,4,3,2,1,null};
			elementData.set(mhpq, data);
			assertEquals(new Integer(8), mhpq.peek(), "peek method is not working correctly (wrong return value)");
			assertArrayEquals(data, (Comparable[])elementData.get(mhpq), "peek method is not working correctly (elementData shouldn't change)");
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			assertEquals(new Integer(randVal), mhpq.peek(), "peek method is not working correctly (wrong return value)");
			assertArrayEquals(data2, (Comparable[])elementData.get(mhpq), "peek method is not working correctly (elementData shouldn't change)");
		} catch (Exception e) {
			fail("peek method is not working correctly");
		}
	}

	@Test
	public void testEmptyPeek() {
		Executable statement = () -> mhpq.peek();
		assertThrows(NoSuchElementException.class, statement, "peek is not throwing an exception correctly when empty");
	}

	@Test
	public void testAdd() {
		try {
			Integer[] data = {null,5,null,null,null,null,null,null,null,null};
			mhpq.add(5);
			assertArrayEquals(data, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after adding first element)");
			assertEquals(1, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data2 = {null,5,3,null,null,null,null,null,null,null};
			mhpq.add(3);
			assertArrayEquals(data2, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect)");
			assertEquals(2, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data3 = {null,5,3,2,null,null,null,null,null,null};
			mhpq.add(2);
			assertArrayEquals(data3, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect)");
			assertEquals(3, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data4 = {null,7,5,2,3,null,null,null,null,null};
			mhpq.add(7);
			assertArrayEquals(data4, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after bubble up)");
			assertEquals(4, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data5 = {null,7,5,2,3,4,null,null,null,null};
			mhpq.add(4);
			assertArrayEquals(data5, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect)");
			assertEquals(5, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data6 = {null,7,5,4,3,4,2,null,null,null};
			mhpq.add(4);
			assertArrayEquals(data6, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after bubble up with duplicate)");
			assertEquals(6, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data7 = {null,8,5,7,3,4,2,4,null,null};
			mhpq.add(8);
			assertArrayEquals(data7, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after bubble up)");
			assertEquals(7, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data8 = {null,10,8,7,5,4,2,4,3,null};
			mhpq.add(10);
			assertArrayEquals(data8, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after bubble up)");
			assertEquals(8, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data9 = {null,10,9,7,8,4,2,4,3,5};
			mhpq.add(9);
			assertArrayEquals(data9, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after bubble up)");
			assertEquals(9, size.get(mhpq), "add method is not working correctly (check size)");

			Integer[] data10 = {null,10,9,7,8,4,2,4,3,5,1,null,null,null,null,null,null,null,null,null};
			mhpq.add(1);
			assertArrayEquals(data10, (Comparable[])elementData.get(mhpq), "add method is not working correctly (array contents incorrect after expected capacity increase)");
			assertEquals(10, size.get(mhpq), "add method is not working correctly (check size after capacity increase)");
		} catch (Exception e) {
			fail("add method is not working correctly");
		}
	}

	@Test
	public void testRemove() {
		try {
			Integer[] data = {null,10,9,7,8,4,2,4,3,5};
			elementData.set(mhpq, data);
			size.set(mhpq, 9);

			Integer[] data9 = {null,9,8,7,5,4,2,4,3,5};
			assertEquals(new Integer(10), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data9, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(8, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data8 = {null,8,5,7,3,4,2,4,3,5};
			assertEquals(new Integer(9), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data8, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(7, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data7 = {null,7,5,4,3,4,2,4,3,5};
			assertEquals(new Integer(8), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data7, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(6, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data6 = {null,5,4,4,3,2,2,4,3,5};
			assertEquals(new Integer(7), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data6, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(5, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data5 = {null,4,3,4,2,2,2,4,3,5};
			assertEquals(new Integer(5), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data5, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(4, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data4 = {null,4,3,2,2,2,2,4,3,5};
			assertEquals(new Integer(4), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data4, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(3, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data3 = {null,3,2,2,2,2,2,4,3,5};
			assertEquals(new Integer(4), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data3, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(2, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data2 = {null,2,2,2,2,2,2,4,3,5};
			assertEquals(new Integer(3), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data2, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(1, size.get(mhpq), "remove method is not working correctly (check size)");

			Integer[] data1 = {null,2,2,2,2,2,2,4,3,5};
			assertEquals(new Integer(2), mhpq.remove(), "remove method is not working correctly (wrong return value)");
			assertArrayEquals(data1, (Comparable[])elementData.get(mhpq), "remove method is not working correctly (array contents incorrect)");
			assertEquals(0, size.get(mhpq), "remove method is not working correctly (check size)");
		} catch (Exception e) {
			fail("remove method is not working correctly");
		}
	}

	@Test
	public void testEmptyRemove() {
		Executable statement = () -> mhpq.remove();
		assertThrows(NoSuchElementException.class, statement, "remove is not throwing an exception correctly when empty");
	}

	@Test
	public void testToString() {
		try {
			assertEquals("[]", mhpq.toString(), "toString method is not working correctly (check with 0 elements)");

			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			assertEquals("["+randVal+"]", mhpq.toString(), "toString method is not working correctly (check with single element)");

			size.set(mhpq, 9);
			Integer[] data = {null,8,6,7,5,4,3,2,1,0};
			elementData.set(mhpq, data);
			assertEquals("[8, 6, 7, 5, 4, 3, 2, 1, 0]", mhpq.toString(), "toString method is not working correctly (check with multiple elements");
		} catch (Exception e) {
			fail("toString method is not working correctly");
		}
	}

	@Test
	public void testToArray() {
		try {
			Integer[] a = new Integer[0];
			assertArrayEquals(a, mhpq.toArray(), "toArray method is not working correctly (check with 0 elements)");

			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			Integer[] a2 = {randVal};
			assertArrayEquals(a2, mhpq.toArray(), "toArray method is not working correctly (check with single element)");

			size.set(mhpq, 9);
			Integer[] data = {null,8,6,7,5,4,3,2,1,0};
			elementData.set(mhpq, data);
			Integer[] a3 = {8,6,7,5,4,3,2,1,0};
			assertArrayEquals(a3, mhpq.toArray(), "toArray method is not working correctly (check with multiple elements)");
		} catch (Exception e) {
			fail("toArray method is not working correctly");
		}
	}
	
	@Test
	public void testContains() {
		try {
			assertFalse(mhpq.contains(0), "contains method is not working correctly (check with 0 elements)");
			
			Integer[] data = {null,10,9,7,8,4,2,4,3,5,1,11,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data);
			size.set(mhpq, 10);
			
			assertTrue(mhpq.contains(10), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(9), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(7), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(8), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(4), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(2), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(3), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(5), "contains method is not working correctly (not returning true when appropriate)");
			assertTrue(mhpq.contains(1), "contains method is not working correctly (not returning true when appropriate)");
			assertFalse(mhpq.contains(11), "contains method is not working correctly (not returning false when appropriate)");
			assertArrayEquals(data, (Comparable[])elementData.get(mhpq), "contains method is not working correctly (shouldn't change array contents)");
			assertEquals(10, size.get(mhpq), "contains method is not working correctly (check size)");
		} catch (Exception e) {
			fail("contains method is not working correctly");
		}
	}
	
	@Test
	public void testIterator() {
		try {
			Iterator<Integer> itr = mhpq.iterator();
			assertFalse(itr.hasNext(), "your iterator is not working correctly (hasNext not returning false when empty)");
			
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			itr = mhpq.iterator();
			assertTrue(itr.hasNext(), "your iterator is not working correctly (hasNext not returning true when appropriate)");
			assertEquals(new Integer(randVal), itr.next(), "your iterator is not working correctly (next not returning correct value)");
			assertFalse(itr.hasNext(), "your iterator is not working correctly (hasNext not returning false when appropriate)");
			
			size.set(mhpq, 9);
			Integer[] data = {null,8,7,6,5,4,3,2,0,1};
			elementData.set(mhpq, data);
			itr = mhpq.iterator();
			for(int i=8; i>=2; --i) {
				assertTrue(itr.hasNext(), "your iterator is not working correctly  (hasNext not returning true when appropriate)");
				assertEquals(new Integer(i), itr.next(), "your iterator is not working correctly (next not returning correct value)");
			}
			assertTrue(itr.hasNext(), "your iterator is not working correctly (hasNext not returning true when appropriate)");
			assertEquals(new Integer(0), itr.next(), "your iterator is not working correctly (next not returning correct value)");
			assertTrue(itr.hasNext(), "your iterator is not working correctly (hasNext not returning true when appropriate)");
			assertEquals(new Integer(1), itr.next(), "your iterator is not working correctly (next not returning correct value)");
			assertFalse(itr.hasNext(), "your iterator is not working correctly (hasNext not returning false when appropriate)");
		} catch (Exception e) {
			fail("your iterator is not working correctly");
		}
	}
	
	@Test
	public void testHeapSort() {
		try {
			Integer[] a = new Integer[0];
			MaxHeapPriorityQueue.heapSort(a, 0);
			assertArrayEquals(new Integer[0], a, "heapSort method is not working correctly with array of capacity 0");
			
			Integer[] a1 = {5};
			Integer[] a1s = {5};
			MaxHeapPriorityQueue.heapSort(a1, 1);
			assertArrayEquals(a1s, a1, "heapSort method is not working correctly with array of capacity 1");
			
			Integer[] a2 = {5,3};
			Integer[] a2s = {3,5};
			MaxHeapPriorityQueue.heapSort(a2, 2);
			assertArrayEquals(a2s, a2, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a3 = {5,3,2};
			Integer[] a3s = {2,3,5};
			MaxHeapPriorityQueue.heapSort(a3, 3);
			assertArrayEquals(a3s, a3, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a4 = {5,3,2,7};
			Integer[] a4s = {2,3,5,7};
			MaxHeapPriorityQueue.heapSort(a4, 4);
			assertArrayEquals(a4s, a4, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a5 = {5,3,2,7,4};
			Integer[] a5s = {2,3,4,5,7};
			MaxHeapPriorityQueue.heapSort(a5, 5);
			assertArrayEquals(a5s, a5, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a6 = {5,3,2,7,4,4};
			Integer[] a6s = {2,3,4,4,5,7};
			MaxHeapPriorityQueue.heapSort(a6, 6);
			assertArrayEquals(a6s, a6, "heapSort method is not working correctly with unsorted array containing a duplicate value");
			
			Integer[] a7 = {5,3,2,7,4,4,8};
			Integer[] a7s = {2,3,4,4,5,7,8};
			MaxHeapPriorityQueue.heapSort(a7, 7);
			assertArrayEquals(a7s, a7, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a8 = {5,3,2,7,4,4,8,10};
			Integer[] a8s = {2,3,4,4,5,7,8,10};
			MaxHeapPriorityQueue.heapSort(a8, 8);
			assertArrayEquals(a8s, a8, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a9 = {5,3,2,7,4,4,8,10,9};
			Integer[] a9s = {2,3,4,4,5,7,8,9,10};
			MaxHeapPriorityQueue.heapSort(a9, 9);
			assertArrayEquals(a9s, a9, "heapSort method is not working correctly with unsorted array");
			
			Integer[] a10 = {5,3,2,7,4,4,8,10,9,1};
			Integer[] a10s = {1,2,3,4,4,5,7,8,9,10};
			MaxHeapPriorityQueue.heapSort(a10, 10);
			assertArrayEquals(a10s, a10, "heapSort method is not working correctly with unsorted array");
			
			Random r = new Random();
			int randVal = r.nextInt(10);
			Integer[] a102 = {5,3,2,7,4,4,8,10,9,1,randVal};
			Integer[] a102s = {1,2,3,4,4,5,7,8,9,10,randVal};
			MaxHeapPriorityQueue.heapSort(a102, 10);
			assertArrayEquals(a102s, a102, "heapSort method is not using the size parameter correctly");

		} catch (Exception e) {
			fail("heapSort method is not working correctly");
		}
	}
}
