package com.obpeter.thesis.dbm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public class BaseService<T, ID> {
    @Autowired
    ElasticsearchRepository<T, ID> repository;

    public Stream<T> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

    public Optional<T> find(ID id) {
        return repository.findById(id);
    }

    public T add(T entity) {
       return repository.save(entity);
    }
}
