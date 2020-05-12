<template>
  <div>
    <user-info :user="user"></user-info>
  </div>
</template>
<script>
import {mapState, mapMutations} from 'vuex';
import UserInfo from '@/components/UserInfo.vue'
export default {
  components: {
    'user-info': UserInfo
  },
  computed: {
    ...mapState('pageTransition', {
        storeUser: state => state.user,
    }),
  },
  mounted () {
    this.getUserInfo()
  },
  data() {
    return {
      user: {
        u_id: '',
        username: '',
        name: '',
        phone: '',
        email: ''
      }
    }
  },
  methods: {
    ...mapMutations('pageTransition', [  
        'setUser'
    ]),
    getUserInfo () {
      let tvm = this
      this.axios.get('/api/current/user')
      .then(function (response) {
        if (response.status == 200){
        if(response.data.success) {
          tvm.user = response.data.data
        } else {
          alert(response.data.message)
        }
        } else {
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    }
  },
}
</script>

