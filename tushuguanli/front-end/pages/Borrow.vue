<template>
  <div>
    <v-layout row wrap>
    </v-layout>
    <borrow-book></borrow-book>
  </div>
</template>

<script>
import BorrowBook from '@/components/BorrowBook.vue'
export default {
  components: {
    'borrow-book': BorrowBook
  },
  data() {
    return {
      isbn: '',
      books: [],
    }
  },
  methods: {
    find(str) {
      alert(str)
    }
  }
}
</script>
