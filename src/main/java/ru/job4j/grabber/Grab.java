package ru.job4j.grabber;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface Grab {
    void init(Store store, Parse parse, Scheduler scheduler) throws SchedulerException;
}
