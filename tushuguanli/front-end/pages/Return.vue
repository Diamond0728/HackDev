<template>
  <div>
    <v-layout>
      <v-flex>
        <v-card  class="pa-2" hover>
          <v-text-field v-model="b_id" label="书籍号"></v-text-field>
          <v-btn block class="success" @click="submit(b_id)">归还图书</v-btn>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
export default {
  data () {
    return {
      b_id: '',
    }
  },
  methods: {
    submit(b_id) {
      this.axios.post('/api/back/bybid',{b_id: b_id})
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            alert("操作成功")
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
