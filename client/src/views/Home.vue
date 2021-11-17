<template>
  <div>
    <section>
      <img
        src="@/assets/images/site/hero.png"
        alt="Hero_img_LOTR"
        width="100%"
        height="auto"
      />
      <router-link to="../category/Classics">
        <button class="shop-book-button">Shop Book</button>
      </router-link>

      <p class="welcome-text">Start your journey with us</p>
    </section>
  </div>
</template>

<script></script>

<style scoped>
.welcome-text {
  width: 20em;
  font-size: 24px;
  font-weight: bolder;
  font-style: italic;
  font-family: "Crimson Text", sans-serif;
  color: #005e3c;
  position: absolute;
  padding: 1rem;
  top: 20rem;
  left: 41%;
}

.category-image-items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 1em;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}

.category-image-items div {
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
}

.background-image {
  opacity: 80%;
}
</style>
