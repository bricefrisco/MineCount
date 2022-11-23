package com.minecount.models.dtos;


public class TotalCountDTO {
    private Long count;

    public TotalCountDTO() {
    }

    public TotalCountDTO(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
