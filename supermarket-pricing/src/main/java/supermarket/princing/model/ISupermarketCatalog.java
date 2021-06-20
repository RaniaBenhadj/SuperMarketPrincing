package supermarket.princing.model;

import java.math.BigDecimal;
import java.util.Map;

import supermarket.princing.model.enums.OfferType;
import supermarket.princing.model.offers.type.Offer;

public interface ISupermarketCatalog {

	BigDecimal getUnitPrice(Product product);
	
	public Map<Product, Offer> getOffers();

	void addOffer(OfferType offerType, Product product, BigDecimal discountPrice);

}
