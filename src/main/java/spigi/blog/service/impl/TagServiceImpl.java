package spigi.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spigi.blog.dto.category.CategoryCreationDto;
import spigi.blog.dto.category.CategoryResponseDto;
import spigi.blog.dto.tag.TagCreationDto;
import spigi.blog.dto.tag.TagResponseDto;
import spigi.blog.exception.ResourceNotFoundException;
import spigi.blog.model.Category;
import spigi.blog.model.Tag;
import spigi.blog.repository.CategoryRepository;
import spigi.blog.repository.TagRepository;
import spigi.blog.service.CategoryService;
import spigi.blog.service.TagService;
import spigi.blog.validations.CategoryValidator;
import spigi.blog.validations.TagValidator;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private ModelMapper modelMapper;
    private TagValidator tagValidator;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.tagValidator = tagValidator;
    }

    @Override
    public Tag createTag(TagCreationDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tag.setSlug(tagDto.getName().toLowerCase().replace(" ", "-"));
        tagValidator.validateTagCreation(tag);
        return tagRepository.save(tag);
    }

    @Override
    public TagResponseDto getTag(Long id) {
        return tagRepository.findById(id)
                .map(tag -> modelMapper.map(tag, TagResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    @Override
    public Tag updateTag(Long id, TagCreationDto tagDto) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        tagValidator.validateTagUpdate(tag, modelMapper.map(tagDto, Tag.class));
        modelMapper.map(tagDto, tag);
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        tagRepository.delete(tag);
    }

    @Override
    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagResponseDto.class))
                .toList();
    }
}
