<template>
  <div class="delete-page">
    <h2>信息删除</h2>

    <el-card class="delete-card">
      <el-form
        :model="deleteForm"
        :rules="rules"
        ref="deleteFormRef"
        :label-width="formLabelWidth"
        @submit.native.prevent
      >
        <!-- 类型选择 -->
        <el-form-item label="删除类型">
          <el-radio-group v-model="deleteForm.type">
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">教师</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 学号/工号 -->
        <el-form-item
          :label="deleteForm.type === 'student' ? '学号' : '工号'"
          prop="id"
        >
          <el-input
            v-model="deleteForm.id"
            :placeholder="`请输入${
              deleteForm.type === 'student' ? '学号' : '工号'
            }`"
            :style="inputStyle"
            @keyup.enter.native="handleDelete"
          ></el-input>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item class="button-container">
          <div class="button-group">
            <el-button type="danger" @click="handleDelete" :loading="loading">{{
              loading ? "删除中..." : "删除"
            }}</el-button>
            <el-button @click="handleReset">重置</el-button>
          </div>
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
      :width="dialogWidth"
    >
      <p>确定要删除该条信息吗？此操作不可撤销。</p>
      <!-- slot="footer" - 指定插入到对话框底部区域的内容 -->
      <!-- @click="confirmVisible = false"：Vue允许在事件处理中直接写赋值语句 -->
      <div slot="footer">
        <el-button @click="confirmVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDelete" :loading="loading"
          >确认删除</el-button
        >
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
        id: "",
      },
      rules: {
        id: [{ required: true, message: "请输入学号/工号", trigger: "blur" }],
      },
      loading: false,
      confirmVisible: false,
      screenWidth: document.documentElement.clientWidth, // 添加屏幕宽度监听
    };
  },
  computed: {
    // 响应式表单标签宽度
    formLabelWidth() {
      return this.screenWidth < 768 ? "80px" : "120px";
    },
    // 响应式输入框宽度
    inputStyle() {
      return {
        width: this.screenWidth < 768 ? "100%" : "300px",
      };
    },
    // 响应式对话框宽度
    dialogWidth() {
      return this.screenWidth < 768 ? "90%" : "400px";
    },
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
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
    },
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>

<style scoped>
.delete-page {
  padding: 20px;
}
.delete-card {
  margin-top: 20px;
}

.button-container {
  margin-bottom: 0;
}

.button-group {
  /* display: flex 创建了一个flex容器,
  默认的排列方向是 flex-direction: row（水平排列） */
  /* gap 是设置flex项目之间的间距 */
  display: flex;
  gap: 10px;
}

.button-group .el-button {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 响应式样式 */
@media screen and (max-width: 768px) {
  .delete-page {
    padding: 15px;
  }

  .button-group {
    flex-wrap: nowrap; /* 确保按钮不换行 */
  }

  .button-group .el-button {
    /* flex: 1 等同于 flex-grow: 1（ 可伸展 ）、flex-shrink: 1（ 可收缩 ）、
    flex-basis: 0（ 基础尺寸为0 ）
    核心作用就是：让元素等分父容器的可用空间*/
    flex: 1; /* 让两个按钮等宽 */
    min-width: 0; /* 允许按钮缩小 */
  }
}

@media screen and (max-width: 480px) {
  .delete-page {
    padding: 10px;
  }

  .button-group {
    gap: 8px; /* 在小屏幕上减小间距 */
  }
}
</style>
