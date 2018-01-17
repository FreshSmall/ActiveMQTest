# ActiveMQTest
#### ActiveMQ的测试例子  
------------------
在该ActiveMQ的Demo中，有两种运用ActiveMQ的方式
 - spring boot集成ActiveMQ，代码实现在conponents包内
 - 采用ActiveMQ Client连接外置ActiveMQ服务器，代码实现在ActiveMQqueue和ActiveMQtopic包内，一个是queue模式一个是topic模式

在activeMQtopic包中增加了监听器的实例