<template>
  <div class="profile-page">
    <h2>个人信息</h2>
    
    <el-card class="profile-card">
      <!-- 基本信息 -->
      <el-form :model="profileForm" label-width="100px">
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
      <div slot="header">
        <span>修改密码</span>
      </div>
      
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <!-- show-password 是显示密码可见性切换按钮 -->
          <el-input v-model="passwordForm.oldPassword" type="password" show-password></el-input>
        </el-form-item>
        
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password></el-input>
        </el-form-item>
        
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password></el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
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
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    };

    return {
      profileForm: {
        username: localStorage.getItem("username") || "用户",
        registerTime: "2024-01-01" // 模拟数据
      },
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 6, max: 20, message: "密码长度 6-20 个字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { validator: validateConfirmPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    handleChangePassword() {
      this.$refs.passwordFormRef.validate((valid) => {
        if (valid) {
          // 模拟修改密码
          this.$message.success("密码修改成功！");
          this.handleResetPassword();
        }
      });
    },
    handleResetPassword() {
      this.$refs.passwordFormRef.resetFields();
    }
  }
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
</style>