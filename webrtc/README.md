# 如何使用

数据库:mysql

后端框架:spring + springMVC + MyBatis 

前端框架: vue-cli (JavaScript + html + css)

ide: IntelliJ IDEA

技术桟：  Sql, JAVA, JavaScript, html, css, WebSocket, WebRTC ....

重点难点： WebSocket, WebRTC

## 后端
### 0、 安装JDK
> 请安装jdk 8u191及其以上版本

> 试过了 8u164都不行

> 8u164的问题在于，会把字符串类型的JSON变量看作JSON对象


### 1、安装IntelliJ IDEA
> https://www.jetbrains.com/idea/

### 2、用IntelliJ IDEA 打开 本文件夹
>第一次加载可能要很久，因为要下载maven依赖

>注意右下角有个 2 process running之类的，这个消失了才能运行

### 3、运行
>点击工具栏的运行按钮即可

>如果工具了运行按钮是灰色的，右键点src/main/java/com.group2.project/ProjectApplication,点run


## 前端
### 0、安装node.js
> https://nodejs.org/zh-cn/

> 选LTS版

### 1、安装npm淘宝镜像
> cmd: npm install -g cnpm --registry=https://registry.npm.taobao.org

> 安装完后，所有的npm命令都用cnpm代替

> 如果全局翻墙了可以省略这一步，也不用cnpm

### 2、安装yarn

> cmd: cnpm install yarn -g

### 3、为前端项目安装依赖
> cmd进入前端项目根目录下

> CMD: yarn install

### 4、运行
> cmd进入前端项目根目录下

> CMD: yarn serve

