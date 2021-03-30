public class Student extends Person {
    private String institution;
  
    public Student (String name, String institution, int age) {
	super(name, age);    // call the Person constructor with args name and age
	this.institution = institution;
    }

    public String getInstitution () {return institution;}

    @Override
    public void sayGreeting () {
	System.out.println(getName() + ": Can't talk now... I'm on my way to " + institution);
    }

    @Override
    public void greet (Person s) {
	System.out.println(getName() + ": Hey, " + s.getName() + "! Can we talk after the exam?");
    }
}
