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
    meta: { requiresAuth: false }, // 不需要登录
  },
  {
    path: "/register",
    name: "register",
    component: () => import("../views/Register.vue"),
    meta: { requiresAuth: false }, // 不需要登录
  },
  {
    path: "/",
    name: "layout",
    // @ 是 webpack 配置的别名，指向 /src
    component: () => import("@/views/Layout.vue"),
    meta: { requiresAuth: true }, // 需要登录
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
    ],
  },
];

// 创建路由实例创建路由实例
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
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  if (to.meta.requiresAuth && !isLoggedIn) {
    // 需要登录但未登录，跳转到登录页
    next("/login");
  } else if ((to.path === "/login" || to.path === "/register") && isLoggedIn) {
    // 已登录但访问登录页或注册页，跳转到首页
    next("/");
  } else {
    // 正常放行
    next();
  }
});

// 导出路由实例，让其他文件可以导入使用
export default router;
