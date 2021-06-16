# EducationalPlatform 

# 介绍
此项目为巩固springboot基础而作，无参照项目，编码风格沿袭[myBlog](https://github.com/ynkm121/myBlog)

# 功能
- 基本的注册登入登出，通过学号/工号长度匹配学生，教师，管理员
- 学生选课，查看课件，查看课程广播消息
- 教师上传课件，广播消息，教学评分
- 管理员对课程、学生、教师进行增删查改，批准注册

# 技术栈
- 基本结构：Springboot + Mybatis + Thymeleaf
- 前端交互thymeleaf，美化使用基于bootstrap的模板[AdminLTE](https://github.com/ColorlibHQ/AdminLTE)
- 认证授权使用shiro，对三种角色通过不同realm验证，并将结果缓存于redis，提高授权响应速度
- 后台使用Springboot连接Mysql完成数据验证及存储
