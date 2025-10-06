package cohort_65.java.forumservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDto {
    String title;
    String content;
    Set<String> tags;
}