package com.epam.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@Column(name="username")
	private String userName;
	@Column(name="email")
	private String email;
	@Column(name="name")
	@NotBlank(message = "The Name cannot be blank!!!")
	private String name;
}
