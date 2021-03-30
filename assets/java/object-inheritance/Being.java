public abstract class Being {
    private String name;
    private int age;

    public Being(String name, int age) {
	this.name = name;
	this.age = age;
    }

    public String getName () { return name; }
    public int getAge () { return age; }

    // a method declaration without an implementation
    public abstract String sayGreeting ();
}
