package org.jhipster.dich.service.mapper;

import org.jhipster.dich.domain.Author;
import org.jhipster.dich.domain.Book;
import org.jhipster.dich.domain.Media;
import org.jhipster.dich.service.dto.AuthorDTO;
import org.jhipster.dich.service.dto.BookDTO;
import org.jhipster.dich.service.dto.MediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "author", source = "author", qualifiedByName = "authorName")
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaFileName")
    BookDTO toDto(Book s);

    @Named("authorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AuthorDTO toDtoAuthorName(Author author);

    @Named("mediaFileName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    MediaDTO toDtoMediaFileName(Media media);
}
