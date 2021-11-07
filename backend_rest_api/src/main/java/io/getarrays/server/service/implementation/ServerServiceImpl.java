package io.getarrays.server.service.implementation;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;
import io.getarrays.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

@Service
@RequiredArgsConstructor // lombork will create a construtor for us
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverReposetory;

    @Override
    public Server create(Server server) {
        log.info("saving new server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverReposetory.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("pinging server IP: {}", ipAddress);
        Server server = serverReposetory.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress); // to get the inet address of the server
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        serverReposetory.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("fetching all servers");
        return serverReposetory.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("fetching server by id: {}", id);
        return serverReposetory.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server : {}", server.getName());
        return serverReposetory.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverReposetory.deleteById(id); //if this line filed => return an error else we continue to the next line
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)])
                .toUriString();
    }
}
