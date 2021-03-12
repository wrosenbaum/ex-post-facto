/*
 * Dialog.java: a simple program to test the Person class
 */

public class Dialog {
    public static void main(String[] args) {

	// create two new people, named Alice and Bob
	Person alice = new Person("Alice");
	Person bob = new Person("Bob");

	// Alice and Bob celebrate their birthdays
	alice.celebrateBirthday();
	bob.celebrateBirthday();

	// Alice and Bob meet and greet each other
	alice.sayGreeting();
	bob.greet(alice);

	System.out.println("(end scene)");

	System.out.println("\nThere were " + Person.getPopulation() + " characters in this dialog.");

	System.out.println("\nNote that all characters in this dialog belong to the species "
			   + Person.SPECIES + ".");
	
    }
}
