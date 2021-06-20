package supermarket.princing.model.offers.type;

import java.math.BigDecimal;

import supermarket.princing.model.Discount;
import supermarket.princing.model.Product;
import supermarket.princing.model.enums.OfferType;


public class ThreeForTwoOffer extends Offer{

	private static final String THREE_FOR_TWO = "3 for 2";

	public ThreeForTwoOffer(Product product, BigDecimal discountAmount) {
		super(product, OfferType.THREE_FOR_TWO, discountAmount);
	}

	@Override
	public Discount getDiscount(BigDecimal quantity, BigDecimal unitPrice) {
		Discount discount = null;
		int quantityAsInt = quantity.intValue();
		int i =3;
		int j =2;
		int numberOfXs = quantityAsInt / i;

		if (quantityAsInt >= i) {

			BigDecimal discountAmount = quantity.multiply(unitPrice).subtract(unitPrice.multiply(new BigDecimal(numberOfXs * j)).add(unitPrice.multiply( new BigDecimal(quantityAsInt % i ))));
			discount = new Discount(this.getProduct(), THREE_FOR_TWO, discountAmount);
		}

		return discount;
	}


}
