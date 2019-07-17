package com.demo.test.common;

import com.demo.test.Enum.ActionEnum;

public class UpMessage {

    private Long articleId;

    private Long readerId;

    private Long numberOfUp;

    private ActionEnum action;

    public Long getNumberOfUp() {
        return numberOfUp;
    }

    public void setNumberOfUp(Long numberOfUp) {
        this.numberOfUp = numberOfUp;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }
}
