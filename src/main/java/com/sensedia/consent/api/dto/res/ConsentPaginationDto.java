package com.sensedia.consent.api.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentPaginationDto {
    private ConsentResponseDto consent;
    private int page;
    private int pageSize;
    private int totalPages;
    private int totalElements;

    public ConsentPaginationDto() {
    }

    public ConsentPaginationDto(ConsentResponseDto consent, int page, int pageSize, int totalPages, int totalElements) {
        this.consent = consent;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
