public class Employee extends Person {
  private String title;

  public Employee (String name, String title, int age) {
    super(name, age);
    this.title = title;
  }

  public void getPaid(int amount) {
    System.out.println(getName() + ": I just got paid $" + amount + "!!!");
  }

  @Override
  public void sayGreeting () {
    System.out.println(getName() + ": Hello everyone! I'm a " + title + "!");
  }
} 
