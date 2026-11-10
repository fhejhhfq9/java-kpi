package zoo;

public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        LionCage lions = new LionCage(2);
        UngulateCage ungulates = new UngulateCage(3);
        BirdCage<Eagle> birds = new BirdCage<>(2);
        zoo.addCage(lions); zoo.addCage(ungulates); zoo.addCage(birds);

        lions.addAnimal(new Lion("lion1"));
        ungulates.addAnimal(new Zebra("zebra1"));
        ungulates.addAnimal(new Giraffe("giraffe1"));
        birds.addAnimal(new Eagle("eagle"));

        System.out.println("Тварин у зоопарку: " + zoo.getNumberOfAnimals());
    }
}