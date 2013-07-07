import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

public class OrderBean {
	private List<Order1> orders = new ArrayList<Order1>();

	public OrderBean() {
		super();
	}
	
//	@PostConstruct
//	public void init() {
//		ArrayList<OrderDetail> odal1 = new ArrayList<OrderDetail>();
//		odal1.add(new OrderDetail("2010-01-31",
//				"detail 1", 100));
//		odal1.add(new OrderDetail("2010-02-20",
//				"detail 2", 50));
//		odal1.add(new OrderDetail("2010-03-30", "third",
//				3000));
//		this.orders.add(new Order1(1, "first order", odal1));
//		
//		ArrayList<OrderDetail> odal2 = new ArrayList<OrderDetail>();
//		odal2.add(new OrderDetail("2010-01-15",
//				"second order d1", 123));
//		odal2.add( new OrderDetail("2010-02-24",
//				"second order d2", 1234));
//		
//		this.orders.add(new Order1(2, "second order", odal2));
//	}

	public List<Order1> getOrders() {
		return orders;
	}

	public void setOrders(List<Order1> orders) {
		this.orders = orders;
	}

}
