package business.order;

import api.ApiException;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.customer.CustomerForm;

import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Date;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		// NOTE: THIS METHOD PROVIDED NEXT PROJECT
		return null;
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		// NOTE: MORE CODE PROVIDED NEXT PROJECT

		return -1;
	}


	private void validateCustomer(CustomerForm customerForm) {

		if (!nameIsValid(customerForm.getName())) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		if (!phoneIsValid(customerForm.getPhone())){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		if (!addressIsValid(customerForm.getAddress())){
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		if (!emailIsValid(customerForm.getEmail())){
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		if (!ccNumberIsValid(customerForm.getCcNumber())){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");

		}
	}

	private boolean ccNumberIsValid(String ccNumber){

		if (ccNumber == null) return false;
		if (ccNumber.equals("")) return false;

		ccNumber = ccNumber.replaceAll(" ", "");
		ccNumber = ccNumber.replaceAll("-", "");

		if (ccNumber.length() > 16) return false;
		if (ccNumber.length() < 14) return false;

		return true;
	}

	private boolean emailIsValid(String email){

		if (email == null) return false;
		if (email.equals("")) return false;
		if (email.contains(" ")) return false;
		if (!email.contains("@")) return false;
		if (email.endsWith(".")) return false;

		return true;
	}

	private boolean addressIsValid(String address){

		if (address == null) return false;
		if (address.equals("")) return false;
		if (address.length() < 4) return false;
		if (address.length() > 45) return false;

		return true;
	}

	private boolean phoneIsValid(String phone) {

		if (phone == null) return false;
		if (phone.equals("")) return false;

		// get rid of parens, spaces, and dashes
		phone = phone.replaceAll(" ", "");
		phone = phone.replaceAll("-", "");
		phone = phone.replaceAll("\\(", "");
		phone = phone.replaceAll("\\)", "");

		// should have a number (no letters) with exactly 10 digits
		if (!phone.matches("[\\d]+")) return false;
		if (phone.length() != 10) return false;

		return true;
	}


	private boolean nameIsValid(String name) {
		if (name == null) return false;
		if (name.equals("")) return false;
		if (name.length() < 4) return false;
		if (name.length() > 45) return false;

		return true;
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		int ccExpiryMonthNum = Integer.parseInt(ccExpiryMonth);
		int ccExpiryYearNum = Integer.parseInt(ccExpiryYear);

		int thisMonth = YearMonth.now().getMonthValue();
		int thisYear = YearMonth.now().getYear();

		if (ccExpiryMonthNum < thisMonth && ccExpiryYearNum == thisYear) return true;
		if (ccExpiryYearNum < thisYear) return true;

		return false;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());

			if (item.getBookPrice() != databaseBook.getPrice()){
				throw new ApiException.InvalidParameter("Invalid book price");
			}

			if (item.getBookCategoryId() != databaseBook.getCategoryId()){
				throw new ApiException.InvalidParameter("Invalid book category");
			}
		});
	}

}
