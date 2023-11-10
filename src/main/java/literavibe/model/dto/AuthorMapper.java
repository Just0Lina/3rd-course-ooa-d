package literavibe.model.dto;

import literavibe.model.entities.Author;
import literavibe.model.entities.Media;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static List<AuthorDto> convertAuthorsToDto(List<Author> authors) {
        if (authors == null) {
            return null;
        }

        return authors.stream()
                .map(AuthorMapper::convertAuthorToDto)
                .collect(Collectors.toList());
    }

    public static AuthorDto convertAuthorToDto(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDto().firstName(author.getFirstName())
                .secondName(author.getSecondName())
                .info(author.getInfo())
                .imageUrl(convertMediaToDto(author.getMedia()));
    }

    private static Long convertMediaToDto(Media media) {
        // Add logic to convert Media entity to MediaDto
        // For example, return media.getId() if MediaDto has an id field
        return (media != null) ? media.getId() : null;
    }
}

