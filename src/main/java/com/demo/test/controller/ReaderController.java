package com.demo.test.controller;


import com.demo.test.Enum.ResultCode;
import com.demo.test.common.ResultEntity;
import com.demo.test.domain.Reader;
import com.demo.test.service.dataservice.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = "/getuprecord",method = RequestMethod.GET)
    public ResultEntity getUpRecord(@RequestBody Reader reader){
        //TODO 获得点赞记录 首先去redis中查询信息，获取到点赞记录 如果没有，直接丢给异步服务处理，
        reader = readerService.getReaderById(reader.getReaderId());
        if(reader!=null){
           return   ResultEntity.createBySuccess(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),reader);
        }else {
            return  ResultEntity.createByErrorCodeMessage(ResultCode.NO_RECORD.getCode(),ResultCode.NO_RECORD.getMsg());
        }
    }
}
