<template>
  <div>
    <div align="center" style="padding-top:50%; padding-left:40px; padding-right:40px;">
<!--    <div align="center" style="padding-top:50%; padding-left:40px; padding-right:40px;"> -->
      <v-text-field
        label="为自己取一个响亮的名字吧"
        v-model="name"
        :error="error"
      ></v-text-field>
      <v-text-field
        label="你的ip地址"
        v-model="username"
      ></v-text-field>
      <v-btn :loading="loading" 
      block
      color="success"
      @click="register()">进入</v-btn>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      loading: false,
      username: '192.168.0.12',
      name: '',
      error: false
    }
  },
  mounted() {
    let _self = this
    let conn = new RTCPeerConnection({
      iceServers: []
    }) 
    let noop = function(){}
    conn.onicecandidate = function(ice){
      if (ice.candidate){
        //使用正则获取ip
        let ip_regex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/
        let ip_addr = ip_regex.exec(ice.candidate.candidate)[1];
        _self.username = ip_addr
        conn.onicecandidate = noop
      }
    }
    //随便创建一个叫狗的通道(channel)
    conn.createDataChannel('dog')
    //创建一个SDP协议请求
    conn.createOffer(conn.setLocalDescription.bind(conn),noop)
    // this.register()
  },
  
  methods: {
    register() {
      let _self = this
      if(_self.name === '') {
        alert('别忘了给自己起一个响亮的名字')
        _self.error = true
        return
      }
      _self.loading = true
      axios.post("/api/register", {
        username: _self.username,
        name: _self.name
      })
      .then(response =>{
        if(response.data.success){
          _self.user.username = response.data.data.username
          setTimeout(_self.jump,100)
        } else {
          alert(response.data.message)
          _self.loading = false
        }
        // stompClient.send("/api/entrance", {}, JSON.stringify([{username}]));
      })
    },
    jump () {
      let _self = this
      _self.loading = false
      _self.$router.replace('/index')
    }
  }
}
</script>

<style lang="scss">
</style>


