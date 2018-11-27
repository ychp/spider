# 一、描述
简易版论坛系统，第一版为个人博客形式<br>
主要功能有用户、分类、文章标签、文章、点赞、收藏、评价、说说、相册等功能<br>
辅助功能有数据分析(PV/UV等),黑名单(IP限制),友情链接<br>
后续可继续迭代，网站访问分析，访问行为分析，推文等功能

# 二、功能
具体功能说明见 [Features.md](https://github.com/ychp/blog/wiki/feature)

# 三、模块说明
## 1、handlebars
模板工具模块：基于handlebars创建的模板渲染工具，用于定义常用的模板函数
具体函数说明见 [Handlebars.md](https://github.com/ychp/blog/wiki/handlebars)

## 2、code-builder
代码生成工具模块：基础的内容生成工具，仅支持模板渲染生成

## 3、common
通用工具模块：常用的工具类以及实体

## 4、mybatis
ORM模块：集成mybatis，支持spring-boot以及代码生成

## 5、msg
消息服务模块：集成各种消息服务，目前仅有邮件服务

## 6、cache
缓存服务模块：目前仅支持redis缓存

## 7、session
session模块，支持分布式session，应用启动时必须前后端一级域名一致