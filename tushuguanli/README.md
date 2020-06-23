> 这是我写的一个图书管理系统，希望对你有帮助。

> 已有三个用户：网站管理员 `admin`，图书管理员`test`，普通用户`test2`，密码都是`123`

- 点击右上角 `star` 按钮可以收藏，等到需要的时候再下载。

- 点击左上角 `code`， 再点击 `clone or download`可以下载.

- 实在不行可以看[讲解视频](#讲解视频)

[后端如何跑起来？](#后端如何跑起来？)

[前端如何跑起来？](#前端如何跑起来？)

[讲解视频](#讲解视频)

# 后端如何跑起来？
数据库:mysql navicat  workbench

后端框架:spring boot

前端框架:vue lavas vuetify

ide: idea

数据库sql文件：根目录下library.sql
### 1、安装IntelliJ IDEA
> https://www.jetbrains.com/idea/

### 2、用IntelliJ IDEA 打开 本文件夹
>第一次加载可能要很久，因为要下载maven依赖
>注意右下角有个 2 process running之类的，这个消失了才能运行

### 3、运行
>点击工具栏的运行按钮即可
>如果工具了运行按钮是灰色的，右键点src/main/java/com.group2.project/ProjectApplication,点run

### 4、技术栈
#### spring-boot 
#### mybatis
>如何优雅的使用mybatis
>http://www.ityouknow.com/springboot/2016/11/06/springboot(六)-如何优雅的使用mybatis.html
#### shiro
>shiro注解权限控制-5个权限注解https://blog.csdn.net/w_stronger/article/details/73109248

# 前端如何跑起来？

> 这是一个 Lavas PWA 项目

### 1、安装Node.js
https://nodejs.org/en/<br>
安装LTS版，不要安装current版<br>
安装过程一直next<br>
### 2、安装npm国内镜像
安装好node.js后，新打开一个命令行<br>
输入 npm install -g cnpm --registry=https://registry.npm.taobao.org<br>
安装过程卡住的时候按一下回车就可以了<br>
安装完cnpm后，npm的命令都可以用cnpm来代理<br>

### 3、安装依赖
命令行输入
cd front-end<br>
cnpm install<br>

### 4、安装lavas
~~cnpm install lavas -g~~ 现在不用了

### 5、调试项目
cd front-end<br>
npm run  dev<br>

### 6、打开浏览器
地址：localhost:8888<br>
运行前端的同时最好也运行后端<br>

### 7、每次新拉下项目的时候都要执行一次cnpm install

### 8、相关文档
vue.js:https://cn.vuejs.org/<br>
axios:https://www.jianshu.com/p/df464b26ae58<br>
vuetify:https://vuetifyjs.com/zh-Hans/getting-started/quick-start<br>
lavas: https://lavas.baidu.com/guide/v2/basic/introduction<br>
一个推荐的vue全家桶教程:http://jspang.com/，往下面翻一点可以找到vue全家桶教程<br>

vue-router:https://router.vuejs.org/zh-cn/
vuex:https://vuex.vuejs.org/zh-cn/


# 讲解视频

关注公众号「HackDev」，回复「图书」获取讲解视频


![](../images/qrcode.jpg)