import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from '@/router'

Vue.config.productionTip = false
Vue.prototype.user = {
  username: '',
}
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

export const trace = text => {
  text = text.trim();
  const now = (window.performance.now() / 1000).toFixed(3);

  console.log(now, text);
}

export const sleep =  ms =>  {
  return new Promise(resolve => setTimeout(resolve, ms))
}

