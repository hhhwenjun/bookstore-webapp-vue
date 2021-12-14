<template>
  <header class="container">
    <section class="header-dropdown-part">
      <header-dropdown-menu></header-dropdown-menu>
    </section>
    <section class="text-logo">
      <router-link to="/">
        <img
          src="@/assets/images/site/Bookshop_logo.png"
          alt="Book Galaxy Logo"
          class="img-style"
        />
      </router-link>
    </section>
    <section class="login-cart-and-search-bar">
      <button class="button cart">
        <router-link to="/cart">
          <span class="fa-stack">
            <i class="fas fa-shopping-cart fa-stack-1x cart-icon-style">
              <strong class="cart-number-style">
                {{ $store.state.cart.numberOfItems }}
              </strong>
            </i>
          </span>
        </router-link>
      </button>
      <button class="button login login-style">
        <strong class="top-text;"> WH </strong>
      </button>
      <form action="category.html" class="search-form">
        <input
          type="text"
          class="search-bar"
          placeholder="Search any book, album, etc"
        />
      </form>
      <button type="submit" class="search-button">
        <i class="fa fa-search submit-style"></i>
      </button>
    </section>
  </header>
</template>

<script>
import HeaderDropdownMenu from "@/components/HeaderDropdown";
export default {
  name: "AppHeader",
  components: { HeaderDropdownMenu },
  component: {
    HeaderDropdownMenu,
  },
};
</script>

<style scoped>
header {
  background: var(--primary-color);
  display: grid;
  grid-template-columns: 40% 20% 40%;
  width: 100%;
  height: 167px;
  padding: 0.5em;
  gap: 0.45em;
}

.text-logo {
  font-family: var(--title-font-family);
}

.login-cart-and-search-bar {
  display: grid;
  grid-template-columns: 33% 33% 33%;
  grid-template-rows: 33% 33% 33%;
}

.search-bar {
  padding: 0.1em;
  width: 280%;
  min-width: 120px;
  border-radius: 8px;
  border-style: none;
  text-align: center;
}

.header-dropdown-part {
  text-align: center;
  display: grid;
  grid-template-columns: 33% 33% 33%;
  grid-template-rows: 33% 33% 33%;
}

.submit-style {
  color: burlywood;
}

.login-style {
  color: #110780;
  font-size: medium;
}

.img-style {
  min-width: 150px;
  min-height: 110px;
}

.cart-icon-style {
  font-size: medium;
  text-align: center;
}

.cart-number-style {
  color: #110780;
  font-size: medium;
  text-align: center;
}

@media (max-width: 600px) {
}
</style>
