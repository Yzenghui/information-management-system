<template>
  <div class="browse-page">
    <h2>信息浏览</h2>
    
    <!-- 选项卡切换学生/教师 -->
    <!-- activeTab 是当前选中选项卡的标识符，切换选项卡时，其值会自动改变
    当 activeTab 变化时：Element UI 自动显示对应的 el-tab-pane -->
    <!-- v-loading="loading" 是 Element UI 的特殊指令
    当 loading = true 时，表格显示加载动画
    当 loading = false 时，加载动画消失 -->
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="学生信息" name="students">
        <el-table 
          :data="studentList" 
          v-loading="loading"
          :size="tableSize"
        >
          <el-table-column prop="id" label="学号" :width="columnWidths.id"></el-table-column>
          <el-table-column prop="name" label="姓名" :width="columnWidths.name"></el-table-column>
          <el-table-column prop="gender" label="性别" :width="columnWidths.gender"></el-table-column>
          <el-table-column prop="major" label="专业" :width="columnWidths.major"></el-table-column>
          <el-table-column 
            prop="address" 
            label="籍贯" 
            :width="columnWidths.address"
          ></el-table-column>
        </el-table>
      </el-tab-pane>
      
      <el-tab-pane label="教师信息" name="teachers">
        <el-table 
          :data="teacherList" 
          v-loading="loading"
          :size="tableSize"
        >
          <el-table-column prop="id" label="工号" :width="columnWidths.id"></el-table-column>
          <el-table-column prop="name" label="姓名" :width="columnWidths.name"></el-table-column>
          <el-table-column prop="gender" label="性别" :width="columnWidths.gender"></el-table-column>
          <el-table-column prop="subject" label="所授学科" :width="columnWidths.major"></el-table-column>
          <el-table-column 
            prop="address" 
            label="籍贯" 
            :width="columnWidths.address"
          ></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "BrowsePage",
  data() {
    return {
      activeTab: "students",
      loading: false,
      screenWidth: document.documentElement.clientWidth, // 获取屏幕宽度用于响应式
      studentList: [
        {
          id: "2021001",
          name: "张三",
          gender: "男",
          major: "计算机科学",
          address: "北京"
        },
        {
          id: "2021002", 
          name: "李四",
          gender: "女", 
          major: "软件工程",
          address: "上海"
        }
      ],
      teacherList: [
        {
          id: "T1001",
          name: "王老师", 
          gender: "男",
          subject: "Java程序设计",
          address: "南京"
        }
      ]
    };
  },
  computed: {
    // 根据屏幕宽度动态计算表格尺寸
    tableSize() {
      return this.screenWidth < 768 ? 'small' : 'medium';
    },
    // 根据屏幕宽度动态计算列宽
    columnWidths() {
      // major、address两列自动平分剩余空间
      if (this.screenWidth < 480) {
        // 手机端：紧凑布局
        return {
          id: '100',
          name: '80', 
          gender: '60',
          major: '',
          address: ''
        };
      } else if (this.screenWidth < 768) {
        // 平板端：适中布局
        return {
          id: '110',
          name: '90',
          gender: '70', 
          major: '',
          address: ''
        };
      } else {
        // 电脑端：宽松布局
        return {
          id: '120',
          name: '100',
          gender: '80',
          major: '',
          address: ''
        };
      }
    }
  },
  methods: {
    handleTabClick() {
      // 切换选项卡时可以重新加载数据
      this.loadData();
    },
    loadData() {
      this.loading = true;
      // 模拟数据加载
      setTimeout(() => {
        this.loading = false;
      }, 500);
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
    }
  },
  mounted() {
    this.loadData();
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
.browse-page {
  /* 根据屏幕宽度动态调整内边距 */
  padding: 20px;
}

/* 响应式样式 */
@media screen and (max-width: 768px) {
  .browse-page {
    padding: 15px;
  }
}

@media screen and (max-width: 480px) {
  .browse-page {
    padding: 10px;
  }
}
</style>