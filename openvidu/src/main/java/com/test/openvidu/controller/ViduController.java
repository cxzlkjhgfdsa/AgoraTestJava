package com.test.openvidu.controller;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ViduController {

    private final OpenVidu openVidu;

    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> getSessionId() throws OpenViduJavaClientException, OpenViduHttpException {
        Map<String, Object> item = new HashMap<>();
        Session session = openVidu.createSession();
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("sessionId=" + session.getSessionId())
                .build();
        Connection connection = session.createConnection(connectionProperties);
        List<Publisher> publishers = connection.getPublishers();
        String token = connection.getToken();
        item.put("token", token);
        item.put("session", session);
        item.put("publishers", publishers);
        System.out.println("token = " + token);
        return ResponseEntity.ok(item);
    }

    @PostMapping("api/sessions")
    public ResponseEntity<String> createSession(@RequestBody Map<String, String> item){
        System.out.println("sessionID ==" + item.get("sessionId"));
        Session activeSession = openVidu.getActiveSession(item.get("sessionId"));
        System.out.println("activeSession = " + activeSession.getSessionId());
        return ResponseEntity.ok(activeSession.getSessionId());
    }

    @PostMapping("api/token")
    public ResponseEntity<String> createToken(@RequestBody Map<String, String> item) throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("sessionId = " + item.get("sessionId"));
        Session activeSession = openVidu.getActiveSession(item.get("sessionId"));
        Connection connection1 = activeSession.getConnection(item.get("connectionId"));
        System.out.println(connection1.getToken());
        return ResponseEntity.ok(connection1.getToken());
    }

    @PostMapping("api/create")
    public ResponseEntity<String> makeSession() throws OpenViduJavaClientException, OpenViduHttpException {

        Session session = openVidu.createSession();
        System.out.println("first==="+session.getSessionId());
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("sessionId=" + session.getSessionId())
                .build();
        Connection connection = session.createConnection(connectionProperties);
        String connectionId = connection.getConnectionId();
        System.out.println("connectionId = " + connectionId);
        return ResponseEntity.ok(session.getSessionId());
    }

}
