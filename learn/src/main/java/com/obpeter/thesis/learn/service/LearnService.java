package com.obpeter.thesis.learn.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.Gson;
import com.obpeter.thesis.learn.client.ESAccess;
import com.obpeter.thesis.learn.entity.Command;
import com.obpeter.thesis.learn.repository.CommandRepo;
import com.obpeter.thesis.learn.util.RandomForestMapping;
import lombok.SneakyThrows;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnService {

    public static final double PERCENTILE = 10;

    @Autowired
    ESAccess access;

    @Autowired
    CommandRepo repository;

    private final Gson gson = new Gson();

    ArrayList<Quartet<String, Class<?>, Method, RandomForestMapping.Strategy>> properties = new ArrayList<>();

    public long count() {
        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("dayOfWeek", "SUNDAY"));
        return access.count("shm", query);
    }

    @SneakyThrows
    public ArrayList<Quartet<String, Class<?>, Method, RandomForestMapping.Strategy>> getProperties() {
        if (!properties.isEmpty()) {
            return properties;
        }
        Field[] fields = Command.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            RandomForestMapping.Strategy strategy = field.getAnnotation(RandomForestMapping.class).strategy();
            if (fieldName.equals("freeText") || strategy == RandomForestMapping.Strategy.IGNORE) {
                continue;
            }
            String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            properties.add(new Quartet<>(fieldName, field.getType(),
                    Command.class.getDeclaredMethod("get" + name),
                    strategy));
        }
        return properties;
    }

    public List<Command> getAllCommands() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void getOneField() {
        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("dayOfWeek", "SUNDAY"));
        access.getField("shm", query, "dayOfWeek");
    }

    public Quartet<String, Class<?>, Method, RandomForestMapping.Strategy> sometin() {
        ArrayList<Quartet<String, Class<?>, Method, RandomForestMapping.Strategy>> properties = getProperties();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        Quartet<String, Class<?>, Method, RandomForestMapping.Strategy> bestProperty = properties.stream()
                .map(property -> Pair.with(property,
                        propertyScore(property.getValue0(), queryBuilder, property.getValue3(), property.getValue1()))).max(
                        Comparator.comparing(pair -> pair.getValue1().getValue1())).get().getValue0();
        return bestProperty;
    }

    public <T> Pair<QueryBuilder, Long> propertyScore(String name, BoolQueryBuilder baseQuery,
            RandomForestMapping.Strategy strategy, Class<T> clazz) {
        switch (strategy) {
        case EQUALS:
            BoolQueryBuilder currentQuery = QueryBuilders.boolQuery().must(baseQuery);
            Set<T> unique = access.getField("shm", baseQuery, name).stream().map(json -> gson.fromJson(json, clazz)).collect(
                    Collectors.toSet());
            T bestValue = null;
            long score = 0;
            for (T value : unique) {
                currentQuery.must(QueryBuilders.matchQuery(name, value.toString()));
                long currentScore = access.count("shm", currentQuery);
                if (score < currentScore) {
                    bestValue = value;
                }
                currentQuery = QueryBuilders.boolQuery().must(baseQuery);
            }
            if (bestValue != null) {
                return Pair.with(QueryBuilders.matchQuery(name, bestValue.toString()), score);
            } else {
                return Pair.with(null, 0L);
            }
        case RANGE:
            long count = access.count("shm", baseQuery);
            RangeQueryBuilder rangeQueryBuilder;
            if (!clazz.equals(LocalDateTime.class)) {
                rangeQueryBuilder = QueryBuilders.rangeQuery(name)
                        .gte(gson.fromJson(
                                access.getPosInSorted("shm", baseQuery, name, (int) Math.ceil((PERCENTILE / 100) * count),
                                        SortOrder.ASC), clazz))
                        .lte(gson.fromJson(
                                access.getPosInSorted("shm", baseQuery, name, (int) Math.ceil((PERCENTILE / 100) * count),
                                        SortOrder.DESC), clazz));
            } else {
                rangeQueryBuilder = QueryBuilders.rangeQuery(name)
                        .gte(LocalDateTime.parse(access
                                        .getPosInSorted("shm", baseQuery, name, (int) Math.ceil((PERCENTILE / 100) * count),
                                                SortOrder.ASC).replace("\"", ""),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")))
                        .lte(LocalDateTime.parse(
                                access.getPosInSorted("shm", baseQuery, name, (int) Math.ceil((PERCENTILE / 100) * count),
                                        SortOrder.DESC).replace("\"", ""),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")));
            }
            return Pair.with(rangeQueryBuilder,
                    access.count("shm", QueryBuilders.boolQuery().must(baseQuery).must(rangeQueryBuilder)));
        default:
            return Pair.with(null, 0L);
        }
    }

}
