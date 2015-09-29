package com.raysmond.blog.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Data
public class SettingsForm {
    @NotEmpty
    private String siteName;

    private String siteSlogan;

    private Integer pageSize;

}
