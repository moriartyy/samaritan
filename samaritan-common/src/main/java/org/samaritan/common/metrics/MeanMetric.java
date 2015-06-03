package org.samaritan.common.metrics;

import java.util.concurrent.atomic.LongAdder;

public class MeanMetric implements Metric {

    private final LongAdder counter = new LongAdder();
    private final LongAdder sum = new LongAdder();

    public void inc(long n) {
        counter.increment();
        sum.add(n);
    }

    public void dec(long n) {
        counter.decrement();
        sum.add(-n);
    }

    public long count() {
        return counter.sum();
    }

    public long sum() {
        return sum.sum();
    }

    public double mean() {
        long count = count();
        if (count > 0) {
            return sum.sum() / (double) count;
        }
        return 0.0;
    }

    public void clear() {
        counter.reset();
        sum.reset();
    }
}
