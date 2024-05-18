package com.akamensi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akamensi.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>  {

}
