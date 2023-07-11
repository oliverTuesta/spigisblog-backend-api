package spigi.blog.dto.category;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private String name;
    private String description;
    private String slug;
}