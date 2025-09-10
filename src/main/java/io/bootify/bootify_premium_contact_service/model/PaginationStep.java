package io.bootify.bootify_premium_contact_service.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaginationStep {

    private boolean active = false;
    private boolean disabled = false;
    private String label;
    private String url;

}
