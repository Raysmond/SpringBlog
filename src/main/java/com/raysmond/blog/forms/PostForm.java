package com.raysmond.blog.forms;

import com.raysmond.blog.models.support.PostFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Data
public class PostForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostFormat postFormat;

}
