public class Main {
    public static void main(String[] args) {

        MyList arrayList = new MyArrayList();
        MyList linkedList = new MyLinkedList();

        MyList[] lists = {arrayList, linkedList};

        for (MyList list : lists) {
            list.add("1");
            list.add("2");
            list.add("3");
            System.out.println(list.toString());
            list.add(1, "8");
            list.addAll(new Object[]{"4", "5"});

            System.out.println("List: " + list);
            System.out.println("Element at index 2: " + list.get(2));

            list.set(0, "999");
            System.out.println("Index of B: " + list.indexOf("4"));
            System.out.println("Removed: " + list.remove(1));

            System.out.println("Size: " + list.size());
            System.out.println("Array: " + java.util.Arrays.toString(list.toArray()));
            System.out.println("----------------------");
        }
    }
}