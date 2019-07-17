package com.demo.test.service.dataservice.impl;

import com.demo.test.domain.UpRecord;
import com.demo.test.repository.ArticleRepository;
import com.demo.test.repository.UpRecordRepository;
import com.demo.test.service.dataservice.UpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpRecordServiceImpl implements UpRecordService {
//
//    @Autowired
//    private UpRecordService upRecordService;

    @Autowired
    private UpRecordRepository upRecordRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void insertUpRecord(UpRecord upRecord) {
//         upRecordService.insertUpRecord(upRecord);
        upRecordRepository.save(upRecord);
    }

    @Override
    public void deleteUpRecord(UpRecord upRecord) {

//        return upRecordService.deleteUpRecord(upRecord);

        upRecordRepository.delete(upRecord);

    }
}
