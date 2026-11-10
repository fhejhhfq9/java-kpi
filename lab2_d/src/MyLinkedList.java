import java.util.Objects;
public class MyLinkedList implements MyList{
    public static class Node{
        Object data;
        Node prev;
        Node next;

        Node(Node prev, Object data, Node next){
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(Object e){                              //додати в кінець
        Node temp = new Node(tail, e, null);
        if(tail == null){
            head = temp;
            tail = temp;
        }else{
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
        size++;
    }

    @Override
    public void add(int index, Object element){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            insertAtBeginning(element);
        } else if (index == size) {
            add(element);
        }else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node temp = new Node(current.prev, element, current);
            current.prev.next = temp;
            current.prev = temp;
            size++;
        }
    }

    @Override
    public void addAll(Object[] c){                  // додати в кінець
        if (c == null) throw new NullPointerException("Array is null");
        for (Object o : c) {
            add(o);
        }
    }

    @Override
    public void addAll(int index, Object[] c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (c == null) throw new NullPointerException("Array is null");
        for (int i = 0; i < c.length; i++) {
            add(index + i, c[i]);
        }
    }
    @Override
    public Object get(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Object o = getNode(index).data;
        return o;
    }

    @Override
    public Object remove(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node node = getNode(index);
        Node prev = node.prev;
        Node next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        Object removed = node.data;
        node.data = null;
        node.prev = null;
        node.next = null;
        size--;
        return removed;
    }


    @Override
    public void set(int index, Object element){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node node = getNode(index);
        node.data = element;
    }

    @Override
    public int indexOf(Object o){
        int i = 0;
        for (Node n = head; n != null; n = n.next, i++) {
            if (Objects.equals(o, n.data)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node n = head; n != null; n = n.next, i++) {
            result[i] = n.data;
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node n = head; n != null; n = n.next) {
            sb.append(n.data);
            if (n.next != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }


    private void insertAtBeginning(Object e)
    {
        Node temp = new Node(null, e, head);
        if (head == null) {
            tail = temp;
            head = temp;
        } else {
            head.prev = temp;
            head = temp;
        }
        size++;
    }

    private Node getNode(int index) {
        Node x;
        if (index < size / 2) {
            x = head;
            for (int i = 0; i < index; i++) x = x.next;
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
        }
        return x;
    }

}
