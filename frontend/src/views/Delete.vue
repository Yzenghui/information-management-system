<template>
  <div class="delete-page">
    <h2>信息删除</h2>
    
    <el-card class="delete-card">
      <el-form :model="deleteForm" :rules="rules" ref="deleteFormRef" label-width="120px" @submit.native.prevent>
        <!-- 类型选择 -->
        <el-form-item label="删除类型">
          <el-radio-group v-model="deleteForm.type">
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">教师</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 学号/工号 -->
        <el-form-item :label="deleteForm.type === 'student' ? '学号' : '工号'" prop="id">
          <el-input 
            v-model="deleteForm.id" 
            :placeholder="`请输入${deleteForm.type === 'student' ? '学号' : '工号'}`"
            style="width: 300px"
            @keyup.enter.native="handleDelete"
          ></el-input>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item>
          <!-- type="danger" - 按钮类型为"危险"（红色） -->
          <el-button type="danger" @click="handleDelete" :loading="loading">{{ loading ? '删除中...' : '删除' }}</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 删除确认对话框 -->
    <!-- :visible.sync="confirmVisible" - 控制对话框显示/隐藏
     visible - 对话框显示状态属性
     .sync - 双向绑定，对话框关闭时自动更新 confirmVisible 为 false
     confirmVisible - 控制变量，true时显示对话框 -->
    <el-dialog
      title="确认删除"
      :visible.sync="confirmVisible"  
      width="400px"
    >
      <p>确定要删除该条信息吗？此操作不可撤销。</p>
      <!-- slot="footer" - 指定插入到对话框底部区域的内容 -->
      <!-- @click="confirmVisible = false"：Vue允许在事件处理中直接写赋值语句 -->
      <div slot="footer">
        <el-button @click="confirmVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDelete" :loading="loading">确认删除</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "DeletePage",
  data() {
    return {
      deleteForm: {
        type: "student",
        id: ""
      },
      rules: {
        id: [{ required: true, message: "请输入学号/工号", trigger: "blur" }]
      },
      loading: false,
      confirmVisible: false
    };
  },
  methods: {
    handleDelete() {
      this.$refs.deleteFormRef.validate((valid) => {
        if (valid) {
          this.confirmVisible = true;
        }
      });
    },
    confirmDelete() {
      this.loading = true;
      // 模拟删除操作
      setTimeout(() => {
        this.loading = false;
        this.confirmVisible = false;
        this.$message.success("删除成功！");
        this.handleReset();
      }, 1000);
    },
    handleReset() {
      /* 清空所有表单字段的值，清除验证状态和错误提示，将表单恢复到初始状态 */
      this.$refs.deleteFormRef.resetFields(); 
      
      this.deleteForm.type = "student";
    }
  }
};
</script>

<style scoped>
.delete-page {
  padding: 20px;
}
.delete-card {
  margin-top: 20px;
}
</style>