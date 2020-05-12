<template>
  <div>
    <v-card hover class="mb-2" v-for="(item,index) in users" :key="index">
      <div class="pa-2">
        <v-layout row wrap>
          <v-flex class="ml-1 mr-1">
            <v-text-field disabled label="用户ID" v-model="item.u_id"></v-text-field>
          </v-flex>
          <v-flex class="ml-1 mr-1">
            <v-text-field disabled label="用户名" v-model="item.username"></v-text-field>
          </v-flex>
          <v-flex class="ml-1 mr-1">          
            <v-text-field disabled label="姓名" v-model="item.name"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row wrap>
          <v-flex  class="ml-1 mr-1">
            <v-text-field color="green" label="联系方式" v-model="item.phone"></v-text-field>
          </v-flex>
          <v-flex class="ml-1 mr-1">
            <v-text-field color="green" label="邮箱" v-model="item.email"></v-text-field>
          </v-flex>
        </v-layout>
        <v-layout row wrap>
            <v-checkbox v-for="(role,i) in roleList" :key="i" 
            :label="role.mask" v-model="item.roles" :value="role.rolename">
            </v-checkbox>
        </v-layout>
        <v-layout>
            <v-btn block color="success" @click="save(item)">保存修改</v-btn>
        </v-layout>
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
  export default {
    methods: {
      open (msg) {
        this.dialogmsg = msg
        this.dialog = true
      },
      close () {
        this.dialog = false
      },
      save(item) {
        let tvm = this
        this.axios.post('/api/admin/change/user', item)
        .then(function (response) {
          if (response.status == 200){
            if(response.data.success) {
              tvm.open("修改成功")
            } else {
              tvm.open(response.data.message)
            }
          } else {
            console.log('permission limit')
          }
        })
      },
      getUsers (pg) {
        let tvm = this
        this.axios.get('/api/admin/user/list?pg=' + pg)
        .then(function (response) {
          if (response.status == 200){
            if(response.data.success) {
              console.log(response.data.data)
              tvm.users = response.data.data
            } else {
              console.log('some error happened')
            }
          } else {
            console.log('permission limit')
          }
        })
      },

      getRoleList () {
        let tvm = this
        this.axios.get('/api/admin/all/roles')
        .then(function (response) {
          if (response.status == 200){
            if(response.data.success) {
              tvm.roleList = response.data.data
            } else {
              console.log('some error happened')
            }
          } else {
            console.log('permission limit')
          }
        })
      }

    },

    mounted () {
      this.getUsers(this.$route.params.id);
      this.getRoleList();
    },

    data () {
      return {
        users: [
          {
            u_id: 0,
            username: 'Frozen Yogurt',
            name: '',
            phone: '',
            email: '',
            roles: ["",""]
          }
        ],
        roleList: [
          {r_id: 0, rolename: "", mask: ""}
        ],
        dialog: false,
        dialogmsg: ''
      }
    }
  }
</script>