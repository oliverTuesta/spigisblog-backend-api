package spigi.blog.dto.post;

import lombok.Data;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.dto.tag.TagResponseDto;
import spigi.blog.dto.user.UserResponseDto;

import java.util.List;

@Data
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String thumbnailUrl;
    private String slug;
    private UserResponseDto user;
    private Long views;
    private List<TagResponseDto> tags;
    private List<CategoryResponseDto> categories;
}
