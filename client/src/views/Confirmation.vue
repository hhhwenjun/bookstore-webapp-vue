<template>
  <div class="confirmation-page">
    <ul>
      <li class="large-text">
        <b>Thank you for your order!</b>
      </li>
      <li>
        Confirmation #:
        <b>{{ $store.state.orderDetails.order.confirmationNumber }}</b>
      </li>
      <li>Order Time: {{ orderTime }}</li>
    </ul>
    <div class="purchase-summary"><b>Purchase Summary</b></div>
    <confirmation-table> </confirmation-table>
    <div>
      <ul class="customer-info">
        <li>
          Subtotal:
          <b>{{
            ($store.state.orderDetails.order.amount -
              $store.state.cart.surcharge)
              | asDollarsAndCents
          }}</b>
        </li>
        <li>
          Surcharge:
          <b>{{ $store.state.cart.surcharge | asDollarsAndCents }}</b>
        </li>
        <li>
          Total:
          <b>{{
            $store.state.orderDetails.order.amount | asDollarsAndCents
          }}</b>
        </li>
      </ul>
    </div>
    <ul class="customer-info">
      <li class="purchase-summary"><b>Customer Information</b></li>
      <li>
        {{ $store.state.orderDetails.customer.customerName }}
      </li>
      <li>{{ $store.state.orderDetails.customer.address }}</li>
      <li>{{ $store.state.orderDetails.customer.email }}</li>
      <li>{{ $store.state.orderDetails.customer.phone }}</li>
      <li>{{ hideCCNumber }}({{ ccExpMonth }} - {{ ccExpYear }})</li>
    </ul>
    <div id="customerInfo"></div>
  </div>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
export default {
  name: "Confirmation",
  components: { ConfirmationTable },
  computed: {
    hideCCNumber() {
      let number = this.$store.state.orderDetails.customer.ccNumber;
      let newNumber = "**** **** ****" + number.substr(number.length - 4, 4);
      return newNumber;
    },
    ccExpYear() {
      let epoch = this.$store.state.orderDetails.customer.ccExpDate;
      let newDate = new Date(epoch);
      let year = newDate.getUTCFullYear();
      return year;
    },
    ccExpMonth() {
      let epoch = this.$store.state.orderDetails.customer.ccExpDate;
      let newDate = new Date(epoch);
      let month = newDate.getMonth() + 1;
      return month;
    },
    orderTime() {
      let epoch = this.$store.state.orderDetails.order.dateCreated;
      let orderDate = new Date(epoch);
      return orderDate;
    },
  },
};
</script>

<style scoped>
.confirmation-page {
  display: flex;
  flex-direction: column;
  background: var(--secondary-background-color);
  color: var(--primary-color);
}
ul {
  text-align: center;
}
ul > li {
  margin: 0.25em;
}

.purchase-summary {
  text-align: center;
  font-size: x-large;
  margin-top: 1em;
}

.customer-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-content: flex-end;
  padding-bottom: 2em;
}

.large-text {
  font-size: xx-large;
}
</style>
