package com.ims.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用的主入口类。
 * 这个类的作用是启动整个Spring Boot应用，它是应用的起点。
 */
@SpringBootApplication
// 这是一个复合注解，包含了@Configuration, @EnableAutoConfiguration, @ComponentScan
// @Configuration 标记该类是一个Spring Boot的配置类
// @EnableAutoConfiguration 启用Spring Boot的自动配置机制（根据pom.xml的依赖自动配置应用）
// @ComponentScan 启用组件扫描，默认扫描当前包（com.ims.backend）及其所有子包下的Spring组件
public class BackendApplication {

	/**
	 * 应用的主入口方法，这是一个标准的Java main方法。
	 * 当运行这个类时，JVM会从这里开始执行。
	 *
	 * @param args 命令行参数，可用于在启动时传递配置
	 */
	public static void main(String[] args) {
		// 启动Spring Boot应用的核心静态方法
		// 参数1: 主配置类（通常就是当前类），Spring Boot会从这个类开始加载配置
		// 参数2: 命令行参数
		// 这个方法会：
		// 1. 创建Spring应用上下文（ApplicationContext）
		// 2. 加载所有通过@SpringBootApplication注解指定的配置
		// 3. 启动内嵌的Web服务器（如Tomcat，因为pom.xml中引入了spring-boot-starter-web）
		// 4. 使应用开始监听指定的端口（默认为8080），等待处理HTTP请求
		SpringApplication.run(BackendApplication.class, args);
	}
}