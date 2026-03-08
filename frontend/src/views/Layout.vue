<template>
  <el-container>
    <!-- 侧边栏 -->
    <!-- width写在属性可优于组件默认CSS与自定义CSS 显示，避免被覆盖 -->
    <!-- 
      动态class绑定说明：
      1. 启用：添加 :class="{ 'is-collapse': isCollapse }"
      （当 isCollapse 为 true 时，为元素添加 is-collapse 这个class）
      2. 配合CSS中 .sidebar:not(.is-collapse) 的固定定位
      3. 实现移动端侧边栏覆盖效果
      4. 当前使用流式布局，故注释掉此动态class
      -->
    <el-aside
      :width="asideWidth"
      class="sidebar"
    >
      <!-- 侧边栏折叠箭头 - 中部位置 -->
      <div class="sidebar-collapse" @click="toggleCollapse">
        <i
          :class="isCollapse ? 'el-icon-arrow-right' : 'el-icon-arrow-left'"
        ></i>
      </div>

      <!-- router：启用vue-router导航，点击菜单自动跳转 -->
      <!-- :collapse-transition="false" 禁用折叠动画 -->
      <!-- index：指定跳转的路由路径，对应router/index.js中的path -->
      <!-- i标签是Element UI的图标组件 -->
      <!-- 添加菜单选择事件 -->
      <el-menu
        router
        class="menu"
        :collapse="isCollapse"
        :collapse-transition="false"
        @select="handleMenuSelect"
      >
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
          <!-- 汉堡菜单按钮，点击切换侧边栏折叠状态 -->
          <el-button
            icon="el-icon-s-fold"
            @click="toggleCollapse"
            class="collapse-btn"
          ></el-button>
          <span class="header-title">信息管理系统</span>
        </div>

        <div class="header-right">
          <span class="welcome-text">{{ welcomeText }}</span>
          <el-button @click="logout" class="logout-btn">退出</el-button>
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
      isCollapse: false, // 控制侧边栏折叠状态
      screenWidth: document.documentElement.clientWidth, // 获取浏览器窗口可视区域的宽度
    };
  },
  // computed 一般用来包裹依赖响应式数据的计算属性
  computed: {
    // 根据折叠状态动态计算侧边栏宽度
    asideWidth() {
      return this.isCollapse ? "64px" : "200px";
    },
    // 根据屏幕宽度动态显示欢迎语
    welcomeText() {
      if (this.screenWidth < 480) {
        return this.username; // 超小屏幕只显示用户名
      } else if (this.screenWidth < 768) {
        return `欢迎，${this.username}`; // 小屏幕显示完整欢迎语
      } else {
        return `欢迎，${this.username}`; // 大屏幕显示完整欢迎语
      }
    },
  },
  methods: {
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      this.$router.push("/login");
    },
    // 切换侧边栏折叠状态
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
      // 当屏幕宽度小于 768px 时，自动折叠侧边栏
      if (this.screenWidth < 768) {
        this.isCollapse = true;
      } else {
        this.isCollapse = false;
      }
    },
    // 菜单选择事件 - 小屏幕下选择功能后自动收起侧边栏
    handleMenuSelect() {
      if (this.screenWidth < 768) {
        this.isCollapse = true;
      }
    },
  },
  mounted() {
    // 组件挂载时执行一次，根据初始屏幕宽度决定侧边栏状态
    this.handleResize();
    // 监听浏览器窗口的大小改变事件，当窗口大小变化时自动调用handleResize方法
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    // 组件销毁前移除监听器，防止内存泄漏
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>

<style scoped>
/* 不加scoped：编译后变成全局CSS，所有组件都能用到 */
/* 加scoped：编译后添加唯一属性选择器，只对当前组件生效 */
.sidebar {
  background: #304156;
  height: 100vh;
  transition: width 0.3s; /* 添加宽度过渡动画 */
  position: relative; /* 为折叠箭头定位 */
}

/* 侧边栏折叠箭头 - 中部明显箭头 */
.sidebar-collapse {
  position: absolute;
  right: 0; /* 紧贴侧边栏右边缘 */
  top: 50%; /* 顶部在侧边栏中点 */
  transform: translateY(-50%); /* 上移一半自身高度，实现完美居中 */

  width: 20px;
  height: 60px;
  background: transparent; /* 默认透明背景 */
  border: 2px solid #606266; /* 灰色边框，区别于背景 */
  border-radius: 10px 0 0 10px;
  border-right: none; /* 去掉右侧边框 */

  display: flex;
  align-items: center;
  justify-content: center;

  cursor: pointer; /* 鼠标悬停时显示手型光标 */
  z-index: 1002; /* 设置层级高度，确保箭头显示在其他元素之上 */
  color: #606266; /* 灰色箭头 */
  font-size: 16px; /* 箭头图标的大小 */
  font-weight: bold; /* 设置箭头图标为粗体 */
  transition: all 0.3s; /* 为所有CSS属性添加过渡动画 */
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
}

.sidebar-collapse:hover {
  background: #409eff; /* 悬停时变蓝色背景 */
  border-color: #409eff; /* 悬停时边框变蓝色 */
  color: white; /* 悬停时箭头变白色 */
  width: 24px;
  box-shadow: -3px 0 12px rgba(0, 0, 0, 0.3);
}

.menu {
  /* 移除菜单边框 */
  border: none;
  /* 设置深灰色背景 */
  background: #304156;
  height: 100%;
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
  background: linear-gradient(135deg, #335c85 0%, #66b1ff 100%);

  /* 参数依次为：水平偏移、垂直偏移、模糊半径、扩散半径、颜色和透明度 */
  box-shadow: 0 2px 12px 0 rgba(105, 182, 244, 0.89);

  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: space-between; /*左右两端对齐，中间平均分布 */

  padding: 0 15px; /* 上下0, 左右15px */
  height: 60px;
  min-width: 320px; /* 设置最小宽度防止过度压缩 */
}

.header-left {
  display: flex;
  align-items: center;
  flex-shrink: 0; /* 防止标题区域被压缩 */
  min-width: 0; /* 允许内容压缩 */
}

.header-left .header-title {
  color: white;
  font-size: 18px; /* 统一字体大小 */
  font-weight: bold;

  white-space: nowrap; /* 防止文字换行 */
  overflow: hidden; /* 隐藏溢出内容 */
  text-overflow: ellipsis; /* 超出显示省略号 */

  /* 参数依次为：水平偏移、垂直偏移、模糊半径、阴影颜色 */
  text-shadow: 0 1px 2px rgb(35, 45, 241);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px; /* 子元素之间的间距 */
  flex-shrink: 1; /* 允许右侧区域适当压缩 */
  min-width: 0; /* 允许内容压缩 */
}

.header-right .welcome-text {
  color: white;
  font-size: 14px;
  white-space: nowrap; /* 防止文字换行 */
  overflow: hidden; /* 隐藏溢出内容 */
  text-overflow: ellipsis; /* 超出显示省略号 */
  max-width: 120px; /* 限制最大宽度 */
}

.header-right .logout-btn {
  color: #409eff;
  background: white;
  border: none;
  border-radius: 4px; /* 圆角大小 */
  padding: 6px 12px; /* 上下6px, 左右12px */
  font-weight: 500;
  font-size: 12px; /* 减小字体 */
  white-space: nowrap;
  flex-shrink: 0; /* 按钮不被压缩 */
}

/* 鼠标悬停状态 */
.header-right .logout-btn:hover {
  background: #f5f7fa;
  transform: translateY(-1px); /* 垂直向上移动1像素，产生上浮效果 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.welcome-page {
  text-align: center;
  padding: 100px 20px; /* 上下100px, 左右20px */
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

/* 响应式样式 */
/* 当屏幕宽度小于 768px（平板/手机）时 */
@media screen and (max-width: 768px) {
/* 
【覆盖式侧边栏 - 移动端适配方案】
作用：小屏幕时侧边栏固定定位，覆盖在内容上层
效果：节省空间但会遮挡部分头部内容
使用场景：需要最大化内容空间的移动端应用
  @media screen and (max-width: 768px) {
    .sidebar:not(.is-collapse) {
    position: fixed; 
    left: 0;
    top: 0;
    bottom: 0;
    z-index: 1001;
    }
  }
  .sidebar:not(.is-collapse)意为选择所有class包含sidebar但class不包含is-collapse的元素
*/

  /* 小屏幕时箭头保持显示但调整大小 */
  .sidebar-collapse {
    width: 18px;
    height: 50px;
    font-size: 14px;
  }

  .header {
    padding: 0 12px; /* 上下0, 左右12px */
  }

  .header-left .header-title {
    font-size: 16px; /* 小屏幕适当减小字体 */
    max-width: 150px; /* 限制标题最大宽度 */
  }

  .header-right .welcome-text {
    font-size: 12px;
    max-width: 80px;
  }
}

/* 当屏幕宽度小于 480px（手机）时 */
@media screen and (max-width: 480px) {
  .header {
    padding: 0 8px; /* 上下0, 左右8px */
  }

  .header-left .header-title {
    font-size: 14px;
    max-width: 120px;
  }

  .header-right .welcome-text {
    max-width: 60px;
  }
}

/* 汉堡菜单按钮样式 */
.collapse-btn {
  margin-right: 8px;
  border: none;
  background: transparent;
  color: white;
  font-size: 16px;
  padding: 8px;
  flex-shrink: 0;
}

/* 大屏幕时隐藏顶部汉堡按钮 */
@media screen and (min-width: 769px) {
  .collapse-btn {
    display: none;
  }
}
</style>
