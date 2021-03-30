public class Person {
    private String name;
    private int age;
  
    public Person (String name, int age) {
	this.name = name;
	this.age = age;
    }

    public String getName () {return name;}
    public int getAge () {return age;}

    public void sayGreeting () {
	System.out.println(name + ": Hello there!");
    }

    public void greet (Person p) {
	System.out.println(name + ": Hello, " + p.getName() + "!");
    }
}
