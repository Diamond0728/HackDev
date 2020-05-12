<template>
  <div>
    <a-bookinfo :abookinfo="storeBookinfo" :status="2"></a-bookinfo>
    <v-card hover>
      <!-- <v-btn v-if="restNumber=0" block color="green" dark>预约</v-btn> -->
      <v-btn v-if="!reserved && restNumber==0" block color="green" @click="reserve(storeBookinfo)" dark>预约</v-btn>
      <v-btn v-if="reserved"  block color="red" flat dark>已预约</v-btn>
    </v-card>
    <v-card hover v-for="(book,index) in books" :key="index" class="mt-2 pa-2">
      <v-layout row wrap>
        <v-flex>
          书号: {{book.b_id}}
        </v-flex>
        <v-flex>
          ISBN: {{book.isbn}}
          </v-flex>
        <v-flex>
          位置: {{book.location}}
          </v-flex>
        <v-flex>
          状态: {{book.status}}
        </v-flex>
      </v-layout>
      <v-layout>
        
      </v-layout>
    </v-card>
  </div>
</template>

<script>
import {mapState, mapActions, mapMutations} from 'vuex';
import ABookinfo from '@/components/ABookinfo.vue'
export default {
  data () {
    return {
      books: [],
      reserved: false,
    }
  },
  components: {
    'a-bookinfo': ABookinfo
  },
  computed: {
    restNumber () {
      let t = 0
      let tvm = this
      for (var i = 0; i < tvm.books.length; i++) {
        if(tvm.books[i].status == '未借出') {
          t++;
        }
      }
      return t;
    },
    ...mapState('pageTransition', {
            pageTransitionType: state => state.type,
            pageTransitionEffect: state => state.effect,
            storeUser: state => state.user,
            storeBookinfo: state => state.bookinfo,
        }),
  },
  mounted () {
    this.getBook(this.$route.params.isbn)
    this.isReserve(this.storeBookinfo)
  },
  methods: {
    reserve (bookinfo) {
      this.axios.post('/api/reserve/one',{isbn: bookinfo.isbn})
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
    },
    isReserve (bookinfo) {
      let tvm = this
      this.axios.post('/api/reserve/is_reserve',{isbn: bookinfo.isbn})
      .then(function (response) {
        tvm.reserved = response.data.success
      })
      .catch(function (err) {
        console.log(err)
      })
    },
    ...mapMutations('pageTransition', [  
            'setBookInfo'
        ]),
    getBook(isbn) {
      let tvm = this
      this.axios.post('/api/book/isbn',{isbn: isbn})
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
