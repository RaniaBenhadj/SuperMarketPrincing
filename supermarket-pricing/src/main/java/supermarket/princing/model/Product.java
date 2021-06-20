package supermarket.princing.model;

import java.util.Objects;

import supermarket.princing.model.enums.ProductUnit;

public class Product {

	private final String name;
	private final ProductUnit unit;

	public Product(String name, ProductUnit unit) {
		this.name = name;
		this.unit = unit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the unit
	 */
	public ProductUnit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "product with unit "+ name + " " + unit.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(name, product.name) &&
				unit == product.unit;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, unit);
	}
}
