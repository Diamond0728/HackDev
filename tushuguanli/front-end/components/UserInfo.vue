<template>
  <div>
    <v-card class="pa-2">
      <v-text-field label="用户ID" disabled v-model="user.u_id"></v-text-field>
      <v-text-field label="用户名" disabled v-model="user.username"></v-text-field>
      <v-text-field label="姓名" v-model="user.name"></v-text-field>
      <v-text-field label="电话号码" v-model="user.phone"></v-text-field>
      <v-text-field label="电子邮箱" v-model="user.email"></v-text-field>
      <v-btn block color="success" @click="update()">保存修改</v-btn>
    </v-card>
  </div>
</template>

<script>
export default {
  props: ['user'],
  data() {
    return {
    }
  },
  methods: {
    update() {
      let tvm = this
      this.axios.post('/api/update/user', tvm.user)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            alert('保存成功')
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
