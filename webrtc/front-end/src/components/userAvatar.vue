<template>
  <div>
    <v-btn v-if="size=='small'" fab dark small depressed :color="color[i % color.length]">
    {{word}}
    </v-btn>
    <v-btn v-if="size!='small' && size !='large'" fab dark  depressed :color="color[i % color.length]">
    {{word}}
    </v-btn>
    <v-btn v-if="size=='large'" fab dark large depressed :color="color[i % color.length]">
    {{word}}
    </v-btn>
    
  </div>
</template>

<script>
export default {
  props: ['size', 'i', 'word'],
  data() {
    return {
      color: [
        "primary",
        "pink",
        "indigo",
        "teal",
        "cyan",
        "purple"
      ]
    }
  }
}
</script>

