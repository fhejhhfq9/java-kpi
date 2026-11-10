package zoo;

public class MammalCage<T extends Mammal> extends Cage<T> {

    public MammalCage(int capacity) {
        super(capacity);
    }
}