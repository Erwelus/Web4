package com.example.web4.service;

import com.example.web4.model.Result;
import com.example.web4.model.User;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService{
    @Override
    public boolean check(Double x, Double y, Double r) {
        return ((x>=0 && y>=0 && x<=r && y<=r) ||
                (x<0 && y>0 && y<=2*x - r/2) ||
                (x>0 && y>0 && x*x + y*y <= r*r));
    }

    @Override
    public Result prepareResult(Double x, Double y, Double r, User user) {
        Result result = new Result();
        result.setX(x);
        result.setY(y);
        result.setR(r);
        result.setHit(check(x,y,r));
        result.setOwner(user);
        return result;
    }
}
