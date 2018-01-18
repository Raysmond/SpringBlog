package com.raysmond.blog.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Raysmond
 */
@Data
public class UserForm {
    @NotNull
    private String password;

    @NotNull
    private String newPassword;
}
