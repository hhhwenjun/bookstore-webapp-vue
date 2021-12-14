import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import Category from "./views/Category.vue";
import Cart from "./views/Cart";
import Checkout from "./views/Checkout";
import Confirmation from "./views/Confirmation";
import NotFound from "./views/NotFound.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
      alias: ["/home", "/index.html"],
    },
    {
      path: "/category/:name",
      name: "category",
      component: Category,
      props: true,
    },
    {
      path: "/category",
      redirect: "category/Classics",
    },
    {
      path: "/cart",
      name: "cart",
      component: Cart,
    },
    {
      path: "*",
      component: NotFound,
    },
    {
      path: "/checkout",
      name: "checkout",
      component: Checkout,
    },
    {
      path: "/confirmation",
      name: "confirmation",
      component: Confirmation,
    },
  ],
});
