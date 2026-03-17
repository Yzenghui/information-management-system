import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.use(ElementUI)

// 设置后端 API 基础地址 - 根据环境自动切换
axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL || ''

// 添加请求拦截器 - 自动在每个请求中携带 Token
axios.interceptors.request.use(
    config => {
        // 从 localStorage 获取 token
        const token = localStorage.getItem('token')
        
        // 如果有 token，添加到请求头
        if (token) {
            // []：访问对象属性的另一种方式（等价于 .Authorization）
            // ``：模板字符串，${} 自动插入变量值（等价于 'Bearer ' + token）
            config.headers['Authorization'] = `Bearer ${token}`
        }
        
        return config
    },
    error => {
        // 拦截器出错时，阻止请求发送
        return Promise.reject(error)
    }
)

// 将 axios 挂载到 Vue 原型链，组件内通过 this.$http 调用
Vue.prototype.$http = axios

// 关闭生产环境提示，开发环境不影响
Vue.config.productionTip = false

// 创建 Vue 根实例
new Vue({
  router,           // 注入路由，使整个应用支持路由
  render: h => h(App) // 渲染根组件 App 到 DOM
}).$mount('#app')   // 挂载到 index.html 的 <div id="app">
