package com.demo.test.service.dataservice.impl;

import com.demo.test.repository.ArticleRepository;
import com.demo.test.service.dataservice.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public void UpdateUpNumberByArticle(Long articleId,Long upNumber){
        articleRepository.updateUpNumberByArticle(articleId,upNumber);
    }
}
