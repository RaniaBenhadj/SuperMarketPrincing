package supermarket.princing.management;

import java.math.BigDecimal;
import java.util.Map.Entry;

import supermarket.princing.model.Discount;
import supermarket.princing.model.IShoppingCart;
import supermarket.princing.model.ISupermarketCatalog;
import supermarket.princing.model.Product;
import supermarket.princing.model.Receipt;
import supermarket.princing.model.offers.type.Offer;

public class PaymentCalculator implements IPaymentCalculator {

	private ISupermarketCatalog catalog ;

	public PaymentCalculator(ISupermarketCatalog catalog) {
		this.catalog = catalog;
	}

	@Override
	public Receipt generateReceipt(IShoppingCart cart) {
		Receipt receipt = new Receipt();
		cart.getShoppingCartMap().entrySet().stream().forEach(entry -> addToReceiptItems(receipt, entry));
		applyDiscountToReceipt(cart, receipt, this.catalog);
		return receipt;
	}

	/**
	 * @param receipt
	 * @param entry
	 */
	private void addToReceiptItems(Receipt receipt, Entry<Product, BigDecimal> entry) {
		Product product = entry.getKey();
		BigDecimal price ;
		BigDecimal quantity = entry.getValue();
		BigDecimal unitPrice = this.catalog.getUnitPrice(product);
		price = quantity.multiply(unitPrice);
		receipt.addToScannedItemList(product, quantity, unitPrice, price);
	}

	// applying discount to client Receipt consist in adding the discount to receipt discount list
	private void applyDiscountToReceipt(IShoppingCart cart, Receipt receipt, ISupermarketCatalog catalog ) {
		cart.getShoppingCartMap().keySet().stream().filter(x -> catalog.getOffers().containsKey(x)).forEach(product ->
		addToReceiptDiscounts(cart, receipt, catalog, product));
	}

	/**
	 * @param cart
	 * @param receipt
	 * @param catalog
	 * @param productOffers
	 * @param product
	 */
	private void addToReceiptDiscounts(IShoppingCart cart, Receipt receipt, ISupermarketCatalog catalog, Product product) {
		BigDecimal quantity = cart.getShoppingCartMap().get(product);
		Offer offer = catalog.getOffers().get(product);
		BigDecimal unitPrice = catalog.getUnitPrice(product);
		Discount discount = offer.getDiscount(quantity, unitPrice);
		if (discount != null)
			receipt.addToDiscountList(discount);
	}
}
