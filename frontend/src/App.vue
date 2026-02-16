<template>
  <div id="app">
    <!-- 路由出口：由路由配置决定渲染登录页、注册页或布局页 -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
/* 清除默认边距
   统一盒模型 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  /* 字体栈，按优先级从高到低排列 */
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    Arial, sans-serif;
  background-color: #f5f7fa;
}

/* 确保根容器占满视口 */
#app {
  min-height: 100vh;
}

</style>
