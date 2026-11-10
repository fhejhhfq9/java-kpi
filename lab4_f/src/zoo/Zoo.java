package zoo;

import java.util.ArrayList;
import java.util.List;

public class Zoo {
    private List<Cage<? extends Animal>> cages = new ArrayList<>();

    public int getNumberOfAnimals(){
        int number = 0;
        for (Cage<? extends Animal> cage : cages) {
            number += cage.getOccupiedNumber();
        }

        return number;
    }

    public void addCage(Cage<? extends Animal> cage){
        if (cage == null) throw new IllegalArgumentException("Вольєр не може бути null");
        cages.add(cage);
    }

}

