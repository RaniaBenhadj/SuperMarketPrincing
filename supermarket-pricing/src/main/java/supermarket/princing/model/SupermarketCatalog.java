package supermarket.princing.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import supermarket.princing.model.enums.OfferType;
import supermarket.princing.model.offers.type.Offer;
import supermarket.princing.model.offers.type.ThreeForAmountOffer;
import supermarket.princing.model.offers.type.ThreeForTwoOffer;

//The Supermarket catalog is represented by HashMap, the key represents the product, and the value represents the unit price
public class SupermarketCatalog  implements ISupermarketCatalog{

	private Map<Product, BigDecimal> catalogMap = new HashMap<>();
	private Map<Product, Offer> offers = new HashMap<>();

	public void addProduct(Product product, BigDecimal unitPrice) {
		this.catalogMap.put(product, unitPrice);
	}

	public void removeProduct(Product product) {
		this.catalogMap.remove(product);
	}

	@Override
	public BigDecimal getUnitPrice(Product product) {
		return this.catalogMap.get(product);
	}

	@Override
	public Map<Product, Offer> getOffers() {
		return offers;
	}

	@Override
	public void addOffer(OfferType offerType, Product product, BigDecimal discountPrice) {
		if (offerType == OfferType.THREE_FOR_AMOUNT) {
			this.offers.put(product, new ThreeForAmountOffer(product, discountPrice));
		}else if (offerType == OfferType.THREE_FOR_TWO)  {
			this.offers.put(product, new ThreeForTwoOffer(product, discountPrice));
		}
	}


}
