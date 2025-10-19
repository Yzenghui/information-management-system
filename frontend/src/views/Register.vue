<template>
  <div class="login-container">
    <div class="login-background"></div>
    <el-card class="login-card">
      <div class="login-header">
        <h2>用户注册</h2>
        <p>欢迎注册，请填写您的账号信息</p>
      </div>

      <el-form
        :model="registerForm"
        :rules="registerRules"
        ref="registerFormRef"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="请输入用户名"
            size="large"
            prefix-icon="el-icon-user"
            @keyup.enter.native="handleRegister"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            prefix-icon="el-icon-lock"
            show-password
            @keyup.enter.native="handleRegister"
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            placeholder="请确认密码"
            size="large"
            prefix-icon="el-icon-lock"
            show-password
            @keyup.enter.native="handleRegister"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleRegister"
            class="login-button"
          >
            {{ loading ? "注册中..." : "注册" }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <el-link type="primary" @click="goToLogin"
          >已有账号？立即登录</el-link
        >
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "UserRegister",
  data() {
    // 定义一个自定义验证函数，用于确认密码字段
    // rule: 验证规则配置对象（包含required、min等配置）
    // value: 当前表单字段的值（用户输入的确认密码）
    /* callback: 回调函数，用于返回验证结果
        验证通过时，调用 callback()
        验证失败时，调用 callback(new Error('错误提示信息'))，参数是一个 Error 对象 */
    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      registerForm: {
        username: "",
        password: "",
        confirmPassword: ""
      },
      registerRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "用户名长度为 3 到 20 个字符",
            trigger: "blur",
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
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
      },
    };
  },
  methods: {
    handleRegister() {
      this.$refs.registerFormRef.validate((valid) => {
        if (valid) {
          this.loading = true;

          // 模拟注册API调用
          setTimeout(() => {
            this.loading = false;

            // 模拟注册成功
            if (this.registerForm.username && this.registerForm.password) {
              this.$message.success("注册成功！");
              
              // 注册成功后自动登录
              localStorage.setItem("isLoggedIn", "true");
              localStorage.setItem("username", this.registerForm.username);

              // 跳转到首页
              this.$router.push("/");
            } else {
              this.$message.error("注册失败，请重试");
            }
          }, 1500);
          
        }
      });
    },

    goToLogin() {
      this.$router.push("/login");
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
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background: #f5f7fa;
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #13aabae7 0%, #764ba2 100%);
  opacity: 0.8;
}

.login-card {
  width: 400px;
  padding: 40px 30px;
  position: relative;
  z-index: 1;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: none;
  border-radius: 12px;
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
  color: #909399;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.login-button {
  width: 100%;
  margin-top: 10px;
}

.login-footer {
  text-align: center;
  border-top: 1px solid #ebeef5;
  padding-top: 20px;
}
</style>