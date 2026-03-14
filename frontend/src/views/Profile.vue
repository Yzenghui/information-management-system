<template>
  <div class="profile-page">
    <h2>个人信息</h2>

    <el-card class="profile-card">
      <!-- 基本信息 -->
      <el-form
        :model="profileForm"
        :label-position="labelPosition"
        label-width="100px"
      >
        <el-form-item label="用户名">
          <!-- disabled 作用是禁用输入框 -->
          <el-input v-model="profileForm.username" disabled></el-input>
        </el-form-item>

        <el-form-item label="注册时间">
          <el-input v-model="profileForm.registerTime" disabled></el-input>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 修改密码 -->
    <el-card class="password-card">
      <!-- <div slot="header"> 是 Vue的插槽(slot)语法
      指定这个div要插入到el-card组件的header插槽位置 -->
      <div slot="header">
        <span>修改密码</span>
      </div>

      <el-form
        :model="passwordForm"
        :rules="passwordRules"
        :label-position="labelPosition"
        ref="passwordFormRef"
        label-width="100px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <!-- show-password 是显示密码可见性切换按钮 -->
          <el-input
            v-model="passwordForm.oldPassword"
            type="password"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="passwordForm.newPassword"
            type="password"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="passwordForm.confirmPassword"
            type="password"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item>
          <!-- 在此处，按钮对齐的是上面的标签 -->
          <el-button type="primary" @click="handleChangePassword"
            >修改密码</el-button
          >
          <el-button @click="handleResetPassword">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ProfilePage",
  data() {
    // 输入框值变化并触发验证时，会自动把当前值作为 value 参数传递进来
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };

    return {
      screenWidth: document.documentElement.clientWidth, // 添加屏幕宽度监听
      profileForm: {
        username: localStorage.getItem("username") || "用户",
        registerTime: "加载中...",
      },
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
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
  computed: {
    // 根据屏幕宽度动态调整标签位置
    labelPosition() {
      return this.screenWidth < 768 ? "top" : "right";
    },
  },
  methods: {
    async loadUserProfile() {
      try {
        const response = await this.$http.get("/api/user/profile");
        const result = response.data;

        if (result.code === 200) {
          this.profileForm.username = result.data.username;
          this.profileForm.registerTime = result.data.registerTime;
        } else {
          this.$message.error(result.message || "获取用户信息失败");
        }
      } catch (error) {
        console.error("获取用户信息异常:", error);
        if (error.response) {
          const status = error.response.status;
          if (status === 401) {
            this.$message.error("登录已过期，请重新登录");
            this.$router.push("/login");
          } else if (status === 403) {
            this.$message.error("无权限查看个人信息");
          } else if (status === 404) {
            this.$message.error("用户信息接口不存在");
          } else if (status === 500) {
            this.$message.error("服务器内部错误，请稍后重试");
          } else {
            this.$message.error(`获取用户信息失败 (${status})`);
          }
        } else if (error.code === "ECONNABORTED") {
          this.$message.error("请求超时，请检查网络");
        } else if (error.message && error.message.includes("Network Error")) {
          this.$message.error("网络异常，请稍后重试");
        } else {
          this.$message.error("获取用户信息失败，请稍后重试");
        }
      }
    },

    async handleChangePassword() {
      this.$refs.passwordFormRef.validate(async (valid) => {
        if (valid) {
          try {
            const response = await this.$http.put("/api/user/password", {
              oldPassword: this.passwordForm.oldPassword,
              newPassword: this.passwordForm.newPassword,
              confirmPassword: this.passwordForm.confirmPassword,
            });

            const result = response.data;

            if (result.code === 200) {
              this.$message.success("密码修改成功！");
              this.handleResetPassword();
            } else {
              this.$message.error(result.message || "密码修改失败");
            }
          } catch (error) {
            console.error("修改密码失败:", error);

            if (error.response) {
              const status = error.response.status;
              if (status === 400) {
                this.$message.error(
                  error.response.data.message || "原密码错误"
                );
              } else if (status === 401) {
                this.$message.error("登录已过期，请重新登录");
                this.$router.push("/login");
              } else if (status === 403) {
                this.$message.error("无权限修改密码");
              } else if (status === 404) {
                this.$message.error("修改密码接口不存在");
              } else if (status === 500) {
                this.$message.error("服务器内部错误，请稍后重试");
              } else {
                const msg =
                  error.response.data?.message || `密码修改失败 (${status})`;
                this.$message.error(msg);
              }
            } else if (error.code === "ECONNABORTED") {
              this.$message.error("请求超时，请检查网络");
            } else if (
              error.message &&
              error.message.includes("Network Error")
            ) {
              this.$message.error("网络异常，请确保后端服务已启动");
            } else {
              this.$message.error("密码修改失败，请稍后重试");
            }
          }
        }
      });
    },
    handleResetPassword() {
      this.$refs.passwordFormRef.resetFields();
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
    },
  },
  mounted() {
    // 添加窗口大小变化监听
    window.addEventListener("resize", this.handleResize);

    // 加载用户信息
    this.loadUserProfile();
  },
  beforeDestroy() {
    // 移除监听器，防止内存泄漏
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>

<style scoped>
.profile-page {
  padding: 20px;
}
.profile-card {
  margin-top: 20px;
  margin-bottom: 20px;
}
.password-card {
  margin-top: 20px;
}

/* 响应式样式 */
@media screen and (max-width: 768px) {
  .profile-page {
    padding: 15px;
  }
}

@media screen and (max-width: 480px) {
  .profile-page {
    padding: 10px;
  }
}
</style>