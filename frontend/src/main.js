import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.use(ElementUI)

// 设置后端 API 基础地址
axios.defaults.baseURL = 'http://localhost:8080'
// 将 axios 挂载到 Vue 原型链，组件内通过 this.$http 调用
Vue.prototype.$http = axios

// 关闭生产环境提示，开发环境不影响
Vue.config.productionTip = false

// 创建 Vue 根实例
new Vue({
  router,           // 注入路由，使整个应用支持路由
  render: h => h(App) // 渲染根组件 App 到 DOM
}).$mount('#app')   // 挂载到 index.html 的 <div id="app">
