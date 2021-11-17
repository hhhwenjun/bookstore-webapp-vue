<template>
  <div class="checkout-page">
    <section
      v-if="$store.state.cart.numberOfItems === 0"
      class="empty-cart-page"
    >
      <div style="padding: 3em 1.5em; display: flex; flex-direction: column">
        <p style="padding-bottom: 3em">
          Your cart is empty. Please add items to your cart to checkout.
        </p>
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
      </div>
    </section>
    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div>
          <label for="name">Name</label>
          <input
            type="text"
            size="20"
            id="name"
            name="name"
            v-model.lazy="$v.name.$model"
          />
        </div>
        <template v-if="$v.name.$error">
          <span class="error" v-if="!$v.name.required">Name is required</span>
          <span class="error" v-else-if="!$v.name.minLength">
            Name must have at least
            {{ $v.name.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.name.maxLength">
            Name can have at most
            {{ $v.name.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else> An unexpected error occurred. </span>
        </template>

        <div>
          <label for="address">Address</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="address"
            name="address"
            v-model.lazy="$v.address.$model"
          />
        </div>
        <template v-if="$v.address.$error">
          <span class="error" v-if="!$v.address.required"
            >Address is required</span
          >
          <span class="error" v-else-if="!$v.address.minLength">
            Address must have at least
            {{ $v.name.$params.minLength.min }} letters.
          </span>
          <span class="error" v-else-if="!$v.address.maxLength">
            Address can have at most
            {{ $v.name.$params.maxLength.max }} letters.
          </span>
          <span class="error" v-else> An unexpected error occurred. </span>
        </template>

        <div>
          <label for="phone">Phone</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="phone"
            name="phone"
            v-model.lazy="$v.phone.$model"
          />
        </div>
        <template v-if="$v.phone.$error">
          <span class="error" v-if="!$v.phone.required">Phone is required</span>
          <span class="error" v-else-if="!$v.phone.phone"
            >Please enter a valid phone number</span
          >
        </template>

        <div>
          <label for="email">Email</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="email"
            name="email"
            v-model.lazy="$v.email.$model"
          />
        </div>
        <template v-if="$v.email.$error">
          <span class="error" v-if="!$v.email.required">Email is required</span>
          <span class="error" v-else-if="!$v.email.email"
            >Please enter a valid email address</span
          >
        </template>

        <div>
          <label for="ccNumber">Credit card</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="ccNumber"
            name="ccNumber"
            v-model.lazy="$v.ccNumber.$model"
          />
        </div>
        <template v-if="$v.ccNumber.$error">
          <span class="error" v-if="!$v.ccNumber.required"
            >Credit card number is required</span
          >
          <span class="error" v-else-if="!$v.ccNumber.creditCard"
            >Please enter a valid credit card number</span
          >
        </template>

        <div>
          <label>Exp Date</label>
          <select v-model="ccExpiryMonth">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select>
            <option v-for="(year, index) in 15" :key="index">
              {{ yearFrom(index) }}
            </option>
          </select>
        </div>

        <input
          type="submit"
          name="submit"
          class="button submit-button"
          :disabled="checkoutStatus == 'PENDING'"
          value="Complete Purchase"
        />
        <!-- disable the input when click submit -->
      </form>

      <!--      <div-->
      <!--        style="-->
      <!--          border: 1px solid black;-->
      <!--          padding: 1em;-->
      <!--          margin-left: 1em;-->
      <!--          text-align: left;-->
      <!--        "-->
      <!--      >-->
      <!--        <tree-view-->
      <!--          :data="$v"-->
      <!--          :options="{ rootObjectKey: '$v', maxDepth: 1 }"-->
      <!--        ></tree-view>-->
      <!--      </div>-->
      <!--      comment out the tree view-->

      <section class="purchase-info-box">
        <section>
          <div class="total-charge-box">
            <p>
              Your credit card will be charged
              <b>{{ (cart.subtotal + cart.surcharge) | asDollarsAndCents }}</b>
              in total.
            </p>
          </div>
          <div>
            <ul>
              <li>
                Subtotal: <b>{{ cart.subtotal | asDollarsAndCents }}</b>
              </li>
              <li>
                Surcharge: <b>{{ cart.surcharge | asDollarsAndCents }}</b>
              </li>
              <li style="display: flex; flex-direction: row">
                <div class="row-sep-transparent"></div>
                <div class="row-sep-visible"></div>
              </li>
              <li>
                Total:
                <b>{{
                  (cart.subtotal + cart.surcharge) | asDollarsAndCents
                }}</b>
              </li>
            </ul>
          </div>
        </section>

        <section v-show="checkoutStatus != ''" class="checkoutStatusBox">
          <div v-if="checkoutStatus == 'ERROR'">
            Error: Please fix the problems above and try again.
          </div>

          <div v-else-if="checkoutStatus == 'PENDING'">Processing...</div>

          <div v-else-if="checkoutStatus == 'OK'">Order placed...</div>

          <div v-else>
            <!-- "checkoutStatus == 'SERVER_ERROR'" -->
            An unexpected error occurred, please try again.
          </div>
        </section>
      </section>
    </section>
  </div>
</template>

<script>
import {
  required,
  email,
  minLength,
  maxLength,
} from "vuelidate/lib/validators";

import isCreditCard from "validator/lib/isCreditCard";
import isMobilePhone from "validator/lib/isMobilePhone";

const phone = (value) => isMobilePhone(value, "en-US");
const creditCard = (value) => isCreditCard(value);

export default {
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: "",
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    phone: {
      required,
      phone,
    },
    ccNumber: {
      required,
      creditCard,
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    email: {
      required,
      email,
    },
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
    },
  },

  methods: {
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$invalid) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        setTimeout(() => {
          this.$store
            .dispatch("placeOrder", {
              name: this.name,
              address: this.address,
              phone: this.phone,
              email: this.email,
              ccNumber: this.ccNumber,
              ccExpiryMonth: this.ccExpiryMonth,
              ccExpiryYear: this.ccExpiryYear,
            })
            .then(() => {
              this.checkoutStatus = "OK";
              this.$router.push({ name: "confirmation" });
            })
            .catch((reason) => {
              this.checkoutStatus = "SERVER_ERROR";
              console.log("Error placing order", reason);
            });
        }, 1000);
      }
    },

    /* NOTE: For example yearFrom(0) == 2021 */
    yearFrom(index) {
      return new Date().getFullYear() + index;
    },
  },
};
</script>

<style scoped>
.checkout-page {
  background: var(--secondary-background-color);
  color: var(--primary-color);
}
.checkout-page-body {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  padding: 3em 12em;
  justify-content: space-between;
}

option {
  cursor: pointer;
  font-size: smaller;
  font-family: "Arial Nova";
  color: var(--primary-color);
}

.purchase-info-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-content: flex-end;
  padding-bottom: 2em;
}

.total-charge-box {
  margin: 0 1em;
  padding: 0 1em;
  text-align: end;
}

.row-sep-transparent {
  background-color: var(--secondary-background-color);
  height: 2px;
  width: 50%;
  margin: 0.5rem 0;
  align-content: flex-end;
}

.row-sep-visible {
  background-color: var(--primary-color);
  height: 2px;
  width: 50%;
  margin: 0.5rem 0;
  align-content: flex-end;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  background-color: var(--secondary-background-color);
  margin-left: 0.5em;
  border: solid var(--primary-color) 2px;
  border-radius: 8px;
}

form > .error {
  color: red;
  text-align: right;
  font-size: small;
  margin-bottom: 1rem;
  margin-top: -0.4rem;
}

ul,
li {
  text-align: end;
  margin: 0.5em 1em;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: end;
}

.continue-shop-buttons {
  background-color: #2aa77a;
  color: var(--card-background-color);
  text-align: center;
  padding: 0;
  float: left;
  width: 10em;
  height: 2.5em;
  border-style: none;
  border-radius: 8px;
}

.continue-shop-buttons:hover {
  background-color: var(--card-background-color);
  border: solid var(--primary-color) 2px;
  color: var(--primary-color);
}

.submit-button {
  background-color: var(--secondary-color);
  color: var(--card-background-color);
  box-shadow: 0 5px 15px 0 rgba(0, 0, 0, 0.19);
  text-align: center;
  margin-left: 6rem;
  margin-top: 1rem;
  padding: 0.5em;
  width: 10em;
  height: 2.5em;
  border-style: none;
  border-radius: 8px;
}

.submit-button:hover {
  background-color: purple;
}
</style>
