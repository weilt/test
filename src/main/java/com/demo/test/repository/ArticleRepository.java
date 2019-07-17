package com.demo.test.repository;

import com.demo.test.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Query(nativeQuery = true,value = "Update article set up_number = up_number+:upNumber where id =:articleId")
    void updateUpNumberByArticle(Long articleId,Long upNumber);

    @Query(nativeQuery = true,value = "select up_number from article where id=:articleId")
    Long getUpNumberByArticle(Long articleId);
}
