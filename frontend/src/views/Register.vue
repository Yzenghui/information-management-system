<template>
  <div class="login-container">
    <div class="login-background"></div>
    <el-card class="login-card">
      <div class="login-header">
        <h2>用户注册</h2>
        <p>欢迎注册，请填写您的账号信息</p>
      </div>

      <!-- 角色选择标签页 -->
      <el-tabs v-model="registerType" class="register-tabs">
        <el-tab-pane label="学生注册" name="STUDENT"></el-tab-pane>
        <el-tab-pane label="教师注册" name="TEACHER"></el-tab-pane>
      </el-tabs>

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
        <el-link type="primary" @click="goToLogin">已有账号？立即登录</el-link>
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
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.registerForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      registerType: "STUDENT", // 注册类型
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
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
          {
            pattern: /^[a-zA-Z0-9_]+$/,
            message: "用户名只能包含字母、数字和下划线",
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
          {
            pattern: /^(?=.*[A-Za-z])(?=.*\d).*$/,
            message: "密码必须包含字母和数字",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { validator: validateConfirmPassword, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    async handleRegister() {
      this.$refs.registerFormRef.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          try {
            const response = await this.$http.post("/api/auth/register", {
              username: this.registerForm.username,
              password: this.registerForm.password,
              role: this.registerType,
            });

            const result = response.data;

            if (result.code == 200) {
              localStorage.setItem("token", result.data.token);
              localStorage.setItem("username", result.data.username);

              this.$message.success("注册成功！");

              this.$router.push("/");
            } else {
              this.$message.error(result.message || "注册失败");
            }
          } catch (error) {
            console.error("注册请求异常:", error);
            if (error.response) {
              const status = error.response.status;
              if (status === 400) {
                this.$message.error(
                  error.response.data.message || "用户名已存在或信息填写有误"
                );
              } else if (status === 401) {
                this.$message.error("登录已过期，请重新登录");
                this.$router.push("/login");
              } else if (status === 403) {
                this.$message.error("无权限访问，请联系管理员");
              } else if (status === 404) {
                this.$message.error("注册接口不存在，请检查后端地址");
              } else if (status === 500) {
                this.$message.error("服务器内部错误，请稍后重试");
              } else {
                this.$message.error(`请求失败 (${status})`);
              }
            } else if (error.code === "ECONNABORTED") {
              this.$message.error("请求超时，请检查网络");
            } else if (
              error.message &&
              error.message.includes("Network Error")
            ) {
              this.$message.error("网络异常，请确保后端服务已启动");
            } else {
              this.$message.error("注册失败，请稍后重试");
            }
          } finally {
            this.loading = false;
          }
        }
      });
    },

    goToLogin() {
      this.$router.push("/login");
    },
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