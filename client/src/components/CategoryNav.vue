<template>
  <nav class="category-nav">
    <ul class="category-buttons unselected-category-button">
      <template v-for="category in $store.state.categories">
        <li
          :key="category.categoryId"
          v-if="$store.state.selectedCategoryName === category.name"
          class="button category-buttons selected-category-button"
          id="button selected-category-button"
        >
          {{ category.name }}
        </li>
        <li :key="category.categoryId" v-else>
          <router-link
            tabindex="1"
            :to="{ name: 'category', params: { name: category.name } }"
            class="button category-buttons unselected-category-button"
          >
            {{ category.name }}
          </router-link>
        </li>
      </template>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "CategoryNav",
};
</script>

<style scoped>
.category-nav {
  background-color: var(--primary-color);
  border-radius: 10px;
  border: var(--primary-color) 3px solid;
  width: 10%;
  align-self: flex-start;
  margin-top: 10em;
  margin-left: 1em;
}

.category-buttons {
  text-align: center;
  width: auto;
  border-radius: 10px;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--primary-color);
  color: var(--secondary-background-color);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  display: block;
  background-color: var(--secondary-background-color);
  color: var(--primary-color);
  border-radius: 10px;
}
.selected-category-button,
.selected-category-button:active {
  display: block;
  background-color: #0017;
  color: var(--secondary-background-color);
  border-radius: 10px;
}
.selected-category-button:hover {
  display: block;
  background-color: var(--secondary-background-color);
  color: var(--primary-color);
  border-radius: 10px;
}
</style>
