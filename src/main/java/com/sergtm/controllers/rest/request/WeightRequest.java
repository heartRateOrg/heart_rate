package com.sergtm.controllers.rest.request;

import com.sergtm.entities.Weight;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightRequest {
    @ApiModelProperty(hidden = true)
    private Long id;

    @DecimalMin("1")
    @DecimalMax("999.999")
    @ApiModelProperty(required = true)
    private BigDecimal weight;

    @ApiModelProperty(required = true, example = "yyyy-MM-dd")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate date;

    public WeightRequest(Weight weight) {
        this.id = weight.getId();
        this.date = weight.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.weight = weight.getWeight();
    }
}
