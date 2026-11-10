package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import zoo.*;


public class ZooTest {
    @Test
    public void testLionCage(){
        LionCage cage = new LionCage(2);

        Lion lion1 = new Lion("Lion1");
        Lion lion2 = new Lion("Lion2");

        cage.addAnimal(lion1);
        cage.addAnimal(lion2);

        assertEquals(2, cage.getOccupiedNumber());
        assertEquals(2, cage.getCapacity());

        assertThrows(IllegalStateException.class,() -> cage.addAnimal(new Lion("Lion3")));

        cage.removeAnimal(lion1);
        assertEquals(1, cage.getOccupiedNumber());
    }

    @Test
    public void testBirdCage(){
        BirdCage cage = new BirdCage(3);

        Eagle eagle1 = new Eagle("eagle1");
        Eagle eagle2 = new Eagle("eagle2");
        Eagle eagle3 = new Eagle("eagle3");

        cage.addAnimal(eagle1);
        cage.addAnimal(eagle2);
        cage.addAnimal(eagle3);

        assertEquals(2, cage.getOccupiedNumber());
        assertEquals(2, cage.getCapacity());

        assertThrows(IllegalStateException.class,() -> cage.addAnimal(new Eagle("eagle4")));

        cage.removeAnimal(eagle1);
        assertEquals(2, cage.getOccupiedNumber());


    }

    @Test
    public void testUngulateCage(){
        UngulateCage cage = new UngulateCage(2);

        Zebra zebra1 = new Zebra("zebra1");
        Giraffe giraffe1 = new Giraffe("giraffe1");

        cage.addAnimal(zebra1);
        cage.addAnimal(giraffe1);

        assertEquals(2, cage.getOccupiedNumber());
        assertEquals(2, cage.getCapacity());

        assertThrows(IllegalStateException.class,() -> cage.addAnimal(new Zebra("zebra2")));

        cage.removeAnimal(zebra1);
        assertEquals(1, cage.getOccupiedNumber());


    }

    @Test
    public void testRemoveMissingAnimal(){
        LionCage cage = new LionCage(2);
        assertThrows(IllegalArgumentException.class,() -> cage.removeAnimal(new Lion("Lion")));
    }

    @Test
    public void testZooCountOfAnimals(){
        Zoo zoo = new Zoo();
        LionCage lions = new LionCage(2);
        UngulateCage ungulates = new UngulateCage(2);
        BirdCage birds = new BirdCage(2);

        zoo.addCage(lions);
        zoo.addCage(ungulates);
        zoo.addCage(birds);
        assertEquals(0, zoo.getNumberOfAnimals());

        lions.addAnimal(new Lion("Lion1"));
        ungulates.addAnimal(new Zebra("zebra1"));
        ungulates.addAnimal(new Giraffe("giraffe1"));
        birds.addAnimal(new Eagle("eagle1"));

        assertEquals(4, zoo.getNumberOfAnimals());
    }



}
