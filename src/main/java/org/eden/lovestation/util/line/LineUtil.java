package org.eden.lovestation.util.line;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;
import org.eden.lovestation.exception.business.LineNotificationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "line")
@Data
public class LineUtil {
    private String messagePushUrl;
    private String userBotChannelToken;
    private String referralEmployeeBotChannelToken;
    private String landlordBotChannelToken;
    private String volunteerBotChannelToken;
    private String firmEmployeeBotChannelToken;

    public void sendUserBotNotification(String lineId, List<Message> messages) throws LineNotificationException {
        MessagePacket messagePacket = new MessagePacket(lineId, messages);
        HttpResponse<String> response;
        try {
            response = sendNotification(messagePacket, userBotChannelToken);
            if (!(response.statusCode() == HttpStatus.OK.value())) {
                throw new LineNotificationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new LineNotificationException();
        }
    }

    public void sendReferralEmployeeBotNotification(String lineId, List<Message> messages) throws LineNotificationException {
        MessagePacket messagePacket = new MessagePacket(lineId, messages);
        HttpResponse<String> response;
        try {
            response = sendNotification(messagePacket, referralEmployeeBotChannelToken);
            if (!(response.statusCode() == HttpStatus.OK.value())) {
                throw new LineNotificationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new LineNotificationException();
        }
    }

    public void sendLandlordBotNotification(String lineId, List<Message> messages) throws LineNotificationException {
        MessagePacket messagePacket = new MessagePacket(lineId, messages);
        HttpResponse<String> response;
        try {
            response = sendNotification(messagePacket, landlordBotChannelToken);
            if (!(response.statusCode() == HttpStatus.OK.value())) {
                throw new LineNotificationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new LineNotificationException();
        }
    }

    public void sendVolunteerBotNotification(String lineId, List<Message> messages) throws LineNotificationException {
        MessagePacket messagePacket = new MessagePacket(lineId, messages);
        HttpResponse<String> response;
        try {
            response = sendNotification(messagePacket, volunteerBotChannelToken);
            if (!(response.statusCode() == HttpStatus.OK.value())) {
                throw new LineNotificationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new LineNotificationException();
        }
    }

    public void sendFirmEmployeeBotNotification(String lineId, List<Message> messages) throws LineNotificationException {
        MessagePacket messagePacket = new MessagePacket(lineId, messages);
        HttpResponse<String> response;
        try {
            response = sendNotification(messagePacket, firmEmployeeBotChannelToken);
            if (!(response.statusCode() == HttpStatus.OK.value())) {
                throw new LineNotificationException();
            }
        } catch (IOException | InterruptedException e) {
            throw new LineNotificationException();
        }
    }

    private HttpResponse<String> sendNotification(MessagePacket messagePacket, String channelToken) throws IOException, InterruptedException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(messagePacket);
        System.out.println("[Line Notification]->"+json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.messagePushUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + channelToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
