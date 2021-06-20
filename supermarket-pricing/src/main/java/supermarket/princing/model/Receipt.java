package supermarket.princing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

	private List<Discount> discountList = new ArrayList<>();
	private List<ScannedItem> scannedItemList = new ArrayList<>();

	public void addToDiscountList(Discount discount) {
		this.discountList.add(discount);
	}

	public List<Discount> getDiscountList() {
		return discountList;
	}
	public void addToScannedItemList(Product p, BigDecimal quantity, BigDecimal price, BigDecimal totalPrice) {
		this.scannedItemList.add(new ScannedItem(p, quantity, price, totalPrice));
	}

	public List<ScannedItem> getscannedItemList() {
		return new ArrayList<>(this.scannedItemList);
	}

	// calculate the payment amount
	public BigDecimal getPaymentAmount() {
		BigDecimal paymentAmount = BigDecimal.ZERO;
		paymentAmount=paymentAmount.add(scannedItemList.stream().map(ScannedItem :: getTotalPrice)
				.reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO));
		paymentAmount=paymentAmount.subtract(discountList.stream().map(Discount :: getDiscountAmount)
				.reduce((a, b) -> a.subtract(b)).orElse(BigDecimal.ZERO));
		paymentAmount=paymentAmount.setScale(2, RoundingMode.CEILING);
		return paymentAmount;
	}



}
