package com.example.web4.mbeans;

public interface ResultCountMBean {
    int getTotal(String username);
    void setTotal(String username, int total, boolean lasthit);
    int getMisses(String username);
    void setMisses(String username, int misses);
}
