<template>
  <div>
    
    <v-card hover class="mt-2 pa-2">
      <v-text-field label="图书ID" v-model="borrow_return.b_id"></v-text-field>
      <v-text-field label="读者ID" v-model="borrow_return.u_id"></v-text-field>
      <v-btn block color="success" @click="borrow()">借书登记</v-btn>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      borrow_return: {
        b_id: '',
        u_id: ''
      }
    }
  },
  methods: {
    borrow() {
      let tvm = this
      this.axios.post('/api/borrow/one',tvm.borrow_return)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            alert("登记成功")
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
