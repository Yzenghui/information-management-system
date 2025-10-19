import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserLogin from "../views/Login.vue"; 

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
    // meta 字段用于存储路由的额外信息（如权限要求）
    meta: { requiresAuth: true }, // 需要登录
  },
  {
    path: "/login",
    name: "login",
    component: UserLogin,
    meta: { requiresAuth: false }, // 不需要登录
  },
  {
    path: "/about",
    name: "about",
    // About 路由使用懒加载，优化性能
    component: () => import("../views/AboutView.vue"),
    meta: { requiresAuth: true },
  },
];

// 创建路由实例创建路由实例
const router = new VueRouter({
  mode: "history",            // 使用 HTML5 History 模式（URL 没有 #）
  base: process.env.BASE_URL, // 基础路径，通常用于子目录部署
  routes,                     // 传入前面定义的路由配置
});

// 路由守卫 - 登录状态检查
// beforeEach 在每次页面跳转前都会执行，用来做权限检查
// to    : 用户要去的路由信息
// from  : 用户来自的路由信息  
// next  : 控制函数，决定是否放行
router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  if (to.meta.requiresAuth && !isLoggedIn) {
    // 需要登录但未登录，跳转到登录页
    next("/login");
  } else if (to.path === "/login" && isLoggedIn) {
    // 已登录但访问登录页，跳转到首页
    next("/");
  } else {
    // 正常放行
    next();
  }
});

// 导出路由实例，让其他文件可以导入使用
export default router;
