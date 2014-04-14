package ru.ecom4u.web.controllers.dto.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Evgeny(e299792459@gmail.com) on 14.04.14.
 */
public class CommentForm {

    @NotEmpty
    private String title;
    @NotEmpty
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
