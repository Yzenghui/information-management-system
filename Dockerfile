# =====================================================
# 多阶段构建：前端 + 后端 → 单一 Spring Boot 可执行 JAR
# =====================================================

# ---------- 阶段 1: 构建前端 (Vue) ----------
FROM node:18-alpine AS frontend-builder
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# ---------- 阶段 2: 构建后端 (Spring Boot) + 合并 ----------
FROM maven:3.9-eclipse-temurin-21 AS backend-builder
WORKDIR /app/backend
COPY backend/pom.xml ./
RUN mvn dependency:go-offline -B
COPY backend/src ./src
# 将前端构建产物复制到 Spring Boot 的 static 目录
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static
RUN mvn clean package -DskipTests -B

# ---------- 阶段 3: 运行阶段 (最小化镜像) ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# 从构建阶段复制打包好的 JAR
COPY --from=backend-builder /app/backend/target/*.jar app.jar
# 暴露端口
EXPOSE 8080
# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]