<template>
  <div class="cart-table">
    <ul>
      <li class="cart-heading">
        <div class="cart-heading-book">Book</div>
        <div class="cart-heading-quantity">Quantity</div>
        <div class="cart-heading-subtotal">Amount</div>
      </li>

      <li v-for="item in $store.state.cart.items" :key="item.book.bookId">
        <div class="cart-book-image">
          <img
            :src="
              require('@/assets/images/books/' + bookImageFileName(item.book))
            "
            :alt="item.book.title"
            style="width: 100px; height: auto"
          />
        </div>
        <div class="cart-book-title">{{ item.book.title }}</div>
        <div class="cart-book-price">
          {{ item.book.price | asDollarsAndCents }}
        </div>
        <div class="cart-book-quantity">
          <button
            class="icon-button dec-button"
            @click="updateCart(item.book, item.quantity - 1)"
          >
            <i class="fas fa-minus-circle"></i>
          </button>
          <span class="row-book-quantity">{{ item.quantity }}</span>

          <button
            class="icon-button inc-button"
            @click="updateCart(item.book, item.quantity + 1)"
          >
            <i class="fas fa-plus-circle"></i>
          </button>
        </div>
        <div class="subtotal">
          {{ (item.quantity * item.book.price) | asDollarsAndCents }}
        </div>
        <!--price times quantity-->
      </li>
      <div class="row-sep"></div>
      <li>
        <div class="cart-heading-book">Total</div>
        <div class="cart-book-quantity" style="text-align: center">
          {{ $store.state.cart.numberOfItems }}
        </div>
        <div class="cart-book-price">
          {{ $store.state.cart.subtotal | asDollarsAndCents }}
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "CartTable",
  methods: {
    bookImageFileName(book) {
      let bookName = book.title;
      return `${bookName}.png`;
    },
    updateCart(book, quantity) {
      /*two arguments, pass them to locations*/
      this.$store.dispatch("updateCart", { book, quantity });
    },
  },
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content); /*repeat 5 times, auto width*/
  row-gap: 1em;
  padding: 1em;
  background-color: var(--secondary-background-color);
  margin: 0 auto; /*center*/
}

ul,
li {
  display: contents;
}

.row-sep {
  grid-column: 1 / -1;
  background-color: gray;
  height: 2px;
}

.cart-heading {
  background-color: var(--primary-color);
  color: var(--secondary-background-color);
}

.cart-heading > * {
  /* any immediate child of heading should have */
  background-color: var(--primary-color);
  color: var(--secondary-background-color);
}

.cart-heading-book {
  grid-column: 1 / 4;
  padding: 0 1em;
}

.cart-heading-quantity {
  grid-column: 4 / 5;
  padding: 0 1em;
  text-align: center;
}

.cart-heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
  padding: 0 1em;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-title {
  padding: 0 1em;
}

.cart-book-price {
  text-align: right;
  padding: 0 1em;
}

.cart-book-quantity {
  padding: 0 1em;
}

.subtotal {
  text-align: right;
  padding: 0 1em;
}

.icon-button {
  color: var(--primary-color);
  font-size: 1rem;
  background-color: transparent;
  border: none;
  font-size: x-small;
}

.icon-button:hover {
  color: purple;
  cursor: pointer;
}

.inc-button {
  margin-left: 0.3rem;
  margin-right: 0.3rem;
}

.dec-button {
  margin-left: 0.3rem;
  margin-right: 0.3rem;
}
</style>
