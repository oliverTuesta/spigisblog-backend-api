package spigi.blog.service;

import spigi.blog.dto.tag.TagCreationDto;
import spigi.blog.dto.tag.TagResponseDto;
import spigi.blog.model.Tag;

import java.util.List;

public interface TagService {
    public Tag createTag(TagCreationDto tagDto);
    public TagResponseDto getTag(Long id);
    public Tag updateTag(Long id, TagCreationDto tagDto);
    public void deleteTag(Long id);
    public List<TagResponseDto> getAllTags();
}
