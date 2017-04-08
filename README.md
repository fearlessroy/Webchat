#web_message/liulishuo_test

=== summary:
* 整体使用MVC架构，使用拦截器对用户进行登录认证，数据库使用Mysql,前端使用velocity模版，事件使用redis队列。

#finished:
* 用户可以注册、登录。需要 id（可以自己决定 email 或者 username）和 password
* 用户登录后，进入联系人列表页面
    - 可以看到自己所有的联系人
    - 每个联系人需要显示对方 id 以及未读私信数量提醒
    - 用户可以通过 id 添加新联系人（可以不需要对方同意）
    - 用户可以删除某个联系人，但保留与对方用户的消息等数据。当再次添加新联系人时，消息等数据都还在
* 点击一个联系人会进入聊天界面，同时未读消息置为 0- 可以看到和某个用户的历史消息
    - 能够在这里收发私信（不需要实时，可以刷一下页面才看到新消息）
    - 当用户 A 发私信给用户 B 时，如果 A 还不是 B 的联系人，应该自动把 A 添加为 B 的联系人，并能够在 B 的联系人列表正常显示（不需要实时）
    - 用户可以删除自己发的消息

#defects:
* 信息暂未实时更新
* 前端功力有限，界面样式相对比较简单

#Design:

  ##1.前后端：

  ###frontend:velocity
  
  ###backend:Java SpringBoot
  
  ###database:Mysql
  
##2.数据库设计（详情见src/test/resources/init-scgema.sql）：
  
  ###User表：
   
   id int auto_increment,
   
   username vachar(64),
   
   password varchar(128),
   
   salt varcahr(32),
   
   head_url varchar(256)

  ###contacts表：
   
   id int aotu_increment,
   
   user_id varchar(64),
   
   contacts_id varchar(128)

  ###message表：
   
   id int auto_increment,
   
   to_id int,
   
   from_id int,
   
   content text,
   
   created_time datatime,
   
   has_read int,
   
   conversation_id varchar(45),
   
   has_del int

  ###login_ticket表：
   
   id int auto_increment,
   
   user_id int,
   
   ticket varchar(45),
   
   expired datetime,
   
   status int
   
   
##3.路由:

###注册：/reg/?username=&password=
 
###登录：/login/?username=&&password=  登录是会下发ticket并设置时间，登录成功后显示联系人列表，location是首页地址 /
 
###登出：/logout/
 
###添加联系人：/addContacts/?targetuserId=
 
###删除联系人：/delContacts/?targetuserId=
 
###发送消息：/msg/sendMessage/?targetuserId=&&content=
 
###删除消息：/msg/delMessage/?messageId=
 
###消息列表：/chats/?userId=&&contactsId=&&conversationId=
 
###(所有权限操作必须用户登录)
  
##4.Coding:
 
###model:各个数据模型
 
###dao层：直接操控底层数据库
 
###service:业务逻辑，使用dao层的接口
 
###controller:显示逻辑，将显示信息传递给volecity模版，并使用service层接口
 
###interceptor：拦截器，负责处理请求之前(检测ticket是否过期，是否登录，设置当前用户hostholder信息)，请求之后(hostholder.clear),面向整个请求过程
 
###async:一个简单的异步处理模块，eventproducer负责将event lpush入redis,eventconsumer实现一个线程，不断从redis中brpop,简单写了一个loginexception发邮件的事件

 
##5.Test
  对数据库值进行了简单的单元测试
  
 
 
