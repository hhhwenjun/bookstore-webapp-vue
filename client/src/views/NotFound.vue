<template>
  <div class="notfound-page">
    <p>We're sorry that page cannot be found on this site.</p>
    <router-link
      :to="{
        name: 'home',
      }"
    >
      <button class="continue-button">Continue</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.notfound-page {
  display: flex;
  flex-direction: column;
  padding: 3em;
  background-color: var(--secondary-background-color);
}

.continue-button {
  background-color: #2aa77a;
  color: var(--card-background-color);
  text-align: center;
  padding: 0;
  float: left;
  width: 10em;
  height: 2.5em;
  border-style: none;
  border-radius: 8px;
  cursor: pointer;
}
</style>
