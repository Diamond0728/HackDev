<template>
    <div>
        <v-card>
          <v-toolbar light flat>
            <v-toolbar-title>登录</v-toolbar-title>
          </v-toolbar>
          <v-divider></v-divider>
          <div class="pa-2">
            <v-text-field color="green" label="用户名" type="text" v-model="username"></v-text-field>
          
            <v-text-field color="green" label="密码" type="password" v-model="password"></v-text-field>
            
            <v-text-field color="green" label="确认密码" type="password" v-model="passwordcheck"></v-text-field>
          
            <v-btn block color="success" @click="register">注册</v-btn>
          </div>
        </v-card>
        <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>{{dialogmsg}}</v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" flat @click.stop="dialog=false">确认</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
</template>

<script>
import {mapState, mapMutations} from 'vuex';
export default {
  data () {
    return {
      username: '',
      password: '',
      passwordcheck: '',
      dialog: false,
      dialogmsg: ''
    }
  },
  computed: {
    ...mapState('pageTransition', {
        storeUser: state => state.user,
    }),
  },
  methods: {
    ...mapMutations('pageTransition', [  
        'setUser'
    ]),
    open (msg) {
      this.dialogmsg = msg
      this.dialog = true
    },
    close () {
      this.dialog = false
    },
    register () {
      let tvm = this
      if (tvm.password != tvm.passwordcheck) {
        tvm.open("两次密码不一致");
      } else {
          this.axios.post('/api/register', {username: tvm.username, password: tvm.password})
          .then(function (response) {
              if (response.status == 200){
              if(response.data.success) {
                //把用户信息放入vuex中保存
                tvm.setUser(response.data.data)
                tvm.$router.push('/')
              } else {
                tvm.open(response.data.message)
              }
              } else {
              tvm.open('服务器繁忙,请稍后重试')
              }
          })
          .catch(function (err) {
              console.log(err)
          })
      }
    }
  }
  
}
</script>

<style>
.ct{
  width: px;
  margin: 0 auto;
}
</style>
