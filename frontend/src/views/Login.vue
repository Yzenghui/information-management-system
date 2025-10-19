<template>
  <div class="login-container">
    <div class="login-background"></div>

    <el-card class="login-card">
      <div class="login-header">
        <h2>信息管理系统</h2>
        <p>欢迎登录，请输入您的账号信息</p>
      </div>

      <!-- ref 是 Vue 的特殊属性
      用于注册对 DOM 元素或子组件的引用
      可以通过 this.$refs.名称 访问 -->
      <el-form
        :model="loginForm"
        :rules="loginRules"
        ref="loginFormRef"
        class="login-form"
      >
        <!-- prop 是 Element UI 表单中表单项的双向标识符
        核心作用：
        数据标识 - 指定该表单项对应数据对象的哪个字段
        规则关联 - 指定验证时使用哪组验证规则 -->
        <el-form-item prop="username">
          <!-- 自闭合标签内部除属性外不能有任何内容（包括注释） -->
          <!-- placeholder - 输入框提示文本（灰色提示，输入时消失） -->
          <!-- size="large" - 组件尺寸（大号输入框） -->
          <!-- prefix-icon - 前缀图标（输入框前面的用户图标） -->
          <!-- @keyup.enter - 事件监听（回车键按下时触发 handleLogin 方法） -->
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="el-icon-user"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item prop="password">
          <!-- type="password" - 输入框类型（设置为密码类型，输入时显示圆点掩码） -->
          <!-- show-password - Element UI 的专属属性
           显示密码可见性切换按钮（眼睛图标）
           点击可切换密码显示/隐藏-->
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="el-icon-lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <!-- type="primary" - 主要按钮样式（蓝色） -->
          <!-- size="large" - 大尺寸按钮 -->
          <!-- :loading="loading" - 绑定加载状态（true时显示加载图标） -->
          <!-- @click="handleLogin" - 点击触发登录方法 -->
          <!-- class="login-button" - 自定义样式类 -->
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleLogin"
            class="login-button"
          >
            {{ loading ? "登录中..." : "登录" }}
          </el-button>
        </el-form-item>

      </el-form>

      <div class="login-footer">
        <el-link type="primary" @click="goToRegister">
            还没有账号？立即注册
        </el-link>
      </div>

    </el-card>
  </div>
</template>

<script>
export default {
  name: "UserLogin",
  data() {
    return {
      loading: false,

      loginForm: {
        username: "",
        password: "",
      },

      loginRules: {
        username: [
          //required: true           检查：字段不能为空
          //message: "请输入用户名"   失败提示
          //trigger: "blur"          触发时机
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "用户名长度为 3 到 20 个字符",
            trigger: "blur", //失去焦点时触发触发验证
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "密码长度为 6 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    handleLogin() {
      // this 指向：当前 Vue 组件实例
      // $ 前缀：Vue 的实例属性/方法标识
      // validate(callback: Function)：来自 Element UI 的 Form 组件
      // callback: Function为回调函数
      // 箭头函数没有自己的 this，继承外层作用域的 this
      // 普通函数有自己的 this，在回调中被重置为 window
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
          this.loading = true;

          // 模拟登录API调用
          // setTimeout(callback: Function, delay: number)：JavaScript 原生定时器函数
          setTimeout(() => {         // 模拟网络延迟
            this.loading = false;    // 关闭加载状态

            // 模拟登录成功
            if (this.loginForm.username && this.loginForm.password) {
              this.$message.success("登录成功！"); // 绿色背景，成功图标
              // 在浏览器本地存储中设置登录标志（后续替换为真实token）
              localStorage.setItem("isLoggedIn", "true");
              // 保存当前登录的用户名到本地存储（密码永不存储在前端本地）
              localStorage.setItem("username", this.loginForm.username);
              
              // 跳转到首页
              this.$router.push("/");
            } else {
              this.$message.error("用户名或密码错误"); // 红色背景，错误图标
            }
          }, 1500);
        }

      });
    },

    goToRegister() {
      this.$message.info("注册功能开发中...");
      // 后续添加：this.$router.push('/register')
    },
  },

  mounted() {
    // 如果已登录，直接跳转到首页
    if (localStorage.getItem("isLoggedIn") === "true") {
      this.$router.push("/");
    }
  },
};
</script>

<style scoped>
/* position: relative（相对定位）为绝对定位的子元素提供定位参照 */
/* position: absolute（绝对定位）定位到最近的定位父元素 */

.login-container {
  min-height: 100vh;       /* 最小高度 = 整个视窗高度 */
  
  display: flex;           /* 弹性布局 */
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
  
  position: relative;      /* 为背景层提供定位基准 */
  background: #f5f7fa;   /* 备用背景色 */
}

.login-background {
  position: absolute;      /* 绝对定位，覆盖整个容器 */
  top: 0; 
  left: 0;
  right: 0; 
  bottom: 0; 
  
  background: linear-gradient(135deg, #13aabae7 0%, #764ba2 100%  );
  opacity: 0.8;            /* 80% 透明度 */
}

.login-card {
  width: 400px;            /* 80% 透明度 */
  padding: 40px 30px;      /* 内边距：上下 40px，左右 30px */
  position: relative;      /* 建立新的堆叠上下文 */
  z-index: 1;              /* 确保卡片在背景层之上 */
  box-shadow: 0 10px 30px rgba(7, 181, 161, 0.35) !important;/* 阴影 */
  border: none;            /* 无边框 */
  border-radius: 12px;     /* 圆角 */
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #303133;
  margin-bottom: 8px;
  font-size: 24px;
}

.login-header p {
  color: #747982;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.login-button {
  width: 100%;       /* 父容器为登陆卡片 */
  margin-top: 10px;  /* 上方留出10像素的间距 */
}

.login-footer {
  text-align: center;
  border-top: 1px solid #1753b2db;
  padding-top: 20px;
}
</style>
