package com.kenect.kenect_challenge.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Contact(long id, String name, String email, String source, String createdAt, String updatedAt)
        implements Serializable {
}
