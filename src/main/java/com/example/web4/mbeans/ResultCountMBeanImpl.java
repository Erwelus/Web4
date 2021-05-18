package com.example.web4.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ManagedResource(objectName = "com.example.web4.mbeans:name=ResultCountMBean")
@Component
public class ResultCountMBeanImpl implements ResultCountMBean {

    private final Map<String, Integer> total;
    private final Map<String, Integer> misses;
    private final ResultMissToTotalRelationCountMBean resultMissToTotalRelationCountMBean;

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
    public void setTotal(String username, int total) {
        this.total.put(username, total);
        resultMissToTotalRelationCountMBean.setRelation(username, misses.size(), this.total.size());
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
}