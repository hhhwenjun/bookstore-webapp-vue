<template>
  <li class="book-box">
    <div class="book-image">
      <img
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
        style="height: 200px"
      />
    </div>
    <div class="book-title">{{ book.title }}</div>
    <div class="book-author">{{ book.author }}</div>
    <div class="book-price">{{ book.price | asDollarsAndCents }}</div>
    <button class="button add-to-cart" @click="addToCart(book)">
      Add to Cart
    </button>
    <button v-if="book.isPublic" class="button read-now">Read Now</button>
  </li>
</template>

<script>
export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    addToCart(book) {
      this.$store.dispatch("addToCart", book);
    },
    bookImageFileName(book) {
      let bookName = book.title;
      return `${bookName}.png`;
    },
  },
};
</script>
<style scoped>
#book-boxes {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  padding: 1em;
  gap: 1em;
}

.book-box {
  display: grid;
  width: 310px;
  height: 225px;
  grid-template-columns: 50% 50%;
  grid-template-rows: min-content min-content min-content 1fr min-content;
  background-color: var(--secondary-background-color);
  padding: 0.5em;
  gap: 0.25em;
  box-shadow: 0 5px 15px 0 rgba(0, 0, 0, 0.19);
  border-radius: 10px;
}

.book-image {
  grid-column: 1/2;
  grid-row: 1/-1;
}

.book-title {
  font-family: "Itim", sans-serif;
  font-size: 18px;
}

.book-author {
  color: var(--primary-color);
  font-style: italic;
  font-weight: bold;
  font-family: "Nunito", sans-serif;
  font-size: 14px;
}

.book-price {
  color: rgb(0, 82, 57);
  font-size: 18px;
  font-family: "Nunito", sans-serif;
  font-weight: bold;
}
</style>
