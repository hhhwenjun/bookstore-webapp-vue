<template>
  <div class="category-page">
    <category-nav></category-nav>
    <category-book-list></category-book-list>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";

export default {
  name: "category",
  components: {
    CategoryNav,
    CategoryBookList,
  },
  /*fetch books from database*/
  data: function () {
    return {
      cateName: this.cateName,
    };
  },
  created: function () {
    const self = this;
    this.$store.dispatch("selectCategory", this.$route.params.name);
    this.$store.dispatch("fetchSelectedCategoryBooks").catch(function () {
      self.$router.push("/404"); //'/404' triggers NotFound
    });
  },
};
</script>

<style scoped>
.category-page {
  background-color: var(--secondary-background-color);
  display: flex;
  flex-direction: row;
}
</style>
