package com.obpeter.thesis.dbm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

public class MappedIterator<T,R> implements Iterator<R> {
    ArrayList<R> items =new ArrayList<>();
    Integer currentIndex=0;
    Function<T,R> function;

    public MappedIterator(Iterable<T> iterable, Function<T,R> function) {
        iterable.forEach(item -> items.add(function.apply(item)));
        this.function=function;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < items.size();
    }

    @Override
    public R next() {
        return items.get(currentIndex++);
    }
}
