import java.util.Date;

public class OrderDetail {
	private String date;
	private String detail;
	private int quantity;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public OrderDetail(String date, String detail, int quantity) {
		super();
		this.date = date;
		this.detail = detail;
		this.quantity = quantity;
	}
}
