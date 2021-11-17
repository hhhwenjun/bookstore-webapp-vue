<template>
  <footer class="container">
    <section class="copyright">
      <p>2021 Book Galaxy by Wenjun Han</p>
    </section>
    <section class="links">
      <router-link to="/">about</router-link>
      <router-link to="/">contact</router-link>
      <router-link to="/">directions</router-link>
    </section>
    <section class="links social-media-icons">
      <router-link to="/" class="social-media-icons-link"
        ><i class="fab fa-facebook-square"></i
      ></router-link>
      <router-link to="/" class="social-media-icons-link"
        ><i class="fab fa-instagram"></i
      ></router-link>
      <router-link to="/" class="social-media-icons-link"
        ><i class="fab fa-twitter"></i
      ></router-link>
    </section>
  </footer>
</template>

<script>
export default {
  name: "AppFooter",
};
</script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: grid;
  grid-template-columns: 33% 33% 33%;
  gap: 1em;
}

.copyright {
  color: var(--primary-color);
  text-align: left;
}

.links {
  display: flex;
  justify-content: center;
  font-size: large;
  flex-direction: row;
  gap: 0.5em;
}
.social-media-icons {
  display: flex;
  justify-content: end;
  font-size: large;
  flex-direction: row;
  margin-right: 3em;
  gap: 0.8em;
}

.social-media-icons-link {
  color: var(--primary-color);
  font-size: x-large;
  cursor: pointer;
}

.social-media-icons-link:hover {
  color: purple;
  font-size: x-large;
  cursor: pointer;
}
</style>
