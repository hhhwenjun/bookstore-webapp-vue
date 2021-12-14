<template>
  <table>
    <tr>
      <td class="table-title">Book Title</td>
      <td class="table-title">Quantity</td>
      <td class="table-title">Price</td>
    </tr>
    <tr
      v-for="(item, index) in this.$store.state.orderDetails.lineItems"
      :key="item.productId"
    >
      <td>
        {{ $store.state.orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>
        {{ $store.state.orderDetails.books[index].price | asDollarsAndCents }}
      </td>
    </tr>
  </table>
</template>

<script>
export default {
  name: "ConfirmationTable",
};
</script>

<style scoped>
table {
  border: 3px var(--primary-color) dotted;
  border-radius: 10px;
  box-shadow: 0 5px 15px 0 rgba(0, 0, 0, 0.19);
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
  align-self: center;
  border-collapse: collapse;
}

.table-title {
  background-color: var(--primary-color);
  color: var(--secondary-background-color);
}

td {
  display: table-cell;
  padding: 0.5em 0.5em;
  background-color: var(--secondary-background-color);
  vertical-align: middle;
}

td:nth-child(1) {
  text-align: center;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: center;
}
</style>
