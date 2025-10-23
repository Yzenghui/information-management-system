<template>
  <el-container>
    <!-- 侧边栏 -->
    <!-- width写在属性可优于组件默认CSS与自定义CSS 显示，避免被覆盖 -->
    <el-aside width="200px" class="sidebar">
      <!-- router：启用vue-router导航，点击菜单自动跳转 -->
      <!-- index：指定跳转的路由路径，对应router/index.js中的path -->
      <!-- i标签是Element UI的图标组件 -->
      <el-menu router class="menu">
        <el-menu-item index="/browse">
          <i class="el-icon-tickets"></i>
          <span>信息浏览</span>
        </el-menu-item>

        <el-menu-item index="/query">
          <i class="el-icon-search"></i>
          <span>信息查询</span>
        </el-menu-item>

        <el-menu-item index="/add">
          <i class="el-icon-circle-plus"></i>
          <span>信息录入</span>
        </el-menu-item>

        <el-menu-item index="/delete">
          <i class="el-icon-delete"></i>
          <span>信息删除</span>
        </el-menu-item>

        <el-menu-item index="/profile">
          <i class="el-icon-user-solid"></i>
          <span>个人信息</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶部导航 -->
      <el-header class="header">
        <div class="header-left">
          <span>信息管理系统</span>
        </div>

        <div class="header-right">
          <span>欢迎，{{ username }}</span>
          <el-button @click="logout">退出</el-button>
        </div>
      </el-header>

      <el-main>
        <div class="welcome-page" v-if="$route.path === '/'">
          <h1>欢迎使用信息管理系统</h1>
          <p>请从左侧菜单选择功能</p>
        </div>
        <router-view v-else></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "AppLayout",
  data() {
    return {
      username: localStorage.getItem("username"),
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("isLoggedIn");
      localStorage.removeItem("username");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
/* 不加scoped：编译后变成全局CSS，所有组件都能用到 */
/* 加scoped：编译后添加唯一属性选择器，只对当前组件生效 */
.sidebar {
  background: #304156;
  height: 100vh;
}

.menu {
  /* 移除菜单边框 */
  border: none;
  /* 设置深灰色背景 */
  background: #304156;
}

.menu .el-menu-item {
  /* 设置菜单项文字为浅灰色 */
  color: #bfcbd9;
}

.menu .el-menu-item:hover {
  /* 鼠标悬停时背景变深灰色 */
  background: #263445;
}

.menu .el-menu-item.is-active {
  /* 当前选中菜单项背景变蓝色，文字变白色 */
  background: #409eff;
  color: white;
}

.header {
  /* 135deg 是渐变角度，表示从左上角到右下角 */
  /* 0deg - 从下到上  90deg - 从左到右  180deg - 从上到下 */
  background: linear-gradient(135deg, #335c85 0%, #66b1ff 100%);

  /* 参数依次为：水平偏移（左右）、垂直偏移（上下）、
  模糊半径（越大越模糊）、 扩散半径（阴影大小）、
  颜色和透明度 */
  box-shadow: 0 2px 12px 0 rgba(105, 182, 244, 0.89);

  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: space-between; /*左右两端对齐，中间平均分布 */

  padding: 0 30px;
  height: 60px;
}

.header-left span {
  color: white;
  font-size: 20px;
  font-weight: bold;

  /* 参数依次为：水平偏移（文字右侧阴影）、垂直偏移（文字下方阴影）、
  模糊半径（阴影模糊程度）、阴影颜色 */
  text-shadow: 0 1px 2px rgb(35, 45, 241);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px; /* 子元素之间的间距 */
}

.header-right span {
  color: white;
  font-size: 14px;
}

.header-right .el-button {
  color: #409eff;
  background: white;
  border: none;
  border-radius: 4px; /* 圆角大小 */
  padding: 8px 16px; /* 字体粗细 */
  font-weight: 500;
}

/* 鼠标悬停状态 */
.header-right .el-button:hover {
  background: #f5f7fa;
  transform: translateY(-1px); /* 垂直向上移动1像素，产生上浮效果 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.welcome-page {
  text-align: center;
  padding: 100px 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin: 20px;
}

.welcome-page h1 {
  color: #409eff;
  font-size: 36px;
  margin-bottom: 20px;
}

.welcome-page p {
  color: #606266;
  font-size: 16px;
}
</style>
