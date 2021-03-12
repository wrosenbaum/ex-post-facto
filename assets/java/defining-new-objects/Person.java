/*
 * class Person: a simple object representing a person! Basic
 * functionalities include storing a name and age, celebtraing
 * birthdays, saying a greeting, and greeting another person.
 */

public class Person {

    // private instance variables
    private String name;
    private int age;

    // public class variable (constant)
    public static final String SPECIES = "Homo sapiens";

    // keep track of the total population with a private class variable
    private static int population = 0;

    // constructor
    Person (String name) {
	this.age = 0;
	this.name = name;

	System.out.println("A person named " + name + " was born!");

	population++;

	System.out.println("The population is now " + population + ".");
    }

    
    // PUBLIC INSTANCE METHODS:

    // "getters" for name and age
    public String getName () {
	return name;
    }

    public int getAge () {
	return age;
    }

    // celebrateBirthday() increments the Person's age and prints a
    // couple of messages.
    public void celebrateBirthday () {
	age++;
	System.out.println("Happy Birthday, " + name + "!!!");
	System.out.println(name + " is now " + age + " years old.");
    }

    // Print a generic greeting
    public void sayGreeting () {
	System.out.println(name + ": Hello, there!");
    }

    // Greet a particular person
    public void greet (Person p) {
	System.out.println(name + ": Hi, " + p.getName() + ".");
    }

    // PUBLIC CLASS METHOD

    // get the total population
    public static int getPopulation() {
	return population;
    }

}
