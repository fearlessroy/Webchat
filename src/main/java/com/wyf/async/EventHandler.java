package com.wyf.async;

import java.util.List;

/**
 * Created by w7397 on 2017/3/30.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
