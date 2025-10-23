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
        <el-table :data="studentList" v-loading="loading">
          <el-table-column prop="id" label="学号" width="120"></el-table-column>
          <el-table-column prop="name" label="姓名" width="100"></el-table-column>
          <el-table-column prop="gender" label="性别" width="80"></el-table-column>
          <!-- 没指定宽度的列自动平分剩余空间 -->
          <el-table-column prop="major" label="专业"></el-table-column>
          <el-table-column prop="address" label="籍贯"></el-table-column>
        </el-table>
      </el-tab-pane>
      
      <el-tab-pane label="教师信息" name="teachers">
        <el-table :data="teacherList" v-loading="loading">
          <el-table-column prop="id" label="工号" width="120"></el-table-column>
          <el-table-column prop="name" label="姓名" width="100"></el-table-column>
          <el-table-column prop="gender" label="性别" width="80"></el-table-column>
          <el-table-column prop="subject" label="所授学科"></el-table-column>
          <el-table-column prop="address" label="籍贯"></el-table-column>
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
    }
  },
  mounted() {
    this.loadData();
  }
};
</script>

<style scoped>
.browse-page {
  padding: 20px;
}
</style>