package com.demo.test.request;

import com.demo.test.Enum.ActionEnum;

public interface Request {
    void  process();

    Long getReaderId();

    Long getArticleId();

    ActionEnum getAction();
}
