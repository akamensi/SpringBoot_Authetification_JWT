package com.akamensi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akamensi.entities.Article;
import com.akamensi.services.ArticleService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
@CrossOrigin("*")
public class ArticleRestController {
	
	private  ArticleService articleService;
	

	public ArticleRestController(ArticleService articleService) {
		super();
		this.articleService = articleService;
		
	}
	
    @GetMapping("/list")
    @ResponseBody
    public List<Article> getAllArticles()
    {
        return (List<Article>) articleService.getAllArticles();
    }
    
	@PostMapping("{providerId}")
	Article createArticle(@PathVariable(value = "providerId") Long providerId, @Valid @RequestBody Article article) {
		return articleService.create(providerId, article);
	}
	
    @PutMapping("{providerId}/{articleId}")
    @ResponseBody
    public Article updateArticle(@PathVariable (value = "providerId") Long providerId,
                                 @PathVariable (value = "articleId") Long articleId,
                                 @Valid @RequestBody Article articleRequest)
    {
    	return articleService.updateArticle(providerId, articleId, articleRequest);
    }
    
    @DeleteMapping("{articleId}")
    @ResponseBody
    public ResponseEntity<?> deleteArticle(@PathVariable (value = "articleId") Long articleId)
    {
    	return articleService.deleteArticle(articleId);
    }


    
    



}
