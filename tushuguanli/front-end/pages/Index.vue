<template>
  <div>
    <v-layout row wrap>
      <v-flex xs12 md12 lg12 class="pa-1">
        <v-card>
           <h1>图书管理系统</h1>
           <h1>前端：Vue.js</h1>
           <h1>后端: Spring boot + Shiro + Mybatis</h1>
           
        </v-card>
      </v-flex>
    </v-layout>
    
  </div>
</template>

<script>
import AvgChart from '../components/AvgChart.vue'
import PriceMap from '../components/PriceMap.vue'
export default {
  components: {
    'avg-chart': AvgChart,
    'price-map': PriceMap
  }
}
</script>

