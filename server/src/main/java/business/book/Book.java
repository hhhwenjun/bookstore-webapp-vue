package business.book;

public class Book {

	private final long bookId;
	private final String title;
	private final String author;
	private final String description;
	private final int price;
	private final int rating;
	private final boolean isPublic;
	private final boolean isFeatured;
	private final long categoryId;

	/*
	* Constructor of the book
	* */
	public Book(long bookId, String title, String author, String description, int price, int rating, boolean isPublic, boolean isFeatured, long categoryId) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.isPublic = isPublic;
		this.isFeatured = isFeatured;
		this.categoryId = categoryId;
	}

	/*
	* getter for Book id
	* */
	public long getBookId() {
		return bookId;
	}

	/*
	 * getter for title
	 * */
	public String getTitle() {
		return title;
	}

	/*
	 * getter for author
	 * */
	public String getAuthor() {
		return author;
	}

	/*
	 * getter for description
	 * */
	public String getDescription() {
		return description;
	}

	/*
	 * getter for price
	 * */
	public int getPrice() {
		return price;
	}

	/*
	 * getter for rating
	 * */
	public int getRating() {
		return rating;
	}

	/*
	 * getter for public status
	 * */
	public boolean getIsPublic() {
		return isPublic;
	}

	/*
	 * getter for feature status
	 * */
	public boolean getIsFeatured() {
		return isFeatured;
	}

	/*
	 * getter for category id
	 * */
	public long getCategoryId() {
		return categoryId;
	}

	/*
	* toString method for the book information
	* */
	@Override
	public String toString() {
		return "Book{" +
				"bookId=" + bookId +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", rating=" + rating +
				", getIsPublic=" + isPublic +
				", getIsFeatured=" + isFeatured +
				", categoryId=" + categoryId +
				'}';
	}
}
