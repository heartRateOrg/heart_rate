package com.sergtm.controllers.rest.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    @NotNull
    private String firstName;
    private String middleName;
    @NotNull
    private String secondName;
    private String email;
}
