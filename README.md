# **RoutingSimulator**

---



详细介绍项目的基本信息，包括项目简介、功能介绍、安装步骤、使用方法、运行环境要求等。例如说明项目是用于什么场景的软件、如何在本地克隆仓库并运行



## 项目结构

```text
│  pom.xml
│  README.md     
├─src
│  └─main
│      ├─java
│      │  │  module-info.java
│      │  │  
│      │  └─org
│      │      └─mirac
│      │          └─routingsimulator
│      │              │  RoutingSimulator.java
│      │              │  
│      │              ├─entity
│      │              │      DataPacket.java
│      │              │      RouteEntry.java
│      │              │      
│      │              ├─routers
│      │              │      Router.java
│      │              │      
│      │              ├─utils
│      │              │      DataUtils.java
│      │              │      IPUtils.java
│      │              │      RouterUtils.java
│      │              │      SimulationUtils.java
│      │              │      
│      │              └─view
│      │                      Mapview.java
│      │                      
│      └─resources
│          └─org
│              └─mirac
│                  └─routingsimulator
│                          hello-view.fxml
│                          
└─target
    ├─classes
    │  │  module-info.class
    │  │  
    │  └─org
    │      └─mirac
    │          └─routingsimulator
    │              │  hello-view.fxml
    │              │  RoutingSimulator.class
    │              │  
    │              ├─entity
    │              │      DataPacket.class
    │              │      RouteEntry.class
    │              │      
    │              ├─routers
    │              │      Router.class
    │              │      
    │              ├─utils
    │              │      DataUtils.class
    │              │      IPUtils.class
    │              │      RouterUtils.class
    │              │      SimulationUtils$1.class
    │              │      SimulationUtils$2.class
    │              │      SimulationUtils.class
    │              │      
    │              └─view
    │                      Mapview.class
    │                      
    └─generated-sources
        └─annotations
```









