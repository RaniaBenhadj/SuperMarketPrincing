package supermarket.princing.model.offers.type;

import java.math.BigDecimal;

import supermarket.princing.model.Discount;
import supermarket.princing.model.Product;
import supermarket.princing.model.enums.OfferType;


public class ThreeForAmountOffer extends Offer{

	private static final String THREE_FOR = "3 for ";

	public ThreeForAmountOffer (Product product, BigDecimal amountForDiscount) {
		super(product, OfferType.THREE_FOR_AMOUNT,  amountForDiscount);
	}

	@Override
	public Discount getDiscount(BigDecimal quantity, BigDecimal unitPrice){
		int i =3;
		Discount discount = null;
		int quantityAsInt = quantity.intValue();

		if (quantityAsInt >= i) {
			BigDecimal total = this.getDiscountAmount() .multiply(new BigDecimal(quantityAsInt / i)).add(unitPrice.multiply(new BigDecimal(quantityAsInt % i)));
			BigDecimal discountAmount = unitPrice.multiply(quantity).subtract(total);
			discount = new Discount(this.getProduct(), THREE_FOR + this.getDiscountAmount(), discountAmount);
		}

		return discount;
	}
}
