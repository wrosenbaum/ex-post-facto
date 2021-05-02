import java.util.Comparator;

public class FirstNameComparator implements Comparator<Person> {
    public int compare (Person p, Person q) {
	return p.getFirstName().compareTo(q.getFirstName());
    }
}
