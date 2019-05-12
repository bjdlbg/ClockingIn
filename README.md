# ClockingIn
- 项目名称：手机考勤客户端
- 创建时间：2019.2.27
- 上次更新时间：2019.5.9
-文档状态：编辑中

>网工三班第八组

### 主要内容
需要先连接server端，从server端拉去教师上课表。
老师使用该app可以实现学生上课打卡，身份认证，出勤统计并且数据可视化，并且可以根据调课来为每一节课增添课程。
最终目的达到解决学生上课时候的考勤问题。
### 总里程碑（包括client & server）

|  编号  |目标|    完成情况    |
|----|----|----|
|V1.0|创建项目，完成主要界面。调用手机nfc功能，可以读出放在手机上面的卡片中的内容。创建数据库，录入数据。|已经完成√|
|V1.1|创建server，添加图形gui，当传感器连接到电脑时候可以打开本地com口来读取或写入传感器（控制步进电机转动）。|已经完成√|
|V1.2|连接手机端和server端，在同一个局域网内手机可以访问server端，发送数据以及控制传感器。|已经完成√|
|V1.3|添加老师课程，选择课程可以跳转界面进行签到，学生来刷卡实现简单的身份认证，限制打卡方式（一节课只能打一次等），统计打卡次数完成数据可视化数据。并且可以查看签到情况|进行中|
|V1.4|尝试加入AI元素，人脸识别，语音提示等。|进行中|


