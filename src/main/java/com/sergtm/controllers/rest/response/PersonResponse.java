package com.sergtm.controllers.rest.response;

import com.sergtm.entities.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponse {
	private Long id;
	private String name;

	public PersonResponse() {
	}

	public PersonResponse(Person person) {
		this.id = person.getId();
		this.name = person.getName();
	}
}
