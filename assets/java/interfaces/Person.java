public class Person implements Comparable<Person> {
    
    private String firstName;
    private String lastName;
    private int age;

    Person (String firstName, String lastName, int age) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.age = age;
    }

    public String getFirstName () { return firstName; }
    public String getLastName () { return lastName; }
    public int getAge () { return age; }

    public String toString () {
	return firstName + " " + lastName + " (" + age + ")";
    }

    public int compareTo (Person p) {
	return lastName.compareTo(p.lastName);
    }
}
