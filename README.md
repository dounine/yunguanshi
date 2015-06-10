Extjs4.2MVC开源项目介绍


1：这个开源项目采用 Extjs4.2 +  Spring4.0 + SpringMVC + Hibernate4.3 + Shiro1.2.3 + mail1.4.1 + FusionCharts + 验证码  主要框架实现



2：数据库中的测试数据为全自动生成。



3：数据库软件为MySQL5.5、Servlet容器为Tomcat



5：因为程序依赖Extjs4.2、所以运行项目的之前先把 share/apache-tomcat-7.0.22 先启动、如果直接把Extjs4.2的文件直接放到里面IDE工具会显得非常卡、不利于开发。



6：项目访问路径：http://localhost:8080/yunguanshi/admin/index.html



7：数据库默认用户和密码都是：root 



8：数据库用户名和密码都采用加密的方式、如果不想启动加密

就把项目 config.properties 里面的 sys.commons.encrypt=yes 修改成 sys.commons.encrypt=no 即可



9：想要生成数据库密文密码在 com.yunguanshi.shiro 包下的 Encrypt.java 是用来专门生成密文的。



10：采用的Extjs4.2采用的是MVC模式实现的、Controller 与 View 都是动态加载进来、所以速度非常快。
