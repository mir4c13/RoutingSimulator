# **RoutingSimulator**

---

## 项目简介

本项目是在基于CIDR的情况下，实现IP数据包分组转发的模拟，实现了自定义数据包模拟、添加路由等功能



## 项目结构

```text
├─pom.xml											//spring配置文件
├─README.md     
├─src
│  └─main
│      ├─java
│      │  │  
│      │  └─org
│      │      └─mirac
│      │          └─routingsimulator
│      │              │  RoutingSimulator.java		//UI启动类
│      │              │  
│      │              ├─entity
│      │              │      DataPacket.java		//数据包实体类
│      │              │      RouteEntry.java		//路由实体类
│      │              │      
│      │              ├─routers
│      │              │      Router.java			//路由表类
│      │              │  
|	   |              ├─test
│      │              ├─utils
│      │              │      DataUtils.java			//数据方法类
│      │              │      IPUtils.java			//IP方法类
│      │              │      RouterUtils.java		//路由方法类
│      │              │      SimulationUtils.java	//模拟方法类
│      │              │      
│      │              └─view
│      │                      Mapview.java			//UI视图类
│      │                      
│      └─resources
│                          
└─target
```



## 功能介绍

这个项目只实现了日志模拟，并未实现真实发包。在代码中编写逻辑进行转发的模拟。实现了默认的模拟、自定义数据包模拟以及添加路由的功能。用户可以自行添加路由，来更加深入地了解基于CIDR的IP分组转发过程。

## 安装步骤

本项目只使用了javafx。因此只需要将pom.xml进行maven的配置导入即可。



## 使用方法

运行RoutingSimulator.java中的main方法即可运行本项目。



## 运行环境要求

根据pom.xml的配置要求配置即可。





