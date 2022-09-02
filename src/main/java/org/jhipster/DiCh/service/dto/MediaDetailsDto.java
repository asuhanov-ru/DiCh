package org.jhipster.dich.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaDetailsDto {

    private String id;
    private String fileName;
    private String fileType;
    private String fileDesc;
    private int lastPageNumber;
}
