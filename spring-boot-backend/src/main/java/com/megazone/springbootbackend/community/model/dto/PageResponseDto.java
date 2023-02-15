package com.megazone.springbootbackend.community.model.dto;

import com.megazone.springbootbackend.community.model.entity.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PageResponseDto {
    private Long articleId;
    private String title;
    private String username;
    private String createdAt;
    private int boardId;

    public static PageResponseDto of(Article article) {
        return PageResponseDto.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .username(article.getUsers().getUsername())
                .createdAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .boardId(article.getBoardId())
                .build();
    }
}