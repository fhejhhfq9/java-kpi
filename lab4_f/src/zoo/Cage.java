package zoo;

import java.util.ArrayList;
import java.util.List;

public abstract class Cage<T extends Animal> {
    private final int capacity;
    private final List<T> animals = new ArrayList<>();;

    public Cage(int capacity){
        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        this.capacity = capacity;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getOccupiedNumber(){
        return animals.size();
    }

    public void addAnimal(T  animal){
        if (animal == null) throw new IllegalArgumentException("Тварина не може бути null");
        if (animals.size() >= capacity)
            throw new IllegalStateException("Вольєр заповнений (" + capacity + " місць)");
        animals.add(animal);
    }

    public void removeAnimal(T animal){
        if (!animals.remove(animal))
            throw new IllegalArgumentException("Тварини немає у вольєрі");
    }

    public List<T> getAnimals() {
        return animals;
    }



}
