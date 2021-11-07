package io.getarrays.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.server.model.Server;

public interface ServerRepository extends JpaRepository<Server, Long>{
	
	// to find a server by ip address
	Server findByIpAddress(String ipAddress);
}
