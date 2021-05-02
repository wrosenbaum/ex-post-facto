import java.util.Arrays;

public class PersonTester {
    
    public static void main(String[] args) {
	Person[] aList = new Person[5];
	aList[0] = new Person("Britney", "Spears", 38);
	aList[1] = new Person("Justin", "Timberlake", 39);
	aList[2] = new Person("Jennifer", "Lopez", 51);
	aList[3] = new Person("Christina", "Aguilera", 39);
	aList[4] = new Person("Shawn", "Carter", 50);

	System.out.println("aList celebrities from the 90's:");
	System.out.println(Arrays.toString(aList));

	Arrays.sort(aList);

	System.out.println("\nNow sorted by last name:");
	System.out.println(Arrays.toString(aList));

	Arrays.sort(aList, new FirstNameComparator());

	System.out.println("\nNow sorted by first name:");
	System.out.println(Arrays.toString(aList));	

    }
}
