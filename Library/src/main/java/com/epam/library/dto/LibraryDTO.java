package com.epam.library.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO {
	Integer id;
	@NotBlank(message = "The User Name cannot be Blank!!!")
	String userName;
	@NotNull(message = "The Book Id cannot be Blank!!!")
	Integer bookId;
	
}
