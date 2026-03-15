import Vue from "vue";
import VueRouter from "vue-router";
import UserLogin from "../views/Login.vue";

Vue.use(VueRouter);

const routes = [
  {
    // path和name是访问同一component的两种不同标识符。
    path: "/login",
    name: "login",
    component: UserLogin,
    // meta 字段用于存储路由的额外信息（如权限要求）
    // guestOnly: true 表示只有未登录的游客才能访问此页面
    meta: { guestOnly: true }
  },
  {
    path: "/register",
    name: "register",
    component: () => import("../views/Register.vue"),
    // 注册页同样只有游客才能访问
    meta: { guestOnly: true }
  },
  {
    path: "/",
    name: "layout",
    // @ 是 webpack 配置的别名，指向 /src
    component: () => import("@/views/Layout.vue"),
    // requiresAuth: true 表示此页面需要登录才能访问
    meta: { requiresAuth: true },
    // 嵌套路由
    children: [
      {
        // children的path是相对于父路径的，所以不要写开头斜杠
        path: "browse",
        name: "browse",
        component: () => import("@/views/Browse.vue"),
      },
      {
        path: "query",
        name: "query",
        component: () => import("@/views/Query.vue"),
      },
      {
        path: "add",
        name: "add",
        component: () => import("@/views/Add.vue"),
      },
      {
        path: "delete",
        name: "delete",
        component: () => import("@/views/Delete.vue"),
      },
      {
        path: "profile",
        name: "profile",
        component: () => import("@/views/Profile.vue"),
      },
    ],
  },
];

// 创建路由实例
const router = new VueRouter({
  mode: "history", // 使用 HTML5 History 模式（URL 没有 #）
  base: process.env.BASE_URL, // 基础路径，通常用于子目录部署
  routes, // 传入前面定义的路由配置
});

// 路由守卫 - 登录状态检查
// beforeEach 在每次页面跳转前都会执行，用来做权限检查
// to    : 用户要去的路由信息
// from  : 用户来自的路由信息
// next  : 控制函数，决定是否放行
router.beforeEach((to, from, next) => {
  // 从 localStorage 获取 token，判断用户是否已登录
  const token = localStorage.getItem('token')
  
  // 需要登录但无 token → 强制跳转到登录页
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } 
  // 已登录用户访问游客页面（如登录页、注册页）→ 强制跳转到首页
  else if (to.meta.guestOnly && token) {
    next('/')
  } 
  // 其他情况正常放行
  else {
    next()
  }
});

// 导出路由实例，让其他文件可以导入使用
export default router;