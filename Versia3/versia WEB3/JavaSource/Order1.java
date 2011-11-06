import java.util.ArrayList;
import java.util.List;

public class Order1 {
	public Order1(int id, String name, List<OrderDetail> details) {
		super();
		Id = id;
		Name = name;
		this.details = details;
	}
	private int Id;
	private String Name;
	private List<OrderDetail> details = new ArrayList<OrderDetail>();
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<OrderDetail> getDetails() {
		return details;
	}
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}
	
}