
public class Main {
    public static void main(String[] args) {

        System.out.println("=== MyArrayList ===");
        MyArrayList array = new MyArrayList();

        array.add(10);
        array.add(20);
        array.add(30);
        array.add(1, 15);
        array.addAll(new Object[]{40, 50});

        System.out.println(array);
        System.out.println("Element at index 2: " + array.get(2));
        System.out.println("Index of 30: " + array.indexOf(30));

        array.remove(0);
        array.set(0, 25);
        System.out.println("After changes: " + array);
        System.out.println("Size: " + array.size());


        // MyLinkedList
        System.out.println("\n=== MyLinkedList ===");
        MyLinkedList linked = new MyLinkedList();

        linked.add(1);
        linked.add(2);
        linked.add(3);
        linked.add(1, 5);
        linked.addAll(new Object[]{4, 6});

        System.out.println(linked);
        System.out.println("Element at index 1: " + linked.get(1));

        linked.remove(0);
        linked.set(0, 7);
        System.out.println("After changes: " + linked);
        System.out.println("Index of 3: " + linked.indexOf(3));


        // MyLinkedHashSet
        System.out.println("\n=== MyLinkedHashSet ===");
        MyLinkedHashSet set = new MyLinkedHashSet();

        set.add(10);
        set.add(20);
        set.add(30);
        set.add(10);

        System.out.println(set);
        System.out.println("Contains 20: " + set.contains(20));
        System.out.println("First element: " + set.getFirst());

        set.moveToEnd(10);
        System.out.println("After moving 10: " + set);

        set.remove(20);
        System.out.println("After removing 20: " + set);

        System.out.print("Iterating: ");
        for (Object o : set) {
            System.out.print(o + " ");
        }
        System.out.println();


        // MyLRUCache
        System.out.println("\n=== MyLRUCache ===");
        MyLRUCache cache = new MyLRUCache(3);

        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);

        System.out.println("Cache: " + cache);
        System.out.println("Get 1: " + cache.get(1));

        cache.put(4, 400);
        System.out.println("After adding 4: " + cache);
        System.out.println("Contains 2: " + cache.containsKey(2));

        cache.put(1, 150);
        System.out.println("Updated 1: " + cache);

        cache.remove(3);
        System.out.println("After removing 3: " + cache);
        System.out.println("Keys: " + java.util.Arrays.toString(cache.keys()));
        System.out.println("Size: " + cache.size());
    }
}
