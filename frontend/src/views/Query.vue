<template>
  <div class="query-page">
    <h2>信息查询</h2>

    <!-- 搜索表单 -->
    <el-card class="search-card">
      <!-- @submit.native.prevent阻止默认的提交行为（页面刷新）
      单输入框表单：只有一个输入框时，回车触发表单提交（浏览器默认）,需要 @submit.native.prevent 阻止刷新
      多输入框表单：有多个输入框时，回车触发切换到下一个输入框（浏览器默认）,不需要阻止默认行为 -->
      <!-- :style 是 Vue的动态样式绑定 -->
      <el-form :model="searchForm" @submit.native.prevent ref="searchFormRef">
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入姓名"
            :style="{ width: inputWidth }"
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
      <el-table :data="resultList" v-loading="loading" :size="tableSize">
        <el-table-column
          prop="id"
          label="学号/工号"
          :width="columnWidths.id"
        ></el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          :width="columnWidths.name"
        ></el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          :width="columnWidths.gender"
        ></el-table-column>
        <el-table-column
          prop="profession"
          label="身份"
          :width="columnWidths.profession"
        >
          <!-- 
            【作用域插槽示例 - 当前使用prop自动填充】
            简化写法：<el-table-column prop="profession" label="身份"></el-table-column>

            完整插槽写法：
            <template slot-scope="scope">
              slot-scope="scope" - 获取当前行的数据作用域
              scope.row - 当前行的数据对象  
              scope.row.profession - 当前行的profession字段值
              {{ scope.row.profession === "学生" ? "学生" : "教师" }}
            </template>

            使用场景：
            - 简单字段显示 → 直接用prop属性
            - 复杂渲染逻辑 → 使用作用域插槽
            -->
        </el-table-column>
        <el-table-column
          prop="course"
          label="专业/学科"
          :width="columnWidths.course"
        ></el-table-column>
        <el-table-column
          prop="address"
          label="籍贯"
          :width="columnWidths.address"
        ></el-table-column>
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
      screenWidth: document.documentElement.clientWidth, // 获取屏幕宽度用于响应式
    };
  },
  computed: {
    // 根据屏幕宽度动态计算表格尺寸
    tableSize() {
      return this.screenWidth < 768 ? "small" : "medium";
    },
    // 根据屏幕宽度动态计算输入框宽度
    inputWidth() {
      if (this.screenWidth < 480) {
        return "100%"; // 手机端全宽度
      } else if (this.screenWidth < 768) {
        return "250px"; // 平板端适中宽度
      } else {
        return "300px"; // 电脑端原始宽度
      }
    },
    // 根据屏幕宽度动态计算列宽
    columnWidths() {
      if (this.screenWidth < 480) {
        // 手机端：紧凑布局
        return {
          id: "100",
          name: "80",
          gender: "60",
          profession: "80",
          course: "",
          address: "",
        };
      } else if (this.screenWidth < 768) {
        // 平板端：适中布局
        return {
          id: "110",
          name: "90",
          gender: "70",
          profession: "90",
          course: "",
          address: "",
        };
      } else {
        // 电脑端：宽松布局
        return {
          id: "120",
          name: "100",
          gender: "80",
          profession: "100",
          course: "",
          address: "",
        };
      }
    },
  },
  methods: {
    async handleSearch() {
      this.loading = true;

      try {
        // 构建查询参数
        const params = {
          name: this.searchForm.name,
        };

        //调用后端查询API
        const response = await this.$http.get("/api/search", { params });
        const result = response.data;

        if (result.code === 200) {
          this.resultList = result.data || [];

          if (this.resultList.length === 0) {
            this.$message.info("未找到匹配的结果");
          }
        } else {
          this.$message.error(result.message || "查询失败");
          this.resultList = [];
        }
      } catch (error) {
        console.error("查询请求异常:", error);

        if (error.response) {
          const status = error.response.status;
          if (status === 401) {
            this.$message.error("登录已过期，请重新登录");
            this.$router.push("/login");
          } else if (status === 404) {
            this.$message.error("查询接口不存在");
          } else {
            this.$message.error(`查询失败 (${status})`);
          }
        } else if (error.message && error.message.includes("Network Error")) {
          this.$message.error("网络异常，请确保后端服务已启动");
        } else {
          this.$message.error("查询失败，请稍后重试");
        }

        this.resultList = [];
      } finally {
        this.loading = false;
      }
    },
    handleReset() {
      this.searchForm.name = "";
      this.resultList = [];
    },
    // 监听窗口大小变化
    handleResize() {
      this.screenWidth = document.documentElement.clientWidth;
    },
  },
  mounted() {
    // 添加窗口大小变化监听
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    // 移除监听器，防止内存泄漏
    window.removeEventListener("resize", this.handleResize);
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

/* 响应式样式 */
@media screen and (max-width: 768px) {
  .query-page {
    padding: 15px;
  }
}

@media screen and (max-width: 480px) {
  .query-page {
    padding: 10px;
  }

  .search-card {
    margin-top: 15px;
    margin-bottom: 15px;
  }
}
</style>