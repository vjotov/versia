import java.util.ArrayList;

public class Second {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Integer> attachedVoID = new ArrayList<Integer>();
		attachedVoID.add(new Integer(2435));
		attachedVoID.add(new Integer(2));
		attachedVoID.add(new Integer(4));
		attachedVoID.add(new Integer(10));

		if (attachedVoID.contains(new Integer(10)))
			System.out.println("yes");
		else
			System.out.println("no");

	}

}
