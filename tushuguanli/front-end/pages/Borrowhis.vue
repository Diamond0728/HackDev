<template>
  <div>
    <v-card hover class="pa-2 ma-2" v-for="(item,index) in his" :key="index">
      <v-layout>
        <v-flex>
          书名：{{item.bookinfo.b_name}}
        </v-flex>
        <v-flex>
          书号：{{item.borrowReturn.b_id}}
        </v-flex>
        <v-flex>
          借书时间: {{timeFormat(item.borrowReturn.btime)}}
        </v-flex>
        <v-flex>
          还书时间: {{(item.borrowReturn.rtime!=null)?timeFormat(item.borrowReturn.rtime): '尚未归还'}}
        </v-flex>
      </v-layout>
    </v-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      his: []
    }
  },
  mounted  () {
    this.getHis();
  },
  methods: {
    timeFormat(tm) {     
      return new Date(parseInt(tm)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, "         ")
    },
    getHis () {
      let tvm = this
      this.axios.post('/api/borrow/his')
      .then(function (response) {
        if (response.status == 200){
          if(response.data.success) {
            tvm.his = response.data.data
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
