<template>
  <div>
    <v-layout row wrap>
      <v-text-field solo label="输入书名" v-model="b_name"></v-text-field>
      <v-btn color="green" dark @click="find(b_name)">查询</v-btn>
    </v-layout>
    <bookinfos class="mt-2" :bookinfos="books"></bookinfos>
  </div>
</template>

<script>
import Bookinfos from '@/components/Bookinfos.vue'
export default {
  components: {
    'bookinfos': Bookinfos
  },
  data() {
    return {
      books: '',
      page: 1,
      b_name: '',
    }
  },
  mounted() {
    this.getBooks()
  },
  methods: {
    getBooks() {
      let tvm = this
      this.axios.get('/api/book/bookinfo/list')
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            tvm.books = response.data.data
          } else {
            alert(response.data.message)
          }
        } else {
          alert('保存失败')
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    },
    find(b_name) {
      let tvm = this
      this.axios.get('/api/book/bookinfo/list/byname?b_name='+b_name)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            tvm.books = response.data.data
          } else {
            alert(response.data.message)
          }
        } else {
          alert('保存失败')
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    }
  }
}
</script>

<style>

</style>
