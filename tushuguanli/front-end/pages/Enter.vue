<template>
  <div>
    <v-layout row wrap>
      <v-text-field solo label="输入ISBN号" v-model="isbn"></v-text-field>
      <v-btn color="green" dark @click="find(isbn)">查询</v-btn>
    </v-layout>
    <v-card hover class="mt-2" v-if="!isExist">
      <v-layout row wrap class="pa-2">
        <v-flex xs12>
          <v-text-field v-model="bookinfo.isbn" disabled label="isbn"></v-text-field>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="bookinfo.b_name" label="书名"></v-text-field>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="bookinfo.author" label="作者"></v-text-field>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="bookinfo.publish" label="出版商"></v-text-field>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="bookinfo.pubdate" label="出版年月"></v-text-field>
        </v-flex>
        <v-flex xs12>
          <v-btn color="green" dark @click="addBookInfo()" block>新增图书记录</v-btn>
        </v-flex>
      </v-layout>
    </v-card>
    <v-card hover class="mt-2" v-if="isExist">
      <v-layout row wrap class="pa-2">
        <v-flex sm6 xs12>
          <v-text-field v-model="bookinfo.isbn" disabled label="isbn"></v-text-field>
        </v-flex>
        <v-flex sm6 xs12>
          <v-text-field v-model="bookinfo.b_name" disabled  label="书名"></v-text-field>
        </v-flex>
        <v-flex sm6 xs12>
          <v-text-field v-model="bookinfo.author" disabled  label="作者"></v-text-field>
        </v-flex>
        <v-flex sm6 xs12>
          <v-text-field v-model="bookinfo.publish" disabled  label="出版商"></v-text-field>
        </v-flex>
        <v-flex sm6 xs12>
          <v-text-field v-model="bookinfo.pubdate" disabled  label="出版年月"></v-text-field>
        </v-flex>
        <v-flex sm6 xs12>
          <v-layout row wrap >
            <v-btn flat>新增</v-btn>
            <v-text-field v-model="n" @input="addnum(n)"></v-text-field>
            <v-btn flat>本</v-btn>
          </v-layout>
        </v-flex>
      </v-layout>
    </v-card>
    <add-book v-for="(book,index) in books" :key="index" :book="book" class="mt-2"></add-book>
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
import AddBook from '@/components/AddBook.vue'
export default {
  components: {
    'add-book': AddBook
  },
  methods: {
    ...mapMutations('pageTransition', [  
        'setUser'
    ]),
    addBookInfo () {
      let tvm = this
      this.axios.post('/api/enter/add/bookinfo',tvm.bookinfo)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            tvm.open("新增成功")
            tvm.isExist = true
          } else {
            tvm.open("新增失败"); 
          }
        } else {
          tvm.open('username or password wrong')
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    },
    find (str) {
      let tvm = this
      this.axios.get('/api/enter/find?isbn='+str)
      .then(function (response) {
        if (response.status == 200){
          if(response.data.data!=null) {
            tvm.bookinfo = response.data.data
            tvm.isExist = true
          } else {
            tvm.bookinfo = { 
              "b_name": "", 
              "author": null, 
              "publish": null, 
              "pubdate": null, 
              "number": 0, 
              "agent_id": tvm.storeUser.u_id, 
              "isbn": str
            },
            tvm.isExist = false
            tvm.open("该书的书目尚未建立，请手动编写建立")
          }
        } else {
          tvm.open('username or password wrong')
        }
      })
      .catch(function (err) {
        console.log(err)
      })
    },
    addnum (num) {
      let tvm = this
      tvm.books = []
      for(var i=0; i<num; i++) {
        tvm.books.push({b_id:'', isbn:tvm.isbn, location:''})
      }
    },
    open (msg) {
      this.dialogmsg = msg
      this.dialog = true
    },
    close () {
      this.dialog = false
    },
  },
  data () {
    return {
      isbn: '',
      isExist: false,
      dialog: false,
      dialogmsg: '',
      n: 0,
      bookinfo: { 
        "b_name": "", 
        "author": "", 
        "publish": "", 
        "pubdate": "", 
        "number": 0, 
        "agent_id": 0, 
        "isbn": "" 
      },
      books: [
      ]
    }
  },
  computed: {
    ...mapState('pageTransition', {
        storeUser: state => state.user,
    }),
  },
}
</script>
