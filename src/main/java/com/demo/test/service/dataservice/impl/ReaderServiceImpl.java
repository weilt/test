package com.demo.test.service.dataservice.impl;

import com.demo.test.domain.Reader;
import com.demo.test.repository.ReaderRepository;
import com.demo.test.service.dataservice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl implements ReaderService {

//    @Autowired
//    private ReaderMapper readerMapper;
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Reader getReaderById(Long readerId) {
//        return readerMapper.getReaderById(readerId);
        return readerRepository.getOne(readerId);
    }
}
