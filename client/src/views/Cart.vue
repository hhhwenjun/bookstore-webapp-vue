<template>
  <div class="cart-page">
    <section
      v-if="$store.state.cart.numberOfItems === 0"
      class="empty-cart-page"
    >
      <div style="padding: 3em 1.5em">Your cart is empty.</div>
      <router-link
        tabindex="1"
        :to="{
          name: 'category',
          params: { name: $store.state.selectedCategoryName || 'Classics' },
        }"
      >
        <button class="button continue-shop-buttons">Continue Shopping</button>
      </router-link>
    </section>
    <section v-else class="non-empty-cart-page">
      <p v-if="$store.state.cart.numberOfItems === 1">
        Your cart has 1 book in it.
      </p>
      <p v-else>
        Your cart has {{ $store.state.cart.numberOfItems }} books in it.
      </p>
      <p>
        Your total amount is
        {{ $store.state.cart.subtotal | asDollarsAndCents }}.
      </p>
      <div class="cart-buttons">
        <router-link
          tabindex="1"
          :to="{
            name: 'category',
            params: { name: $store.state.selectedCategoryName || 'Classics' },
          }"
        >
          <button class="button continue-shop-buttons">
            Continue Shopping
          </button>
        </router-link>
        <router-link to="../Checkout">
          <button class="button checkout-button">Checkout</button>
        </router-link>
      </div>
      <cart-table></cart-table>
      <button class="button link-like-button" @click="clearCart()">
        Clear the Cart</button
      ><!--tertiary-->
    </section>
  </div>
</template>

<script>
import CartTable from "../components/CartTable";

export default {
  name: "Cart",
  components: { CartTable },
  methods: {
    clearCart() {
      this.$store.dispatch("clearCart");
    },
  },
};
</script>

<style scoped>
.cart-page {
  padding: 3em;
  background-color: var(--secondary-background-color);
}

.non-empty-cart-page {
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.cart-buttons {
  display: flex;
  justify-content: space-between;
}

.checkout-button {
  max-width: 134px;
  width: 8em;
  height: 2.5em;
  border-radius: 10px;
  box-shadow: 0 5px 15px 0 rgba(0, 0, 0, 0.19);
  background-color: var(--secondary-color);
  color: var(--card-background-color);
  font-size: large;
  font-weight: bold;
  text-align: center;
  padding: 0;
  margin: 0 0.2em 0.7em 0;
}

.continue-shop-buttons {
  background-color: #2aa77a;
  color: var(--card-background-color);
  text-align: center;
  padding: 0;
  float: left;
  width: 12em;
  height: 35px;
  border-style: none;
  border-radius: 8px;
}

.continue-shop-buttons:hover {
  background-color: var(--card-background-color);
  border: solid var(--primary-color) 2px;
  color: var(--primary-color);
}

.link-like-button {
  align-self: start;
  color: var(--secondary-color);
  width: 10em;
  border-radius: 8px;
  border: solid var(--secondary-color) 2px;
  font-family: "Nunito", sans-serif;
  font-weight: bold;
  text-align: center;
  font-size: 13px;
  padding: 0.1em;
  margin: 1em 0;
}

.button.checkout-button:hover {
  background-color: purple;
  color: var(--secondary-background-color);
}

.link-like-button:hover,
.link-like-button:active {
  background: lightgray;
}
</style>
