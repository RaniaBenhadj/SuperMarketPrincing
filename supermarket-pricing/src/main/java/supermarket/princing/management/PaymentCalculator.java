package supermarket.princing.management;

import java.math.BigDecimal;
import java.util.Map.Entry;

import supermarket.princing.model.Discount;
import supermarket.princing.model.IShoppingCart;
import supermarket.princing.model.ISupermarketCatalog;
import supermarket.princing.model.Product;
import supermarket.princing.model.Receipt;
import supermarket.princing.model.enums.ProductUnit;
import supermarket.princing.model.offers.type.Offer;

public class PaymentCalculator implements IPaymentCalculator {

	private static final int OUNCES_PER_POUND = 16;

	private ISupermarketCatalog catalog ;

	public PaymentCalculator(ISupermarketCatalog catalog) {
		this.catalog = catalog;
	}

	/** generate the receipt 
	 * @param cart
	 * return receipt
	 */
	@Override
	public Receipt generateReceipt(IShoppingCart cart) {
		Receipt receipt = new Receipt();
		cart.getShoppingCartMap().entrySet().stream().forEach(entry -> addToReceiptItems(receipt, entry));
		applyDiscountToReceipt(cart, receipt, this.catalog);
		return receipt;
	}

	/** add scanned item to receipt scanned item list
	 * @param receipt
	 * @param entry
	 */
	private void addToReceiptItems(Receipt receipt, Entry<Product, BigDecimal> entry) {
		Product product = entry.getKey();
		BigDecimal price;
		BigDecimal quantity = entry.getValue();
		BigDecimal unitPrice = this.catalog.getUnitPrice(product);
		if (ProductUnit.EACH.equals(product.getUnit()))
			price = quantity.multiply(unitPrice);
		else {
			price = quantity.divide(new BigDecimal(OUNCES_PER_POUND)).multiply(unitPrice);
		}
		receipt.addToScannedItemList(product, quantity, unitPrice, price);
	}

	/** apply discount to client Receipt consist in adding the discount to receipt discount list
	 * @param cart
	 * @param receipt
	 * @param catalog
	 */ 
	private void applyDiscountToReceipt(IShoppingCart cart, Receipt receipt, ISupermarketCatalog catalog ) {
		cart.getShoppingCartMap().keySet().stream().filter(x -> catalog.getOffers().containsKey(x)).forEach(product ->
		addToReceiptDiscounts(cart, receipt, catalog, product));
	}

	/** add discount to receipt discount list
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
