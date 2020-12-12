package com.example.web4.service;

import com.example.web4.model.Result;
import com.example.web4.model.User;
import com.example.web4.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
public class ResultServiceImpl implements ResultService{

    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }
    @Override
    public boolean check(Double x, Double y, Double r) {
        return ((x>=0 && y>=0 && x<=r && y<=r) ||
                (x<0 && y>0 && y<=2*x + r) ||
                (x>0 && y<0 && x*x + y*y <= r*r));
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

    @Override
    public List<Result> getAllForOwner(User owner) {
        return resultRepository.findByOwner(owner);
    }
    @Override
    public String prepareToPrimeReact(List<Result> results) {
        StringJoiner joiner = new StringJoiner(",");
        for (Result res : results) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\"x\":\"");
            stringBuilder.append(String.format("%.3f",res.getX()));
            stringBuilder.append("\", \"y\":\"");
            stringBuilder.append(String.format("%.3f",res.getY()));
            stringBuilder.append("\", \"r\":\"");
            stringBuilder.append(res.getR().toString());
            stringBuilder.append("\", \"res\":\"");
            stringBuilder.append(res.getHit());
            stringBuilder.append("\"}");
            joiner.add(stringBuilder.toString());
        }
        return "["+joiner.toString()+"]";
    }
}
