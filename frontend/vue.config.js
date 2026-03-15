// 导入Vue CLI的defineConfig函数，用于类型安全的配置定义
const { defineConfig } = require('@vue/cli-service')

// 导出Vue项目配置
module.exports = defineConfig({
  // 转译依赖：将node_modules中的第三方包转换为兼容性更好的代码
  transpileDependencies: true,
  
  // 开发服务器配置
  devServer: {
    // 监听地址：'0.0.0.0'表示监听所有网络接口，允许外部设备访问
    // 默认值：'localhost'（仅本机可访问）
    host: '0.0.0.0',
    
    // 端口号：指定开发服务器运行的端口
    // 需要与ngrok转发端口保持一致
    port: 8081,
    
    // 允许的主机：'all'表示接受任何域名的访问请求
    // 解决：通过ngrok域名访问时的"Invalid Host header"错误
    allowedHosts: 'all',
    
    // 客户端配置
    client: {
      // WebSocket连接URL：'auto://'前缀让系统自动选择安全协议
      // 在HTTPS环境下自动使用WSS（安全WebSocket），在HTTP下使用WS
      // 解决：通过ngrok HTTPS访问时的"The operation is insecure"错误
      webSocketURL: 'auto://0.0.0.0:0/ws'
    }
  }
})