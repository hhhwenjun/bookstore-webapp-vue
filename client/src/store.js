import Vue from "vue";
import Vuex from "vuex";
import ApiService from "./services/ApiService";
import { ShoppingCart } from "@/models/ShoppingCart";

Vue.use(Vuex);

export const CART_STORAGE_KEY = "cart";
export const ORDER_DETAIL_STORAGE_KEY = "orderDetail";

export default new Vuex.Store({
  state: {
    categories: [],
    selectedCategoryName: "",
    selectedCategoryBooks: [],
    orderDetails: null,
    cart: new ShoppingCart(),
  },
  mutations: {
    SET_CATEGORIES(state, newCategories) {
      state.categories = newCategories;
    },
    SELECT_CATEGORY(state, categoryName) {
      state.selectedCategoryName = categoryName;
    },
    SET_SELECTED_CATEGORY_BOOKS(state, books) {
      state.selectedCategoryBooks = books;
    },
    ADD_TO_CART(state, book) {
      state.cart.addItem(book, 1);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    UPDATE_CART(state, { book, quantity }) {
      state.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    CLEAR_CART(state) {
      state.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_CART(state, shoppingCart) {
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(shoppingCart));
      let newCart = new ShoppingCart();
      shoppingCart.items.forEach((item) => {
        newCart.addItem(item.book, item.quantity);
      });
      state.cart = newCart;
    },
    CLEAR_ORDER_DETAILS(state) {
      sessionStorage.removeItem(ORDER_DETAIL_STORAGE_KEY);
      state.orderDetails = null;
    },
    SET_ORDER_DETAILS(state, orderDetails) {
      state.orderDetails = orderDetails;
      sessionStorage.setItem(
        ORDER_DETAIL_STORAGE_KEY,
        JSON.stringify(orderDetails)
      );
    },
  },
  actions: {
    fetchCategories(context) {
      ApiService.fetchCategories()
        .then((categories) => {
          //response data
          console.log("Data: ", categories);
          context.commit("SET_CATEGORIES", categories);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
        });
    },
    fetchSelectedCategoryBooks(context) {
      return ApiService.fetchSelectedCategoryBooks(
        context.state.selectedCategoryName
      )
        .then((selectedCategoryBooks) => {
          //response data
          console.log("Data: " + selectedCategoryBooks);
          context.commit("SET_SELECTED_CATEGORY_BOOKS", selectedCategoryBooks);
        })
        .catch((reason) => {
          //invalid result
          console.log("Error: " + reason);
          throw reason;
        });
    },
    addToCart(context, book) {
      context.commit("ADD_TO_CART", book);
    },
    updateCart(context, { book, quantity }) {
      context.commit("UPDATE_CART", { book, quantity });
    },
    selectCategory(context, categoryName) {
      context.commit("SELECT_CATEGORY", categoryName);
    },
    clearCart(context) {
      context.commit("CLEAR_CART");
    },
    placeOrder({ commit, state }, customerForm) {
      commit("CLEAR_ORDER_DETAILS");

      return ApiService.placeOrder({
        cart: state.cart,
        customerForm: customerForm,
      }).then((orderDetails) => {
        commit("CLEAR_CART");
        commit("SET_ORDER_DETAILS", orderDetails);
      });
    },
  },
});
