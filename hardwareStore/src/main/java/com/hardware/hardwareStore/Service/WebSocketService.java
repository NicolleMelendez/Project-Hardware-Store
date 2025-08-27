package com.hardware.hardwareStore.Service;

import com.hardware.hardwareStore.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyClientUpdate(Client client, String action) {
        ClientNotification notification = new ClientNotification(client.getName(), action);
        messagingTemplate.convertAndSend("/topic/client-updates", notification);
    }

    public static class ClientNotification {
        private String name;
        private String action;

        public ClientNotification(String name, String action) {
            this.name = name;
            this.action = action;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}