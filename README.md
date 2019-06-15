# ClockingIn
- 项目名称：手机考勤客户端
- 创建时间：2019.2.27
- 更新时间：2019.5.9|2019.6.1
-文档状态：编辑中

>网工三班第八组

### 主要内容
需要先连接server端，从server端拉去教师上课表。
老师使用该app可以实现学生上课打卡，身份认证，出勤统计并且数据可视化，并且可以根据调课来为每一节课增添课程。
最终目的达到解决学生上课时候的考勤问题。
### 总里程碑（包括client & server大体功能点）

|  编号  |目标|    完成情况    |
|----|----|----|
|V1.0|创建项目，完成主要界面。调用手机nfc功能，可以读出放在手机上面的卡片中的内容。创建数据库，录入数据。|已经完成√|
|V1.1|创建server，添加图形gui，当传感器连接到电脑时候可以打开本地com口来读取或写入传感器（控制步进电机转动）。|已经完成√|
|V1.2|连接手机端和server端，在同一个局域网内手机可以访问server端，发送数据以及控制传感器。|已经完成√|
|V1.3|添加老师课程，选择课程可以跳转界面进行签到，学生来刷卡实现简单的身份认证，限制打卡方式（一节课只能打一次等），统计打卡次数完成数据可视化数据。并且可以查看签到情况|已经完成√|
|V1.4|尝试加入AI元素，人脸识别，语音提示等。|进行中|
|V2.0|项目代码重构，eventbus，okhttp等|进行中|

### 程序截图
登陆界面，首先开启server端，获取server端的ip（cmd：ipconfig）,选择对应的教师以及课程时间段点击登陆
![image.png](https://upload-images.jianshu.io/upload_images/13139591-dfc04e506078def0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

转到设置，选择打开NFC功能
![image.png](https://upload-images.jianshu.io/upload_images/13139591-2a813c90770e7fe1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)

三个主界面一览（签到界面、上课记录查询界面、教师界面）
![image.png](https://upload-images.jianshu.io/upload_images/13139591-d7ab9a468a951079.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

签到流程1：首先选择课程，会从远程数据库拉取学生信息然后等待读卡，读到学生卡就会弹出对应dialog，然后进行之后的操作，可以标记旷课等。
![image.png](https://upload-images.jianshu.io/upload_images/13139591-4c335f14c7e7b2ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

签到流程2：点击签到则会显示签到成功，并且3s自动消失，中间界面可以查询是否上过。
![image.png](https://upload-images.jianshu.io/upload_images/13139591-3f88260656c63659.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

额外特色功能，可以向server端的窗口发送一条文本，点击按钮可以发送指令控制连接在server端的步进电机（模拟开关门）。
![image.png](https://upload-images.jianshu.io/upload_images/13139591-13cbc3b969583a24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
