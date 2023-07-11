package spigi.blog.service;

import spigi.blog.model.Tag;

public interface TagService {
    public Tag createTag(Tag tag);
    public Tag getTag(Long id);
    public Tag updateTag(Long id, Tag tag);
    public void deleteTag(Long id);
}
