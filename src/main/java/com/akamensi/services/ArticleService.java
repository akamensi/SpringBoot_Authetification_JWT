package com.akamensi.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.akamensi.entities.Article;
import com.akamensi.exception.ResourceNotFoundException;
import com.akamensi.repositories.ArticleRepository;
import com.akamensi.repositories.ProviderRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;
	private ProviderRepository providerRepository;

	public ArticleService(ArticleRepository articleRepository, ProviderRepository providerRepository) {
		super();
		this.articleRepository = articleRepository;
		this.providerRepository = providerRepository;
	}

	public List<Article> getAllArticles() {
		return (List<Article>) articleRepository.findAll();
	}

	public Article create(Long providerId, Article article) {
		return providerRepository.findById(providerId).map(provider -> {
			article.setProvider(provider);
			return articleRepository.save(article);
		}).orElseThrow(() -> new ResourceNotFoundException("ProviderId " + providerId + " not found"));
	}
	public Article updateArticle( Long providerId, Long articleId,
         Article articleRequest) {
		if(!providerRepository.existsById(providerId)) {
            throw new ResourceNotFoundException("ProviderId " + providerId + " not found");
        }

        return articleRepository.findById(articleId).map(article -> {
             article.setLabel(articleRequest.getLabel()); 
             article.setPrice(articleRequest.getPrice());
             article.setPicture(articleRequest.getPicture()); 
        return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("ArticleId " + articleId + "not found"));
    }
	
	
    public ResponseEntity<?> deleteArticle( Long articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));
    }





	
	
	
	
}
