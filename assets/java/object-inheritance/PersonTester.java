public class PersonTester {
    public static void main (String[] args) {
	
	Person alice = new Person("Alice", 23);
	Student bob = new Student("Bob", "UMass", 19);
	ACStudent eve = new ACStudent("Eve", 20);
	Employee will = new Employee("Will", "Assistant Professor", 34);

	alice.sayGreeting();
	bob.sayGreeting();
	eve.sayGreeting();
	will.sayGreeting();
    }
}
