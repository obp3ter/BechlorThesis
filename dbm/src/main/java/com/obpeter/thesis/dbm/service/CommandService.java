package com.obpeter.thesis.dbm.service;

import java.time.DayOfWeek;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import com.obpeter.thesis.dbm.entity.Command;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandService extends BaseService<Command, UUID> {
    private static final CommandService INSTANCE = new CommandService();

    private final CommandUtil util = CommandUtil.getInstance();

    public static CommandService getInstance() {
        return INSTANCE;
    }

    public Stream<Command> searchForDOW(@NotNull DayOfWeek dayOfWeek) {
        return StreamSupport.stream(repository.search(util.searchForDOW(QueryBuilders.boolQuery(), dayOfWeek)).spliterator(), false);
    }
    public Stream<Command> searchHourMinute(@NotNull Short hour,@Nullable Short minute){
        BoolQueryBuilder query = util.searchHour(QueryBuilders.boolQuery(),hour);
        if(minute!=null)
            query=util.searchMinute(query,minute);
        return StreamSupport.stream(repository.search(query).spliterator(),false);
    }


}
