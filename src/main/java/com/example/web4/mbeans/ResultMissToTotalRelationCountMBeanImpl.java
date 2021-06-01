package com.example.web4.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ManagedResource(objectName="com.example.web4.mbeans:name=ResultRelationCountMBean")
@Component
public class ResultMissToTotalRelationCountMBeanImpl implements ResultMissToTotalRelationCountMBean{

    private final Map<String, Double> relation;

    public ResultMissToTotalRelationCountMBeanImpl() {
        this.relation = new HashMap<>();
    }

    @ManagedOperation
    @Override
    public double getRelation(String username) {
        return relation.get(username);
    }

    @ManagedOperation
    @Override
    public void setRelation(String username, int misses, int total) {
        this.relation.put(username, (double)(misses/total));
    }
}
