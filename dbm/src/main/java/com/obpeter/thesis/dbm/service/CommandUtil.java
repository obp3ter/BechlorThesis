package com.obpeter.thesis.dbm.service;

import java.time.DayOfWeek;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandUtil {

    private static final CommandUtil INSTANCE = new CommandUtil();

    public static CommandUtil getInstance() {
        return INSTANCE;
    }

    public BoolQueryBuilder searchForDOW(BoolQueryBuilder query, DayOfWeek dayOfWeek) {
        return query.must(QueryBuilders
                .scriptQuery(new Script("doc['time'].value.getDayOfWeekEnum() == DayOfWeek." + dayOfWeek)));
    }
    public BoolQueryBuilder searchHour(BoolQueryBuilder query, Short hour){
        return query.must(QueryBuilders
                .scriptQuery(new Script("doc['time'].value.getHourOfDay() == "+hour)));
    }
    public BoolQueryBuilder searchMinute(BoolQueryBuilder query, Short minute){
        return query.must(QueryBuilders
                .scriptQuery(new Script("doc['time'].value.minute() == "+minute)));
    }

}
