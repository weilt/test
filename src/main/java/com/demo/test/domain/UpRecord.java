package com.demo.test.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_uprecord")
public class UpRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Long articleId;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Long readerId;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "reader_name")
    private String readerName;

    @Column(name = "create_time",columnDefinition = "timestamp")
    private Date createTime;


    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
