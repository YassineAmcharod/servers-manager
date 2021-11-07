package io.getarrays.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;

import io.getarrays.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity

//to get getters and setters with lombok dependency
@Data
// constructor without args
@NoArgsConstructor
// constructor without all args
@AllArgsConstructor
public class Server {
	
	@Id 
	@GeneratedValue(strategy= AUTO)
	private Long id;
	
	@Column(unique = true)
	@NotEmpty(message = "address ip cannot be empty or null")
	private String ipAddress;
	private String name;
	private String memory;
	private String type;
	private String imageUrl;
	private Status status;

}
