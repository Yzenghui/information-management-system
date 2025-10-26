<template>
  <div class="add-page">
    <h2>信息录入</h2>
    
    <el-card class="add-card">
      <!-- label-width：Element UI 表单属性，设置所有表单项标签的宽度 -->
      <!-- label-position：Element UI 表单属性，设置标签的位置 -->
      <el-form 
        :model="addForm" 
        :rules="rules" 
        ref="addFormRef" 
        :label-width="labelWidth"
        :label-position="labelPosition"
      >
        <!-- 类型选择 -->
        <el-form-item label="人员类型">
          <el-radio-group v-model="addForm.type">
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">教师</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 学号/工号 -->
        <el-form-item label="学号/工号" prop="id">
          <el-input 
            v-model="addForm.id" 
            :size="inputSize"
            placeholder="请输入学号或工号" 
            @keyup.enter.native="handleSubmit"
          ></el-input>
        </el-form-item>

        <!-- 姓名 -->
        <el-form-item label="姓名" prop="name">
          <el-input 
            v-model="addForm.name" 
            :size="inputSize"
            placeholder="请输入姓名" 
            @keyup.enter.native="handleSubmit"
          ></el-input>
        </el-form-item>

        <!-- 性别 -->
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="addForm.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 动态字段：专业/学科 -->
        <el-form-item 
          v-if="addForm.type === 'student'"  
          label="专业" 
          prop="major"
        >
          <el-input 
            v-model="addForm.major" 
            :size="inputSize"
            placeholder="请输入专业" 
            @keyup.enter.native="handleSubmit"
          ></el-input>
        </el-form-item>
        <el-form-item 
          v-else 
          label="所授学科" 
          prop="subject"
        >
          <el-input 
            v-model="addForm.subject" 
            :size="inputSize"
            placeholder="请输入所授学科" 
            @keyup.enter.native="handleSubmit"
          ></el-input>
        </el-form-item>

        <!-- 籍贯 -->
        <el-form-item label="籍贯" prop="address">
          <el-input 
            v-model="addForm.address" 
            :size="inputSize"
            placeholder="请输入籍贯" 
            @keyup.enter.native="handleSubmit"
          ></el-input>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item>
          <el-button 
            type="primary" 
            :size="buttonSize"
            @click="handleSubmit"
          >提交</el-button>
          <el-button 
            :size="buttonSize"
            @click="handleReset"
          >重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "AddPage",
  data() {
    return {
      screenWidth: document.documentElement.clientWidth, // 获取屏幕宽度用于响应式
      addForm: {
        type: "student", // student/teacher
        id: "",
        name: "",
        gender: "男",    // gender对应的是选中项的label 值
        major: "",
        subject: "", 
        address: ""
      },
      rules: {
        id: [{ required: true, message: "请输入学号/工号", trigger: "blur" }],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        major: [{ required: true, message: "请输入专业", trigger: "blur" }],
        subject: [{ required: true, message: "请输入所授学科", trigger: "blur" }],
        address: [{ required: true, message: "请输入籍贯", trigger: "blur" }]
      }
    };
  },
  computed: {
    // 根据屏幕宽度动态计算标签宽度
    labelWidth() {
      if (this.screenWidth < 480) {
        return '80px'; // 手机端标签宽度
      } else if (this.screenWidth < 768) {
        return '90px'; // 平板端标签宽度
      } else {
        return '100px'; // 电脑端标签宽度
      }
    },
    // 根据屏幕宽度动态计算标签位置
    //top为标签在上方，right为标签在右侧
    labelPosition() {
      return this.screenWidth < 768 ? 'top' : 'right'; 
    },
    // 根据屏幕宽度动态计算输入框尺寸
    inputSize() {
      return this.screenWidth < 768 ? 'small' : 'medium';
    },
    // 根据屏幕宽度动态计算按钮尺寸
    buttonSize() {
      return this.screenWidth < 768 ? 'small' : 'medium';
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.addFormRef.validate((valid) => {
        if (valid) {
          // 模拟提交
          this.$message.success("录入成功！");
          this.handleReset();
        }
      });
    },
    handleReset() {
      this.$refs.addFormRef.resetFields();
      this.addForm.type = "student";
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
    }
  },
  mounted() {
    // 添加窗口大小变化监听
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    // 移除监听器，防止内存泄漏
    window.removeEventListener('resize', this.handleResize);
  }
};
</script>

<style scoped>
.add-page {
  padding: 20px;
}
.add-card{
    margin-top: 20px;
}

/* 响应式样式 */
@media screen and (max-width: 768px) {
  .add-page {
    padding: 15px;
  }
}

@media screen and (max-width: 480px) {
  .add-page {
    padding: 10px;
  }
  
  .add-card {
    margin-top: 15px;
  }
}
</style>