import java.util.Iterator;

public class MyLRUCache {
    private static final long NEVER = Long.MAX_VALUE;

    private static class CacheEntry {
        final Object key;
        Object value;
        long expiresAt;

        CacheEntry(Object key, Object value, long expiresAt) {
            this.key = key;
            this.value = value;
            this.expiresAt = expiresAt;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CacheEntry && key.equals(((CacheEntry) o).key);
        }
    }

    private final MyLinkedHashSet entries = new MyLinkedHashSet();
    private final int capacity;
    private final long defaultTtl;
    private int expiringCount;

    public MyLRUCache(int capacity) {
        this(capacity, 0);
    }


    public MyLRUCache(int capacity, long ttlMillis) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        if (ttlMillis < 0) {
            throw new IllegalArgumentException("TTL must not be negative: " + ttlMillis);
        }
        this.capacity = capacity;
        this.defaultTtl = ttlMillis;
    }


    protected long now() {
        return System.currentTimeMillis();
    }


    public Object get(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
        CacheEntry e = findLive(key);
        if (e == null) {
            return null;
        }
        entries.moveToEnd(e);
        return e.value;
    }

    public Object put(Object key, Object value) {
        return put(key, value, defaultTtl);
    }

    public Object put(Object key, Object value, long ttlMillis) {
        if (key == null) {
            throw new NullPointerException("key must not be null" );
        }
        if (value == null) {
            throw new NullPointerException("value must not be null");
        }
        if (ttlMillis < 0) {
            throw new IllegalArgumentException("TTL must not be negative: " + ttlMillis);
        }
        long expiresAt = computeExpiry(ttlMillis);

        CacheEntry e = findLive(key);
        if (e != null) {
            Object old = e.value;
            e.value = value;
            setExpiry(e, expiresAt);
            entries.moveToEnd(e);
            return old;
        }

        if (entries.size() == capacity) {
            removeExpired();
        }
        if (entries.size() == capacity) {
            removeEntry((CacheEntry) entries.getFirst());
        }

        entries.add(new CacheEntry(key, value, expiresAt));
        if (expiresAt != NEVER) {
            expiringCount++;
        }
        return null;
    }


    public boolean containsKey(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null" );
        }
        return findLive(key) != null;
    }


    public Object remove(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null" );
        }
        CacheEntry e = findLive(key);
        if (e == null) {
            return null;
        }
        removeEntry(e);
        return e.value;
    }


    public int removeExpired() {
        if (expiringCount == 0) {
            return 0;
        }
        int removed = 0;
        Iterator<Object> it = entries.iterator();
        while (it.hasNext()) {
            CacheEntry e = (CacheEntry) it.next();
            if (isExpired(e)) {
                it.remove();
                expiringCount--;
                removed++;
            }
        }
        return removed;
    }

    public void clear() {
        entries.clear();
        expiringCount = 0;
    }


    public int size() {
        removeExpired();
        return entries.size();
    }

    public int capacity() {
        return capacity;
    }


    public Object[] keys() {
        removeExpired();
        Object[] result = new Object[entries.size()];
        int i = 0;
        for (Object o : entries) {
            result[i++] = ((CacheEntry) o).key;
        }
        return result;
    }

    @Override
    public String toString() {
        removeExpired();
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Object o : entries) {
            CacheEntry e = (CacheEntry) o;
            if (!first) sb.append(", ");
            sb.append(e.key).append('=').append(e.value);
            first = false;
        }
        return sb.append("}").toString();
    }



    private long computeExpiry(long ttlMillis) {
        if (ttlMillis == 0) {
            return NEVER;
        }
        long t = now() + ttlMillis;
        return t < 0 ? NEVER : t;
    }

    private void setExpiry(CacheEntry e, long expiresAt) {
        boolean wasFinite = e.expiresAt != NEVER;
        boolean isFinite = expiresAt != NEVER;
        if (wasFinite && !isFinite) {
            expiringCount--;
        } else if (!wasFinite && isFinite) {
            expiringCount++;
        }
        e.expiresAt = expiresAt;
    }

    private boolean isExpired(CacheEntry e) {
        return e.expiresAt != NEVER && now() >= e.expiresAt;
    }


    private CacheEntry findLive(Object key) {
        CacheEntry probe = new CacheEntry(key, null, NEVER);
        CacheEntry e = (CacheEntry) entries.get(probe);
        if (e != null && isExpired(e)) {
            removeEntry(e);
            return null;
        }
        return e;
    }

    private void removeEntry(CacheEntry e) {
        entries.remove(e);
        if (e.expiresAt != NEVER) {
            expiringCount--;
        }
    }
}