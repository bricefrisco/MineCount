package com.minecount.models.dtos;

import java.sql.Timestamp;

public class ServerCountDTO {
    private Timestamp time;
    private Integer min;
    private Double avg;
    private Integer max;

    public Timestamp getTime() {
        return time;
    }

    public Integer getMin() {
        return min;
    }

    public Double getAvg() {
        return avg;
    }

    public Integer getMax() {
        return max;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "ServerCountDTO{" +
                "time=" + time +
                ", min=" + min +
                ", avg=" + avg +
                ", max=" + max +
                '}';
    }
}
