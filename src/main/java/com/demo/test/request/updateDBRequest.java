package com.demo.test.request;

import com.demo.test.Enum.ActionEnum;
import com.demo.test.domain.UpRecord;
import com.demo.test.service.dataservice.ArticleService;
import com.demo.test.service.dataservice.UpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class updateDBRequest implements Request {

    @Autowired
    private UpRecordService upRecordService;

    @Autowired
    private ArticleService articleService;

    private Long readerId;
    private Long articleId;
    private ActionEnum action;

    public updateDBRequest(Long readerId,Long articleId,ActionEnum action) {
        this.readerId = readerId;
        this.articleId = articleId;
        this.action  = action;
    }

    @Override
    @Transactional
    public void process() {
        UpRecord upRecord = new UpRecord();
        upRecord.setId(0L);
        upRecord.setArticleId(this.articleId);
        upRecord.setReaderId(this.readerId);
        if(this.action == ActionEnum.UP){
            upRecordService.insertUpRecord(upRecord);
            articleService.UpdateUpNumberByArticle(articleId,1L);
        } else {
            upRecordService.deleteUpRecord(upRecord);
            articleService.UpdateUpNumberByArticle(articleId,-1L);
        }
    }

    @Override
    public Long getReaderId() {
        return this.readerId;
    }

    @Override
    public Long getArticleId() {
        return articleId;
    }

    @Override
    public ActionEnum getAction() {
        return action;
    }
}
