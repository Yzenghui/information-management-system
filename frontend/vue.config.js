// 导入 Vue CLI 的配置辅助函数，提供类型提示和代码智能补全
const { defineConfig } = require("@vue/cli-service");

// 导出Vue项目配置
module.exports = defineConfig({
  // 转译依赖：将node_modules中的第三方包转换为兼容性更好的代码
  transpileDependencies: true,

  // 公共资源路径 - 指定静态资源（JS/CSS/图片）的加载路径（npm run build 时生效）
  // "/" 表示网站根目录，浏览器会从此路径加载所有前端资源文件
  publicPath: "/",

  // 关闭保存时的 ESLint 检查，以提升开发流畅度
  lintOnSave: false,

  // 生产环境不生成 source map
  productionSourceMap: false,

  // 开发服务器配置
  devServer: {
    // 监听地址：'0.0.0.0'表示监听所有网络接口，允许外部设备访问
    // 默认值：'localhost'（仅本机可访问）
    host: "0.0.0.0",

    // 端口号：指定开发服务器运行的端口
    port: 8081,

    // 允许的主机：'all'表示接受任何域名的访问请求
    allowedHosts: "all",

    // 客户端配置（仅影响开发环境的 WebSocket 热更新连接）
    client: {
      // WebSocket连接URL：'auto://'前缀让系统自动选择安全协议
      // 在HTTPS环境下自动使用WSS（安全WebSocket），在HTTP下使用WS
      webSocketURL: "auto://0.0.0.0:0/ws",
    },

    // 开发环境 API 代理 - 解决跨域问题
    proxy: {
      "/api": {
        target: "http://localhost:8080",  // 后端服务器地址（开发环境）
        changeOrigin: true,               // 修改请求头 Host，伪装成同源请求
        secure: false,                    // 允许转发到 HTTP（非 HTTPS）后端
        ws: true,                         // 支持 WebSocket 协议转发
      },
    }
  },

  // 优化构建性能警告 - 调整 Webpack 文件大小限制阈值
  // 默认值 244KB 过小，ElementUI + Vue 等项目依赖很容易超过，产生大量无意义警告
  configureWebpack: {
    performance: {
      hints: false,              // 关闭性能警告（设为 "warning" 可保留警告）
      maxAssetSize: 512000,      // 单个文件最大 512KB（默认 244KB）
      maxEntrypointSize: 512000, // 入口文件总大小最大 512KB（默认 244KB）
    },
  },
});
