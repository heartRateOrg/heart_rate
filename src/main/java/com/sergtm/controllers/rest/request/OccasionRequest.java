package com.sergtm.controllers.rest.request;

import com.sergtm.OccasionLevel;
import com.sergtm.entities.Occasion;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OccasionRequest {
    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(required = true)
    private OccasionLevel occasionLevel;

    @ApiModelProperty(required = true)
    private boolean convulsion;

    @ApiModelProperty(required = true, example = "yyyy-MM-ddTHH:mm:ss")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime occasionDate;

    public OccasionRequest(Occasion occasion) {
        this.id = occasion.getId();
        this.occasionLevel = occasion.getOccasionLevel();
        this.convulsion = occasion.isConvulsion();
        this.occasionDate = occasion.getOccasionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
