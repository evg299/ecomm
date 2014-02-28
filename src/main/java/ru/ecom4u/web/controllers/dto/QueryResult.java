package ru.ecom4u.web.controllers.dto;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 27.02.14.
 */
public class QueryResult<T> {
    private Integer countAll;
    private List<T> data;

    public QueryResult(Integer countAll, List<T> data) {
        this.countAll = countAll;
        this.data = data;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
