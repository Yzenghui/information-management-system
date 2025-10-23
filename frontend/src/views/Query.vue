<template>
  <div class="query-page">
    <h2>信息查询</h2>

    <!-- 搜索表单 -->
    <el-card class="search-card">
      <!-- @submit.native.prevent阻止默认的提交行为（页面刷新） -->
      <el-form :model="searchForm" @submit.native.prevent ref="searchFormRef">
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入姓名"
            style="width: 300px"
            @keyup.enter.native="handleSearch" 
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 搜索结果 -->
    <el-card class="result-card">
      <div slot="header">
        <span>查询结果</span>
      </div>
      <el-table :data="resultList" v-loading="loading">
        <el-table-column prop="id" label="学号/工号" width="120"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column prop="gender" label="性别" width="80"></el-table-column>
        <el-table-column prop="profession" label="身份" width="100">
          <template slot-scope="scope">
            {{ scope.row.profession === "学生" ? "学生" : "教师" }}
          </template>
        </el-table-column>
        <el-table-column prop="course" label="专业/学科"></el-table-column>
        <el-table-column prop="address" label="籍贯"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "QueryPage",
  data() {
    return {
      searchForm: {
        name: "",
      },
      loading: false,
      resultList: [],
    };
  },
  methods: {
    handleSearch() {
      this.loading = true;
      // 模拟查询
      setTimeout(() => {
        if (this.searchForm.name === "张三") {
          this.resultList = [
            {
              id: "2021001",
              name: "张三",
              gender: "男",
              profession: "学生",
              course: "计算机科学",
              address: "北京",
            },
          ];
        } else {
          this.resultList = [];
        }
        this.loading = false;
      }, 500);
    },
    handleReset() {
      this.searchForm.name = "";
      this.resultList = [];
    },
  },
};
</script>

<style scoped>
.query-page {
  padding: 20px;
}
.search-card {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
