package com.demo.test.domain;

import javax.persistence.*;
import java.util.List;

/**
 * 作者
 */
@Entity
@Table(name = "t_reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long readerId; //读者id

    @Column(name = "reader_name",columnDefinition = "VARCHAR(50) NOT NULL")
    private String readerName; //读者名称

    @OneToMany(mappedBy = "readerId",cascade = {CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.LAZY)
    private List<UpRecord> upRecords; //点赞列表




    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
}
