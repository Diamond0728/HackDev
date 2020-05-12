<template>
  <div>
    <v-card class="pa-2" hover>
      <v-layout row="" wrap="">
        <v-flex>
          <v-text-field disabled label="ISBN" v-model="abookinfo.isbn"></v-text-field>
        </v-flex>
        <v-flex>
          <v-text-field disabled label="书名" v-model="abookinfo.b_name"></v-text-field>
        </v-flex>
        <v-flex>
          <v-text-field disabled label="作者" v-model="abookinfo.author"></v-text-field>
        </v-flex>
        <v-flex>
          <v-text-field disabled label="数量" v-model="abookinfo.number"></v-text-field>
        </v-flex>
        <v-flex>
          <v-text-field disabled label="出版日期" v-model="abookinfo.pubdate"></v-text-field>
        </v-flex>
        <v-flex>
          <v-text-field disabled label="出版社" v-model="abookinfo.publish"></v-text-field>
        </v-flex>
        <!-- <v-flex>
          <v-btn flat outline color="green" @click="$router.push('/books/'+abookinfo.isbn)">查看详情</v-btn>
        </v-flex> -->
      </v-layout>
      <v-layout>
        <v-flex>
          <v-btn v-if="status==1" flat outline block="" color="green" @click="showDetail(abookinfo)">查看详情</v-btn>
        </v-flex>
      </v-layout>
    </v-card>
  </div>
</template>


<script>
import {mapState, mapActions, mapMutations} from 'vuex';
export default {
  props: ['abookinfo',"status"],
  computed: {
    ...mapState('pageTransition', {
            pageTransitionType: state => state.type,
            pageTransitionEffect: state => state.effect,
            storeUser: state => state.user,
            storeBookinfo: state => state.bookinfo,
        }),
  },
  methods: {
    ...mapMutations('pageTransition', [  
            'setBookInfo'
        ]),
    showDetail(abookinfo) {
      this.setBookInfo(abookinfo)
      this.$router.push('/books/'+abookinfo.isbn)
    }
  }
}
</script>

<style>

</style>
