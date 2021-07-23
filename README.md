# rabbitmq-study
 
   ##针对方案：  
 
    服务端：配置确认消息发送方式保证消息正常投递  
    消费端：设置自动确认，异常重试次数自定义，异常情况增加日志表通过定时任务进行重新投递进行补偿  
   
   ## 消息处理幂等解决方案
      使用redisson方式进行处理

   ##消息实体字段


     MessageContent{
   
         private String messageId; 消息ID
     
         private String businessLine; 所属业务线 记录日志使用
     
         private Boolean firstTreatment; 首次处理 默认 True
     
         private String messageContent; 业务消息内容 业务处理使用
         
     }
     
   ##实现流程:

   ####提前请知晓：
   此Demo仅考虑消费者自动确认情况，手动确认可自行改造，难度并不大(相信你可以的)
   
       消费成功最后增加 asyncExecuteLog(Message message,Boolean bool) 方法
       消费失败通过: RejectAndDontRequeueRecoverer 消费者所有重试失败进行执行 asyncExecuteLog(Message message,Boolean bool) 方法
       

   #可能出现的情况分析

   ####一、 消息者首次接收到消息重试所有次数后执行成功
   
     执行 asyncExecuteLog(...)方法 不会新增 log 信息
     原因：message中内容 firstTreatment 为True , status为True  asyncExecuteLog方法中进行判断此种情况不进行处理,具体详见项目代码中查看

   ####二、消费者首次接收到消息重试所有次数后执行失败

     调用 执行 RejectAndDontRequeueRecoverer 对象中asyncExecuteLog(...)方法 会新增 log 信息 
     原因: message中内容 firstTreatment 为True , status为False 此种情况会进行新增
   ####三、定时任务补偿（此处不进行分析、单体 微服务项目，又包含公司的原因大不相同，此Demo使用 spring中@Scheduled() 方式）

     具体获取重发数据此处不做详解
     消息重新投递 重发消息略有不同 message中内容 firstTreatment 为False,其余信息不变，投递成功之后，修改表数据信息processStatus 为False 此处修改的原因:防止定时任务重复投递，
     避免占用mq资源
   ####四、消费者获取定时任务投递的消息 执行成功

      执行 asyncExecuteLog(...)方法 修改 log 信息 ,firstTreatment为 False , status为True，修改数据表中的数据重试状态为成功，客户执行状态为成功，并增加重试次数（此处的次数不考虑消费者失败重试次数，只考虑接收到队列中同一消息的次数）
   ####五、 消费者获取定时任务投递的消息 执行失败

      执行 asyncExecuteLog(...)方法 修改 log 信息 ,firstTreatment为 False , status为True，修改数据表中的数据重试状态为失败，客户执行状态为成功，并增加重试次数（此处的次数不考虑消费者失败重试次数，只考虑接收到队列中同一消息的次数）
   ####六、超过自定义重试次数

      转人工处理，处理完成并更改对日志进行修改并备注原因
  
  
  
   #####后续考虑

   增加队列预警，平衡生产者 入队数据  与消费者消费数据的速度的平衡，防止mq宕机造成的服务不可用
