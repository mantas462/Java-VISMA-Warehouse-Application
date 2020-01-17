package BackEnd;

import java.util.Comparator;

public class SortByProduct implements Comparator<Product> {
	@Override
	public int compare(Product a, Product b) {
		return a.getProduct().compareTo(b.getProduct());
	}
}