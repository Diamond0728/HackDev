<template>
  <v-card hover class="pa-2">
    <v-layout row wrap>
      <v-flex sm4 xs12>
        <v-text-field label="ISBN号" v-model="bookt.isbn"></v-text-field>
      </v-flex>
      <v-flex sm4 xs12>
        <v-text-field label="图书ID" v-model="bookt.b_id"></v-text-field>
      </v-flex>
      <v-flex sm4 xs12>
        <v-select
          :items="items"
          v-model="bookt.location"
          label="存放位置"
          single-line
        ></v-select>
      </v-flex>
      <v-flex sm12 xs12>
        <v-btn block color="success" @click="addbook()">入库</v-btn>
      </v-flex>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  props: ['book'],
  data () {
    return {
      bookt: this.book,
      items:['图书流通室','图书阅览室']
    }
  },
  methods: {
    addbook () {
      let tvm = this
      this.axios.post('/api/enter/add/book',tvm.bookt)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            alert("添加成功")
          } else {
            alert(response.data.message)
          }
        } else {
          alert('permission limited')
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    }
  }
}
</script>
