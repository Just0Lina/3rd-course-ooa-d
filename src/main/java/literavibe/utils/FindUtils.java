package literavibe.utils;

import literavibe.model.entities.*;
import literavibe.model.exceptions.NotFoundException;
import literavibe.respository.*;

public class FindUtils {
    private FindUtils() {
    }

    public static User findUser(UserRepository repository, String userId) throws NotFoundException {
        return repository.findByLogin(userId).orElseThrow(
                () -> new NotFoundException("Couldn't find user with id: " + userId));
    }

    public static Book findBook(BookRepository repository, Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't find book with id: " + id));
    }

    public static Comment findComment(CommentRepository repository, Long commentId) throws NotFoundException {
        return repository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Couldn't find comment with id: " + commentId));
    }

    public static Collection findCollection(CollectionRepository repository, Long collectionId) throws
            NotFoundException {
        return repository.findById(collectionId).orElseThrow(
                () -> new NotFoundException("Couldn't find collection with id: " + collectionId));
    }

    public static Media findMedia(MediaRepository repository, Long mediaId) throws NotFoundException {
        return repository.findById(mediaId).orElseThrow(
                () -> new NotFoundException("Couldn't find media with id: " + mediaId));
    }

    public static Note findNote(NoteRepository repository, Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Couldn't find Note with id: " + id));
    }
}
