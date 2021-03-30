public class ACStudent extends Student {
    public ACStudent (String name, int age) {
	super(name, "Amherst College", age);
    }

    @Override
    public void sayGreeting () {
	System.out.println(getName() + ": Can't talk now... I'm on my way to Val.");
    }
  
    @Override
    public void greet (Person s) {
	super.greet(s);
	System.out.println("Go Mammoths!");
    }
  
}
