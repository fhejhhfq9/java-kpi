import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public class MyLinkedHashSet implements Iterable<Object> {
    private static class Entry {
        final Object key;
        final int hash;
        Entry next;
        Entry before;
        Entry after;

        Entry(Object key, int hash) {
            this.key = key;
            this.hash = hash;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry[] table;
    private Entry head;
    private Entry tail;
    private int size;
    private int threshold;
    private int modCount;

    public MyLinkedHashSet() {
        table = new Entry[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }


    public boolean add(Object e) {
        int h = hash(e);
        int i = h & (table.length - 1);
        for (Entry x = table[i]; x != null; x = x.next) {
            if (x.hash == h && eq(e, x.key)) {
                return false;
            }
        }

        Entry entry = new Entry(e, h);
        entry.next = table[i];
        table[i] = entry;

        entry.before = tail;
        if (tail == null) {
            head = entry;
        } else {
            tail.after = entry;
        }
        tail = entry;

        size++;
        modCount++;
        if (size > threshold) {
            resize();
        }
        return true;
    }


    public boolean addAll(Object[] c) {
        if (c == null) {
            throw new NullPointerException("Array is null");
        }
        boolean changed = false;
        for (Object o : c) {
            if (add(o)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean contains(Object o) {
        int h = hash(o);
        for (Entry x = table[h & (table.length - 1)]; x != null; x = x.next) {
            if (x.hash == h && eq(o, x.key)) {
                return true;
            }
        }
        return false;
    }


    public boolean remove(Object o) {
        int h = hash(o);
        int i = h & (table.length - 1);
        Entry prev = null;
        for (Entry x = table[i]; x != null; prev = x, x = x.next) {
            if (x.hash == h && eq(o, x.key)) {
                if (prev == null) {
                    table[i] = x.next;
                } else {
                    prev.next = x.next;
                }
                unlinkFromOrder(x);
                size--;
                modCount++;
                return true;
            }
        }
        return false;
    }

    public Object get(Object o) {
        int h = hash(o);
        for (Entry x = table[h & (table.length - 1)]; x != null; x = x.next) {
            if (x.hash == h && eq(o, x.key)) {
                return x.key;
            }
        }
        return null;
    }

    public Object getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Set is empty");
        }
        return head.key;
    }
    public boolean moveToEnd(Object o) {
        int h = hash(o);
        Entry x = null;
        for (Entry e = table[h & (table.length - 1)]; e != null; e = e.next) {
            if (e.hash == h && eq(o, e.key)) {
                x = e;
                break;
            }
        }
        if (x == null) {
            return false;
        }
        if (x == tail) {
            return true;
        }

        Entry b = x.before;
        Entry a = x.after;
        if (b == null) {
            head = a;
        } else {
            b.after = a;
        }
        a.before = b;

        x.before = tail;
        x.after = null;
        tail.after = x;
        tail = x;
        modCount++;
        return true;
    }



    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
        modCount++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Entry e = head; e != null; e = e.after) {
            result[i++] = e.key;
        }
        return result;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Entry e = head; e != null; e = e.after) {
            sb.append(e.key);
            if (e.after != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }



    private static int hash(Object o) {
        if (o == null) {
            return 0;
        }
        int h = o.hashCode();
        return h ^ (h >>> 16);
    }

    private static boolean eq(Object a, Object b) {

        return Objects.equals(a,b);
    }

    private void unlinkFromOrder(Entry x) {
        Entry b = x.before;
        Entry a = x.after;
        if (b == null) {
            head = a;
        } else {
            b.after = a;
        }
        if (a == null) {
            tail = b;
        } else {
            a.before = b;
        }
        x.before = null;
        x.after = null;
        x.next = null;
    }


    private void resize() {
        Entry[] newTable = new Entry[table.length * 2];
        for (Entry e = head; e != null; e = e.after) {
            int i = e.hash & (newTable.length - 1);
            e.next = newTable[i];
            newTable[i] = e;
        }
        table = newTable;
        threshold = (int) (newTable.length * LOAD_FACTOR);
    }

    private class Itr implements Iterator<Object> {
        private Entry nextEntry = head;
        private Entry lastReturned;
        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return nextEntry != null;
        }

        @Override
        public Object next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (nextEntry == null) {
                throw new NoSuchElementException();
            }
            lastReturned = nextEntry;
            nextEntry = nextEntry.after;
            return lastReturned.key;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            MyLinkedHashSet.this.remove(lastReturned.key);
            lastReturned = null;
            expectedModCount = modCount;
        }
    }
}