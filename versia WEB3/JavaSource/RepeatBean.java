import java.util.ArrayList;
import java.util.List;

public class RepeatBean {
	private List<Num> numbers;
	private int row;

	public RepeatBean() {
		numbers = new ArrayList<Num>();
		numbers.add(new Num(3));
		numbers.add(new Num(8));
	}

	public void decrease() {
		Num n = numbers.get(row);
		n.setNumber(n.getNumber() - 1);
	}

	public void increase() {
		Num n = numbers.get(row);
		n.setNumber(n.getNumber() + 1);
	}

	public List<Num> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Num> numbers) {
		this.numbers = numbers;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}
