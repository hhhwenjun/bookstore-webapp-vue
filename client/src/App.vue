<template>
  <div id="app">
    <app-header></app-header>
      <router-view id="router-view" :key="$route.fullPath"></router-view>
    <app-footer></app-footer>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";

export default {
  name: "App",
  components: {
    AppHeader,
    AppFooter,
  },
  created: function () {
    this.$store.dispatch("fetchCategories");
  },
};
</script>

<style></style>
