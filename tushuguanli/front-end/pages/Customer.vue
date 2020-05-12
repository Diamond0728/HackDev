<template>
  <div id="inspire" style="background-color:#eee;">
      
    <v-content>
        <router-view class="ma-2"></router-view>
    </v-content>
  </div>
</template>

<script>
  export default {
    data: () => ({
      drawer: null
    }),
    props: {
      source: String
    }
  }
</script>