package com.epam.library.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	Integer id;
	@NotBlank(message = "The name of the book cannot be blank")
	String name;
	@NotBlank(message = "The name of the publisher cannot be blank")
	String publisher;
	@NotBlank(message = "The name of the author cannot be blank")
	String author;
	String port;
}
