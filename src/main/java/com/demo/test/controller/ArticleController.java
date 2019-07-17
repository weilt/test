package com.demo.test.controller;


import com.demo.test.Enum.ResultCode;
import com.demo.test.common.Const;
import com.demo.test.common.ResultEntity;
import com.demo.test.domain.Article;
import com.demo.test.redis.RedisCache;
import com.demo.test.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public ResultEntity getArticle(@PathVariable Long id) {
//        Article article = new Article();
//        article.setArticleId(id);
        Article article = articleRepository.getOne(id);
        if(article!=null){
//            Long upNumber = RedisCache.get(Const.ARTICLE_PREFIX+String.valueOf(id),String.valueOf(upNumber),0);
            return ResultEntity.createBySuccess(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),article);
        }else {
            return ResultEntity.createByErrorCodeMessage(ResultCode.ERROR.getCode(),ResultCode.ERROR.getMsg());
        }
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResultEntity getAllArticle(){
        List<Article> articleList = articleRepository.findAll();
        if(articleList.size() > 0 ){
            return ResultEntity.createBySuccess(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),articleList);
        }else {
            return  ResultEntity.createByErrorCodeMessage(ResultCode.NO_ARTICLE.getCode(),ResultCode.NO_ARTICLE.getMsg());
        }
    }
}
