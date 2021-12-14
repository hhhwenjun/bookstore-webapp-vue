package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private CustomerDao customerDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private Random random = new Random();

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {this.customerDao = customerDao; }

	public void setOrderDao(OrderDao orderDao) {this.orderDao = orderDao;}

	public void setLineItemDao(LineItemDao lineItemDao) { this.lineItemDao = lineItemDao; }

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private Date getDate(String monthString, String yearString) {
		int month = Integer.parseInt(monthString);
		int year = Integer.parseInt(yearString);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month);//auto goes to last day of the month
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DATE, -5);//the last day of the month -5
		Date date = calendar.getTime();

		return date;
		}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber(){ return random.nextInt(999999999);}

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

		if (ccExpiryMonth == null) return true;
		if (ccExpiryYear == null) return true;
		if (ccExpiryMonth.equals("")) return true;
		if (ccExpiryYear.equals("")) return true;

		int ccExpiryMonthNum = Integer.parseInt(ccExpiryMonth);
		int ccExpiryYearNum = Integer.parseInt(ccExpiryYear);

		int thisMonth = YearMonth.now().getMonthValue();
		int thisYear = YearMonth.now().getYear();

		if (ccExpiryMonthNum > 12 || ccExpiryMonthNum < 1) return true;

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
