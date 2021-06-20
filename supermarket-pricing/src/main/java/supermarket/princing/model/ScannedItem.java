package supermarket.princing.model;

import java.math.BigDecimal;

public class ScannedItem {

	private final Product product;
	private final BigDecimal quantity;
	private final BigDecimal unitPrice;
	private BigDecimal totalPrice;


	public ScannedItem(Product p, BigDecimal quantity, BigDecimal price, BigDecimal totalPrice) {
		this.product = p;
		this.quantity = quantity;
		this.unitPrice = price;
		this.totalPrice = totalPrice;
	}

	public Product getProduct() {
		return product;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

}
