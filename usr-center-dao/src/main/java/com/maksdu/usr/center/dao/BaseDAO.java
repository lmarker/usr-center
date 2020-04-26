package com.maksdu.usr.center.dao;

import com.maksdu.usr.center.domain.BaseDO;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<S extends BaseDO, ID extends Serializable> {

    void insert(S item);

    List<S> findAll(S item);

    S getById(ID id);

    S getByCode(String code);
}
