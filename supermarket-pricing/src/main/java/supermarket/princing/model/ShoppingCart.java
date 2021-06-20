package supermarket.princing.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//The shopping cart is represented by HashMap, the key represents the stored product, and the value represents the quantity
public class ShoppingCart implements IShoppingCart {

	Map<Product, BigDecimal> shoppingCartMap = new HashMap<>();

	public void addToCart(Product product, BigDecimal quantity) {
		shoppingCartMap.computeIfPresent(product, (key, val) -> val.add(quantity));
		shoppingCartMap.computeIfAbsent(product, val -> quantity);
	}

	public void removeFromCart(Product product, BigDecimal quantity) {
		shoppingCartMap.computeIfPresent(product, (key, val) -> val.subtract(quantity));
		if((BigDecimal.ZERO).equals(shoppingCartMap.get(product))){
			shoppingCartMap.remove(product);	
		}
	}

	@Override
	public Map<Product, BigDecimal> getShoppingCartMap() {
		return shoppingCartMap;
	}



}

