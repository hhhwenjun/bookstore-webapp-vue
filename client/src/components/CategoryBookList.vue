<template>
  <ul>
    <template v-for="book in $store.state.selectedCategoryBooks">
      <li :key="book.bookId">
        <category-book-list-item :book="book"></category-book-list-item>
      </li>
    </template>
  </ul>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";

export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem,
  },
};
</script>

<style scoped>
ul {
  display: flex;
  flex-wrap: wrap;
  padding: 1em;
  gap: 1em;
}
</style>
