package com.tengyue360.mq.send;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.bean.SsMqPushLog;
import com.tengyue360.constant.FanoutExancheConstant;
import com.tengyue360.dao.SsMqPushLogMapper;
import com.tengyue360.mq.topic.message.MessageTemplate;
import com.tengyue360.utils.CommonBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送mq to server
 *
 * @author xuliang 2018/08/12
 */
@Service
public class SendMqMessageByTopic implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    private static Logger logger = LoggerFactory.getLogger(SendMqMessageByTopic.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    SsMqPushLogMapper mqPushLogMapper;

    public void send(MessageTemplate mqPushLog) {
        logger.info("发送内容：{},到队列：{}", mqPushLog, mqPushLog.getMessageQueueName());
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.convertAndSend(FanoutExancheConstant.FANOUT_EXANCHE_CONSTANT_A, mqPushLog.getMessageQueueName(), mqPushLog, mqPushLog);
    }


    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("消息发送失败:{}，请检查队列订阅名称是否正确", message);
        try {
            //正常来说  交换机不正确 消息就没有进入队列当中 所以应该去插入 轮训mq表
            String sendMessage = new String(message.getBody(), "UTF-8");
            MessageTemplate messageTemplate = (MessageTemplate) JSONObject.parse(sendMessage);
            messageTemplate.setMqStatus(3); //待轮训处理
            SsMqPushLog pushLog = new SsMqPushLog();
            CommonBeanUtils.copyProperties(messageTemplate,pushLog);
            mqPushLogMapper.insert(pushLog);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        MessageTemplate messageTemplate = (MessageTemplate) correlationData;
        SsMqPushLog pushLog = new SsMqPushLog();
        CommonBeanUtils.copyProperties(messageTemplate,pushLog);
        if (!ack) {
            logger.info("队列：{}，消息发送失败:{}", messageTemplate.getMessageQueueName(), cause);
            pushLog.setMqStatus(2);//发送失败
            mqPushLogMapper.insert(pushLog);
        } else {
            logger.info("队列：{}，消息发送成功:{} ", messageTemplate.getMessageQueueName(), messageTemplate.getMessageInfo());
            pushLog.setMqStatus(0);//已发送
            mqPushLogMapper.insert(pushLog);
        }
    }


}
