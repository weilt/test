package com.demo.test.domain;

import javax.persistence.*;
import java.util.List;

/**
 * 文章
 */
@Entity
@Table(name = "t_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId; //文章id

    @Column(name = "title",columnDefinition = "VARCHAR(255) NOT NULL")
    private String title; //文章标题

    @Column(name = "content",columnDefinition = "TEXT")
    private String content; //文章内容

    @Column(name = "number_up",columnDefinition = "INT")
    private Long numberUp; //点赞数

    @OneToMany(targetEntity = UpRecord.class,mappedBy = "articleId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UpRecord> upRecords; //点赞列表

    public List<UpRecord> getUpRecords() {
        return upRecords;
    }

    public void setUpRecords(List<UpRecord> upRecords) {
        this.upRecords = upRecords;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNumberUp() {
        return numberUp;
    }

    public void setNumberUp(Long numberUp) {
        this.numberUp = numberUp;
    }
}
