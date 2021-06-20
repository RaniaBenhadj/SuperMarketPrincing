package supermarket.princing.model.offers.type;

import java.math.BigDecimal;

import supermarket.princing.model.Discount;
import supermarket.princing.model.Product;
import supermarket.princing.model.enums.OfferType;


public abstract class Offer {

	private final Product product;
	private OfferType offerType;
	private BigDecimal discountAmount;

	protected Offer(Product product, OfferType offerType, BigDecimal discountAmount) {
		this.product = product;
		this.offerType = offerType;
		this.discountAmount = discountAmount;

	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return the offerType
	 */
	public OfferType getOfferType() {
		return offerType;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public abstract Discount getDiscount(BigDecimal quantity, BigDecimal unitPrice);

}
