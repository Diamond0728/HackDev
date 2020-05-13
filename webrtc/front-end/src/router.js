import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/index'
import login from '@/views/Login'


Vue.use(Router)

export default new Router({
  // mode: 'history',
  routes: [
    {
      path: '/',
      component: index
    },
    {
      path: '/index',
      component: index
    },
    {
      path: '/login',
      component: login
    }
  ]
})
