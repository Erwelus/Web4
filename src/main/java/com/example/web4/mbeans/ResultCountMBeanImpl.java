package com.example.web4.mbeans;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.util.HashMap;
import java.util.Map;

@ManagedResource(objectName = "com.example.web4.mbeans:name=ResultCountMBean")
@Component
public class ResultCountMBeanImpl implements ResultCountMBean, NotificationPublisherAware {

    private final Map<String, Integer> total;
    private final Map<String, Integer> misses;
    private long sequenceNumber = 1;
    private final ResultMissToTotalRelationCountMBean resultMissToTotalRelationCountMBean;
    private NotificationPublisher publisher;

    public ResultCountMBeanImpl(ResultMissToTotalRelationCountMBean resultMissToTotalRelationCountMBean) {
        this.resultMissToTotalRelationCountMBean = resultMissToTotalRelationCountMBean;
        total = new HashMap<>();
        misses = new HashMap<>();
    }

    @ManagedOperation
    @Override
    public int getTotal(String username) {
        return total.get(username);
    }

    @ManagedOperation
    @Override
    public void setTotal(String username, int total, boolean lasthit) {
        this.total.put(username, total);
        resultMissToTotalRelationCountMBean.setRelation(username, misses.size(), this.total.size());
        if (!lasthit){
            Notification notification = new Notification("CountNotification",this, sequenceNumber++, "Last result was false");
            publisher.sendNotification(notification);
        }
    }

    @ManagedOperation
    @Override
    public int getMisses(String username) {
        return misses.get(username);
    }

    @ManagedOperation
    @Override
    public void setMisses(String username, int misses) {
        this.misses.put(username, misses);
        resultMissToTotalRelationCountMBean.setRelation(username, this.misses.size(), total.size());
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }
}