public interface MyList {
    public void add(Object e);
    public void add(int index, Object element);
    public void addAll(Object[] c);
    public void addAll(int index, Object[] c);
    public Object get(int index);
    public Object remove(int index);
    public void set(int index, Object element);
    public int indexOf(Object o);
    public int size();
    public Object[] toArray();
}
