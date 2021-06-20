package supermarket.princing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import supermarket.princing.management.PaymentCalculator;
import supermarket.princing.management.IPaymentCalculator;
import supermarket.princing.model.Discount;
import supermarket.princing.model.Product;
import supermarket.princing.model.Receipt;
import supermarket.princing.model.ShoppingCart;
import supermarket.princing.model.SupermarketCatalog;
import supermarket.princing.model.enums.OfferType;
import supermarket.princing.model.enums.ProductUnit;

//JUnit5 test classes can have any visibility but private, 
//however, it is recommended to use the default package visibility, which improves readability of code.
class OfferTypesTest{

	private SupermarketCatalog catalog = new SupermarketCatalog();
	private Product canOfBeans = new Product("canOfBeans", ProductUnit.EACH);
	private ShoppingCart shoppingCart = new ShoppingCart();
	private IPaymentCalculator paymentCalculator = new PaymentCalculator(catalog);

	@BeforeEach
	void setup() {
		catalog.addProduct(canOfBeans, new BigDecimal(200.0));	
	}

	@ParameterizedTest
	@CsvSource({
		"3, 200.0, 400.0",   // test three for two offer
		"1, 200.0, 200.0",  // test three for two offer if quantity less than three
		"4, 200.0, 600.0"  // test three for two offer if quantity more than three
	})
	void testThreeForTwo(String a, String b, String c){
		Receipt Receipt = setContextInformation(new BigDecimal(a), OfferType.THREE_FOR_TWO, new BigDecimal(b),catalog);
		BigDecimal expectedResult = new BigDecimal(c).setScale(2, RoundingMode.CEILING);
		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
	}

	@ParameterizedTest
	@CsvSource({
		"3, 500.0, 500.0",   // test three for amount offer
		"1, 500.0, 200.0",  // test three for amount offer if quantity less than three
		"4, 500.0, 700.0"  // test three for amount offer if quantity more than three
	})
	void testThreeForAmount(String a, String b, String c){
		Receipt Receipt = setContextInformation(new BigDecimal(a), OfferType.THREE_FOR_AMOUNT, new BigDecimal(b), catalog);
		BigDecimal expectedResult = new BigDecimal(c).setScale(2, RoundingMode.CEILING);
		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
	}

	@Test
	void testWithoutDiscount() {
		shoppingCart.addToCart(canOfBeans, new BigDecimal(5));        
		Receipt Receipt = paymentCalculator.generateReceipt(shoppingCart);
		List<Discount> discounts = Receipt.getDiscountList();
		BigDecimal expectedResult = new BigDecimal(1000.0).setScale(2, RoundingMode.CEILING);

		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
		Assertions.assertTrue(discounts.isEmpty());
	}

	@Test
	void testWithoutDiscountWhileRemovingProduct() {
		shoppingCart.addToCart(canOfBeans, new BigDecimal(5)); 
		shoppingCart.removeFromCart(canOfBeans, new BigDecimal(2));
		Receipt Receipt = paymentCalculator.generateReceipt(shoppingCart);
		List<Discount> discounts = Receipt.getDiscountList();
		BigDecimal expectedResult = new BigDecimal(600.0).setScale(2, RoundingMode.CEILING);

		Assertions.assertEquals(expectedResult, Receipt.getPaymentAmount());
		Assertions.assertTrue(discounts.isEmpty());
	}


	private Receipt setContextInformation(BigDecimal quantity, OfferType offerType, BigDecimal discountPrice, SupermarketCatalog catalog) {
		shoppingCart.addToCart(canOfBeans, quantity);
		catalog.addOffer(offerType, canOfBeans, discountPrice);
		Receipt Receipt = paymentCalculator.generateReceipt(shoppingCart);
		return Receipt;
	}
}
