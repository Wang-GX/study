package com.wgx.study.project.RabbitMQ;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(value = "RabbitMQ消息队列", tags = "RabbitMQ消息队列")
@RestController
@Slf4j
@RequestMapping("/mq")
public class MessageController {

    @Autowired
    private MessageSender messageSender;

    @ApiOperation(value = "消息发送：receive")
    @PostMapping("/receive")
    public void send1() {
        messageSender.receive();
    }

    @ApiOperation(value = "消息发送：receiveBack")
    @PostMapping("/receiveBack")
    public void send2() {
        messageSender.receiveBack();
    }

    @ApiOperation(value = "消息发送：delayQueue")
    @PostMapping("/delayQueue")
    public void send3() {
        messageSender.delay();
    }

    @ApiOperation(value = "消息发送：delayMsg")
    @PostMapping("/delayMsg")
    public void send4() {
        messageSender.delayMsg();
    }


    public static void main(String[] args) {
        //取时间段的并集
        List<String> startList = new ArrayList<>();
        List<String> endList = new ArrayList<>();

        startList.add("08:00");
        startList.add("15:00");
        startList.add("09:00");
        endList.add("10:00");
        endList.add("11:00");
        endList.add("16:00");
        Collections.sort(startList);
        Collections.sort(endList);

        List<String> startRemoveIndex = new ArrayList<>();
        List<String> endRemoveIndex = new ArrayList<>();

        for (int i = 0; i < startList.size(); i++) {
            if (i > 0){
                String startTime = startList.get(i);
                String endTemp = endList.get(i - 1);
                if (startTime.compareTo(endTemp) <= 0){
                    startRemoveIndex.add(startTime);
                    endRemoveIndex.add(endTemp);
                }
            }
        }

        startList.removeAll(startRemoveIndex);
        endList.removeAll(endRemoveIndex);

        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < startList.size(); i++) {
            //只需要遍历一个集合，因为另外一个集合中的元素数量一定与之相同
            result.put(startList.get(i),endList.get(i));
        }

        System.out.println(result);
    }

    @Data
    class Item{
        String startTime;
        String endTime;
    }

    public static Set<Item> getTimeArea(List<Item> itemList){
        List<String> start = new ArrayList<>();
        List<String> end = new ArrayList<>();

        start.add("08:00");
        start.add("15:00");
        start.add("09:00");
        end.add("16:00");
        end.add("11:00");
        end.add("10:00");


        return null;

    }


}


