package supermarket.princing.model;

import java.math.BigDecimal;

public class Discount {

	private final Product product;
	private final String description;
	private final BigDecimal discountAmount;

	public Discount(Product product, String description, BigDecimal discountAmount) {
		this.product = product;
		this.description = description;
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}


}
