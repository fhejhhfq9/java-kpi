package zoo;

public class LionCage <T extends Lion> extends Cage<T> {

    public LionCage(int capacity){
        super(capacity);
    }
}
