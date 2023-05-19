# HandList

HandList is a Simple task and project management app which have a [backend](https://github.com/Azathoth1729/HandList-Server)

## Features

### 任务管理

+ 查看任务：用户可以查看自己所拥有的任务列表，以及该任务的所属项目。通过任务名称，开始/截止日期等属性可以对任务进行搜索和筛选。
+ 追踪任务进度：任务列表通过任务的状态进行分类，让管理者/用户清楚的知道自己还有那些任务未完成、正在进行、已经完成或者是被放弃。
+ 发布任务：管理者可以随时随地创建任务，为任务设置名称、描述、开始/截止日期，状态等属性。
+ 分配任务：管理者用户可以指定任务给相关人员执行任务。
+ 修改、删除任务：管理者可以随时更新任务的名称、描述、开始/截止日期，还可以随时删除之前发布的任务。

### 项目管理

+ 创建项目：项目是任务的容器，用来更好的聚合、管理任务，并向管理层反馈项目的进度。管理者可以随时创建一个项目。
修改、删除项目：管理者可以更新项目名称，层次位置等属性。
+ 分解项目：项目管理者可以在一个项目中创建多个子项目/任务，在子项目中也能嵌套地创建多个子项目/任务。
+ 指定项目成员：管理者指定一个项目所需要的成员并进行增删查改。
+ 移动和复制任务/项目：为了减少用户进行重复性操作，在一个项目中的任务/项目可以被直接移动或者复制到其他项目中，该任务的指派人员可以在移动或复制的过程修改或者直接保持不变。

### 用户管理

+ 用户登录/注册：用户可以通过注册功能创建新账号并通过账号登录系统。
+ 个人中心：用户可以查看自己的账号信息，修改密码，更新个人信息等。

## Usage

### Requirements

+ [backend](https://github.com/Azathoth1729/HandList-Server)
+ JDK version >= 17
+ Android Studio

### Build from source

+ Build and make [backend](https://github.com/Azathoth1729/HandList-Server) run
+ Build and run app in Android Studio
