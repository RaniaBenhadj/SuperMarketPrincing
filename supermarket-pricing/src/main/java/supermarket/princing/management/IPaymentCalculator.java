package supermarket.princing.management;

import supermarket.princing.model.IShoppingCart;
import supermarket.princing.model.Receipt;

public interface IPaymentCalculator {

	// generate the client receipt 
	public Receipt generateReceipt(IShoppingCart cart);
	
}
