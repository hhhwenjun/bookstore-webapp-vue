<template>
  <div class="header-dropdown">
    <button class="button categories-button" style="float: left">
      <i class="fas fa-caret-down" style="float: left"></i>
      Categories
    </button>
    <ul>
      <template v-for="category in $store.state.categories">
        <li :key="category.categoryId">
          <router-link
            tabindex="1"
            :to="{ name: 'category', params: { name: category.name } }"
            class="header-dropdown unselected-link"
          >
            {{ category.name }}
          </router-link>
        </li>
      </template>
    </ul>
  </div>
</template>

<script>
export default {
  name: "HeaderDropdownMenu",
};
</script>

<style scoped>
.categories-button {
  background-color: #2aa77a;
  color: var(--card-background-color);
  float: left;
  width: 150px;
  height: 35px;
  box-shadow: 0 15px 25px 0 rgba(0, 0, 0, 0.19);
  border-style: none;
  border-radius: 8px;
}

.categories-button:hover {
  background-color: var(--card-background-color);
  color: var(--primary-color);
}

.header-dropdown {
  grid-column: 1/2;
  grid-row: -3/-2;
  text-align: center;
}

.header-dropdown ul {
  /*drop down list*/
  display: none;
  background-color: var(--secondary-background-color);
  color: var(--primary-color);
  box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
  z-index: 1;
  position: absolute;
  border-radius: 8px;
}

.header-dropdown.unselected-link {
  display: block;
  padding: 0.25em 0.25em;
  border-radius: 8px;
  height: 25px;
  color: var(--primary-color);
  background-color: var(--secondary-background-color);
  text-decoration: none;
}

.header-dropdown.unselected-link:hover,
.header-dropdown.unselected-link:active {
  display: block;
  height: 35px;
  font-size: large;
  text-align: center;
  transition: all 250ms;
  color: var(--secondary-background-color);
  background-color: var(--primary-color);
  text-decoration: none;
}

.header-dropdown:hover ul {
  /*different when hover*/
  display: inline-block;
  position: absolute;
  min-width: 7em;
  z-index: 1;
  cursor: pointer;
}
</style>
