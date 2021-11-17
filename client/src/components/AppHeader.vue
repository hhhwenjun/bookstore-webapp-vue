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
          style="min-width: 150px; min-height: 110px"
        />
      </router-link>
    </section>
    <section class="login-cart-and-search-bar">
      <button class="button cart">
        <router-link to="/cart">
          <span class="fa-stack">
            <i
              class="fas fa-shopping-cart fa-stack-1x"
              style="font-size: medium; text-align: center"
            >
              <strong
                style="color: #110780; font-size: medium; text-align: center"
              >
                {{ $store.state.cart.numberOfItems }}
              </strong>
            </i>
          </span>
        </router-link>
      </button>
      <button class="button login" style="color: #110780; font-size: medium">
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
        <i class="fa fa-search" style="color: burlywood"></i>
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
  /*
  things layout by childs, you can change it to grid, then delete padding and gap.
  In grid, you need to define how many cols and rows you need to have

  display: grid;
  grid-template-columns: 33% 33% 33%; or 1fr 1fr 1fr;(3 columns, each 1/3 of it)
  gap: 1em;

  */
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
  /*
  now all the elements are equal space and perfect inline,
  we can also do it using css grid
  */
}

@media (max-width: 600px) {
  /*responsive design at here, page need to be wrapped*/
}
</style>
