package com.thatday.user.rocketmq;

import com.thatday.common.rocketmp.RocketMQUtil;
import com.thatday.user.entity.db.GroupInfo;
import com.thatday.user.entity.db.User;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RocketMQConfig implements ApplicationRunner {

    public static final String Topic = "bbb";
    public static final String Topic_A = "aaa";
    public static final String Tags_A = "a";
    public static final String Tags_B = "b";

    @Value("${server.port}")
    private int port;

    @Override
    public void run(ApplicationArguments args) {
        RocketMQUtil.initDefaultProducer();

        RocketMQUtil.initConsumer(port, Topic, Tags_A, (msgList, context) -> {
            User user = RocketMQUtil.getData(msgList, User.class);
            System.out.println(user);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        RocketMQUtil.initConsumer(port, Topic, Tags_B, (msgList, context) -> {

            GroupInfo groupInfo = RocketMQUtil.getData(msgList, GroupInfo.class);
            System.out.println(groupInfo);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }
}