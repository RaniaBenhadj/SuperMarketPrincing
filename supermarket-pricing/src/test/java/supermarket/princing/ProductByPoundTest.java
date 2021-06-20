package supermarket.princing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import supermarket.princing.management.IPaymentCalculator;
import supermarket.princing.management.PaymentCalculator;
import supermarket.princing.model.Discount;
import supermarket.princing.model.Product;
import supermarket.princing.model.Receipt;
import supermarket.princing.model.ShoppingCart;
import supermarket.princing.model.SupermarketCatalog;
import supermarket.princing.model.enums.ProductUnit;

class ProductByPoundTest{

	private SupermarketCatalog catalog = new SupermarketCatalog();
	private Product canOfBeans = new Product("canOfBeans", ProductUnit.EACH);
	private Product apple = new Product("apple", ProductUnit.POUND);
	private ShoppingCart shoppingCart = new ShoppingCart();
	private IPaymentCalculator supermarketManager = new PaymentCalculator(catalog);

	@BeforeEach
	void setup() {
		catalog.addProduct(apple, new BigDecimal(2.0));
		catalog.addProduct(canOfBeans, new BigDecimal(200.0));	
	}


	@Test
	void testNominalProductByPound() {
		shoppingCart.addToCart(apple, new BigDecimal(4));        
		Receipt Receipt = supermarketManager.generateReceipt(shoppingCart);
		BigDecimal expectedResult = new BigDecimal(0.5).setScale(2, RoundingMode.CEILING);
		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
	}

	@Test
	void testReceiptWithTwoTypesOfProduct() {
		shoppingCart.addToCart(apple, new BigDecimal(4));
		shoppingCart.addToCart(canOfBeans, new BigDecimal(3)); 
		Receipt Receipt = supermarketManager.generateReceipt(shoppingCart);
		List<Discount> discounts = Receipt.getDiscountList();
		BigDecimal expectedResult = new BigDecimal(600.50).setScale(2, RoundingMode.CEILING);

		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
		Assertions.assertTrue(discounts.isEmpty());
	}

}
