<template>
  <div>
    <v-layout row wrap>
      <v-flex v-for="(bookinfo,index) in bookinfos" :key="index" class="mt-2">
        <a-bookinfo :abookinfo="bookinfo" :status="1"></a-bookinfo>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import ABookinfo from '@/components/ABookinfo.vue'
export default {
  props: ['bookinfos'],
  components: {
    'a-bookinfo': ABookinfo
  }
}
</script>

<style>

</style>
