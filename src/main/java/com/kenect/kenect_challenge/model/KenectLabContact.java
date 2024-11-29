package com.kenect.kenect_challenge.model;

import lombok.Data;

@Data
public class KenectLabContact {
    private long id;
    private String name;
    private String email;
    private String source;
    private String createdAt;
    private String updatedAt;
}
