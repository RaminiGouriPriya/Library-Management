package com.epam.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	String userName;
	@NotBlank(message = "The Email cannot be blank!!!")
	String email;
	@NotBlank(message = "The Name cannot be blank!!!")
	String name;
	String port;
}
