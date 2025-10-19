<template>
  <div id="app">
    <!-- 登录页面独立显示，其他页面显示完整布局 -->
    <router-view v-if="$route.path === '/login'" />
    <div v-else class="main-layout">
      <!-- 这里后续添加导航栏、侧边栏等公共布局 -->
      <router-view />
    </div>
    
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

/* 确保布局区域也占满视口 */
.main-layout {
  min-height: 100vh;
}
</style>
