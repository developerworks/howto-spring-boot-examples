package com.example.springbootwebfluxsecuritydao.service;

import java.util.List;

/**
 * 二次抽象, 目的是达到存储层可替换的目的. 存储可以使关系数据库, NoSQL, 等各种存储方式.
 * 可以用工厂Bean(FactoryBean)的方式实现运行时的实现类替换
 *
 * https://stackoverflow.com/questions/19225115/how-to-do-conditional-auto-wiring-in-spring
 *
 * @param <T>
 */
public interface CrudService<T> {
    List<?> listAll();

    T getById(Long id);

    T saveOrUpdate(T domainObject);

    void delete(Long id);
}