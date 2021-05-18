package com.example.web4.mbeans;

public interface ResultMissToTotalRelationCountMBean {
    double getRelation(String username);
    void setRelation(String username, int misses, int total);
}
