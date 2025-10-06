package cohort_65.java.forumservice.security;

import cohort_65.java.forumservice.post.dao.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService{
    final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isAuthor(String username, String postId) { //проверкa, является ли пользователь с именем username автором
        return postRepository.findById(postId)
                .map(post -> post.getAuthor().equalsIgnoreCase(username)) //если пост найден
                .orElse(false); //если пост не найден
    }
}
