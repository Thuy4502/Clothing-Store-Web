package com.ptithcm.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Long productId;
    private double star;
    private String content;
    private String status;
    private Long orderItemId;

}
