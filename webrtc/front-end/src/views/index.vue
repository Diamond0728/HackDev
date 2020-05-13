<template>
  <div>
    <v-card v-if="!isLinked" color="grey lighten-4" flat>
      <v-toolbar color="primary" dark>
        <v-toolbar-title>音视频聊天室</v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
    </v-card>

    <v-list v-if="!isLinked" subheader>
      <v-subheader>当前局域网内在线用户</v-subheader>
      <v-list-tile 
        v-for="(username, index) in usernames" 
        :key="index"
        avatar
        v-if="username != user.username"
      >
        <v-list-tile-avatar>
          <user-avatar :size="'small'" :i="index" :word="username[0]"></user-avatar>
        </v-list-tile-avatar>
        <v-list-tile-content>
          <v-list-tile-title v-html="names[index]"></v-list-tile-title>
          <v-list-tile-title v-html="`ip：${username}`"></v-list-tile-title>
        </v-list-tile-content>
        <v-list-tile-action>
          <v-icon color="success" @click="tryPhoneCall(username)">phone</v-icon>
        </v-list-tile-action>
        <v-list-tile-action>
          <v-icon color="success" @click="tryVideoCall(username)">video_call</v-icon>
        </v-list-tile-action>
      </v-list-tile>
    </v-list>

    <!--<div>
      <button @click="start()">Start</button>
      <button @click="call()">CallAction</button>
      <button @click="hangUp()">Hang Up</button>
    </div>-->
    <v-dialog v-model="askAccept" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <div align="center">
          <user-avatar :size="'large'" :i="usernames.indexOf(targetUsername)" :word="targetUsername[0]"
          style="padding-top:100px;margin-bottom:200px;"></user-avatar>
          <h2>来自{{targetUsername}}的{{option.video?'视频':'语音'}}通话邀请</h2>
          <v-btn fab dark depressed color="success" @click="acceptCall()" class="mr-5">
            接受
          </v-btn>
          <v-btn fab dark depressed color="error" @click="refuseCall()" class="mf-5">
            拒绝
          </v-btn>
        </div>
      </v-card>
    </v-dialog>

    <v-dialog v-model="linking" fullscreen hide-overlay transition="dialog-bottom-transition">
      <v-card>
        <div align="center">
          <user-avatar :size="'large'" :i="usernames.indexOf(targetUsername)" :word="targetUsername[0]"
          style="padding-top:100px;margin-bottom:200px;"></user-avatar>
          <h2>等待{{names[usernames.indexOf(targetUsername)]}}接受邀请</h2>
          <v-btn fab dark depressed color="error" @click="cancelCall()">
            挂断
          </v-btn>
        </div>
      </v-card>
    </v-dialog>
    <div>
      <video 
        :style="`display:${isLinked && option.video ? '': 'none'};`" 
        id="remoteVideo"
        :height="screenHeight"
        autoplay playsinline>
      </video>
      <video 
        :style="`display:${isLinked && option.video ? '': 'none'};
        position: absolute;
        right: 10px;
        top: 10px;
        z-index:999`" 
        width="100px"
        id="localVideo"
        muted
        autoplay playsinline>
      </video>

      <div align="center" :style="`display:${isLinked && !option.video ? '': 'none'};`">
        <user-avatar :size="'large'" :i="usernames.indexOf(targetUsername)" :word="targetUsername[0]"
        style="padding-top:100px;margin-bottom:140px;"></user-avatar>
        <h2>正在和{{names[usernames.indexOf(targetUsername)]}}语音通话</h2>
      </div>
    </div>
    <div v-if="isLinked" align="center" style="
      position: absolute;
      bottom: 100px;
      z-index:999;
      width:100%">
      <v-btn fab dark  depressed color="red" 
        @click="reject()">
        <div icon>
          <v-icon>call_end</v-icon>
        </div>
      </v-btn>
    </div>
  </div>
</template>

<script>
/* eslint-disable*/
import {trace, sleep} from '@/main.js'
import userAvatar from '@/components/userAvatar'
import axios from 'axios'
import * as _ from 'lodash'
// import axios from 'axios'
export default {
  components: {
    userAvatar
  },
  data () {
    return {
      sharetoken: '',
      localStream: '',
      localPeerConnection: '',
      remotePeerConnection: '',
      sock: '',
      stomp: '',
      socket: '',
      stompClient: '',
      usernames: [],
      names: [],
      targetUsername: '',
      isLinked: false,
      option: {
      },
      askAccept: false,
      linking: false
    }
  },
  computed: {
    localVideo: () => document.getElementById("localVideo"),
    remoteVideo: () => document.getElementById("remoteVideo"),
    screenHeight: () => document.body.clientHeight
  },
  async mounted () {
    let _self = this
    // alert(this.user.username)
    if(this.user.username === '') {
      this.$router.replace('/login')
      return
    }
    
    this.socketConnect()
    setTimeout(this.broad,1000)
  },
  methods: {
    socketConnect() {
      let _self = this
      this.sock = new SockJS("/root/endpointChat")
      this.stomp = Stomp.over(this.sock)
      _self.stomp.connect('guest', 'guest', function(frame) {
        console.log(frame)
        _self.stomp.subscribe("/user/queue/notifications", async (message) => {
          const messageBody = eval('(' + message.body + ')')
          // const description = messageBody.content
          if(messageBody.type === 'send') {
            const description = eval('(' +messageBody.content + ')')
            _self.createAnswer(description, messageBody.from)
          }
          else if(messageBody.type === 'tryCall') {
            _self.option = eval('(' +messageBody.content + ')')
            _self.targetUsername = messageBody.from
            if(_self.isLinked) {
              _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
                'type': 'isLinked',
                'content': ''  
              }))
            }
            else {
              _self.askAccept = true
            }
          }
          else if(messageBody.type === 'accept') {
            _self.linking = false
            _self.isLinked = true
            _self.call(messageBody.from)
            await sleep(1000)
            // why call again? I dont konw. It doesnt work without twice call.
            _self.call(messageBody.from)
          }
          else if(messageBody.type === 'refuse'){
            await _self.hangUp()
            _self.linking = false
            _self.localStream.getVideoTracks()[0].stop()
            alert('对方拒接')
          }
          else if(messageBody.type === 'isLinked'){
            await _self.hangUp()
            _self.linking = false
            _self.localStream.getVideoTracks()[0].stop()
            alert('对方正在通话中，请稍后重试')
          }
          else if(messageBody.type === 'cancelCall'){
            _self.askAccept = false
          }
          else if(messageBody.type === 'reject'){
            await _self.hangUp()
            _self.isLinked = false
            _self.localStream.getVideoTracks()[0].stop()
            alert('对方挂断了电话')
          }
          else if(messageBody.type === 'match'){
            const description = eval('(' +messageBody.content + ')')
            _self.localPeerConnection.setRemoteDescription(description)
          }
        })
      })

      _self.socket =  new SockJS("/root/endpointChat");
      _self.stompClient = Stomp.over(_self.socket)
      _self.stompClient.connect({}, function (frame) {//连接WebSocket服务端
        console.log(frame)
        _self.stompClient.subscribe("/topic/users/list", async function (msg) {//通过stopmClient.subscribe订阅"/topic/response"目标发送的消息，这个路径是在控制器的@SendTo中定义的
          const users = eval('(' + msg.body + ')')
          const newUsernames =  _.map(users, 'username')
          const newNames =  _.map(users, 'name')
          console.log(newUsernames)
          if(_self.isLinked && newUsernames.indexOf(_self.targetUsername) === -1){
            await _self.hangUp()
            _self.isLinked = false
            _self.localStream.getVideoTracks()[0].stop()
            alert('对方挂断了电话')
          }
          _self.usernames = newUsernames
          _self.names = newNames
          console.log(_self.usernames)
        });
      })

    },
    broad() {
      let _self = this 
      _self.stompClient.send("/api/entrance", {}, JSON.stringify());
    },

    async tryPhoneCall(username) {
      let _self = this
      _self.option = {
        // video: true,
        audio:true
      }
      await _self.start()
      await _self.callAction()
      _self.tryCall(username)
      _self.linking = true
    },

    async tryVideoCall(username) {
      let _self = this
      _self.option = {
        video: true,
        audio:true
      }
      await _self.start()
      await _self.callAction()
      _self.tryCall(username)
      _self.linking = true
    },

    async tryCall(targetUsername) {
      let _self = this
      _self.targetUsername = targetUsername
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': targetUsername,
        'type': 'tryCall',
        'content': JSON.stringify(_self.option) 
      }))
    },

    cancelCall() {
      let _self = this
      _self.hangUp()
      _self.linking = false
      _self.localStream.getVideoTracks()[0].stop();
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
        'type': 'cancelCall',
        'content': '' 
      }))
    },

    async call(targetUsername) {
      console.log("call!!!!!!!!")
      let _self = this
      _self.createOffer(targetUsername)
    },

    async acceptCall() {
      let _self = this
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
        'type': 'accept',
        'content': ''  
      }))
      _self.askAccept = false
      await _self.start()
      await _self.callAction()
      _self.isLinked = true
      _self.call(_self.targetUsername)
      await sleep(1000)
      // why call again? I dont konw. It doesnt work without twice call.
      _self.call(_self.targetUsername)
    },

    async refuseCall() {
      let _self = this
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
        'type': 'refuse',
        'content': ''  
      }))
      // await _self.hangUp()
      // _self.localVideo.srcObject = null
      // _self.localStream = null
      _self.askAccept = false
    },

    reject() {
      let _self = this
      _self.hangUp()
      _self.isLinked = false
      _self.localStream.getVideoTracks()[0].stop();
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
        'type': 'reject',
        'content': '' 
      }))
    },

    async start() {
      let _self = this
      const mediaStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio:true
      })
      // .then( mediaStream => {
        console.log(_self.localVideo)
        _self.localVideo.srcObject = mediaStream;
        _self.localStream = mediaStream;
        trace('Received local stream.');
      // })
      // .catch( error => {
      //   trace(`navigator.getUserMedia error: ${error.toString()}.`);
      // });
      trace('Requesting local stream.');
    },
    async callAction() {
      let _self = this
      // 获取媒体流
      const videoTracks = _self.localStream.getVideoTracks();
      const audioTracks = _self.localStream.getAudioTracks();
      if (videoTracks.length > 0) {
        trace(`Using video device: ${videoTracks[0].label}.`);
      }
      if (audioTracks.length > 0) {
        trace(`Using audio device: ${audioTracks[0].label}.`);
      }
      // 定义本地连接
      _self.localPeerConnection = new RTCPeerConnection({});
      trace('Created local peer connection object localPeerConnection.');
      _self.localPeerConnection.addEventListener('icecandidate', event => {
        const peerConnection = event.target;
        const iceCandidate = event.candidate;
        if (iceCandidate) {
          const newIceCandidate = iceCandidate;
          const otherPeer = (peerConnection === _self.localPeerConnection) ? _self.remotePeerConnection : _self.localPeerConnection;
          console.log(1000)
          console.log(newIceCandidate)
          otherPeer.addIceCandidate(newIceCandidate)
            .then(() => {
              trace(`${_self.getPeerName(peerConnection)} addIceCandidate success.`);
            }).catch((error) => {
              trace(`${_self.getPeerName(peerConnection)} failed to add ICE Candidate:\n`+`${error.toString()}.`);
            });
        }
      });
      _self.localPeerConnection.addEventListener('iceconnectionstatechange', event => {
        const peerConnection = event.target;
        console.log('ICE state change event: ', event);
        trace(`${_self.getPeerName(peerConnection)} ICE state: ` + `${peerConnection.iceConnectionState}.`);
      });
      // 定义远程连接
      _self.remotePeerConnection = new RTCPeerConnection({});
      trace('Created remote peer connection object remotePeerConnection.');

      _self.remotePeerConnection.addEventListener('icecandidate', event => {
        const peerConnection = event.target;
        const iceCandidate = event.candidate;

        if (iceCandidate) {
          const newIceCandidate = iceCandidate;
          const otherPeer = (peerConnection === _self.localPeerConnection) ? _self.remotePeerConnection : _self.localPeerConnection;

          otherPeer.addIceCandidate(newIceCandidate)
            .then(() => {
              trace(`${_self.getPeerName(peerConnection)} addIceCandidate success.`);
            }).catch((error) => {
              trace(`${_self.getPeerName(peerConnection)} failed to add ICE Candidate:\n`+`${error.toString()}.`);
            });

          // trace(`${getPeerName(peerConnection)} ICE candidate:\n` +
          //       `${event.candidate.candidate}.`);
        }
      });
      _self.remotePeerConnection.addEventListener('iceconnectionstatechange', event => {
        const peerConnection = event.target;
        console.log('ICE state change event: ', event);
        trace(`${_self.getPeerName(peerConnection)} ICE state: ` + `${peerConnection.iceConnectionState}.`);
      });
      _self.remotePeerConnection.addEventListener('addstream', event => {
        const mediaStream = event.stream;
        _self.remoteVideo.srcObject = mediaStream;
        _self.remoteStream = mediaStream;
        trace('Remote peer connection received remote stream.');
      });

      // Add local stream to connection and create offer to connect.
      _self.localPeerConnection.addStream(_self.localStream);
      trace('Added local stream to localPeerConnection.');
    },

    async createOffer(targetUsername) {
      let _self = this
      trace('localPeerConnection createOffer start.');
      const description = await _self.localPeerConnection.createOffer({offerToReceiveVideo: 1})
      // .then(description => {
        trace(`Offer from localPeerConnection:\n${description.sdp}`);

        trace('localPeerConnection setLocalDescription start.');
        console.log("==============")
        console.log(description)
        console.log("==============")
        _self.localPeerConnection.setLocalDescription(description)
        console.log("send websocket")
        _self.stomp.send("/api/chat", {}, JSON.stringify({'username': targetUsername,
        'type': 'send',
        'content':  JSON.stringify(description)}))
      // })
    },
    createAnswer(description, from) {
      let _self = this
      trace('remotePeerConnection setRemoteDescription start.');
      _self.remotePeerConnection.setRemoteDescription(description)

      trace('remotePeerConnection createAnswer start.');
      _self.remotePeerConnection.createAnswer()
      .then(desc => {
        trace(`Answer from remotePeerConnection:\n${desc.sdp}.`);

        trace('remotePeerConnection setLocalDesc start.');
        _self.remotePeerConnection.setLocalDescription(desc)
        trace('localPeerConnection setRemoteDescription start.');
        // _self.localPeerConnection.setRemoteDescription(desc)
        _self.stomp.send("/api/chat", {}, JSON.stringify({'username': from,
        'type': 'match',
        'content':  JSON.stringify(desc)}))
      })
    },
    async hangUp() {
      let _self = this
      _self.localPeerConnection.close();
      _self.remotePeerConnection.close();
      _self.localPeerConnection = null;
      _self.remotePeerConnection = null;
      _self.stomp.send("/api/chat", {}, JSON.stringify({'username': _self.targetUsername,
        'type': 'hangUp',
        'content': ''  
      }))
      _self.isLinked = false
      trace('Ending call.');
    },
    getPeerName(peerConnection) {
      let _self = this
      return (peerConnection === _self.localPeerConnection) ?
          'localPeerConnection' : 'remotePeerConnection';
    },
    
  }
}
</script>

<style lang="stylus">
</style>
<style lang="scss" scoped>
</style>


