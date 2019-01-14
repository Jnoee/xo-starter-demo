import Vue from 'vue'
import ElementUI from 'element-ui'
import App from './App'
import router from './router'
import store from './store'
import axios from 'axios'

import 'normalize.css/normalize.css' 
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import '@/icons' 
import '@/permission'
import directives from "./directive"

import { getToken, getCookies } from '@/bin/utils/auth'

Vue.prototype.$http = axios
Vue.config.productionTip = false

Vue.use(directives);
Vue.use(ElementUI)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
