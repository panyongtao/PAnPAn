package com.pan.springlistener;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MyEvent extends ApplicationEvent {
    String name;
    public MyEvent(Object source,String name) {
        super(source);
        this.name=name;
    }

    public MyEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
