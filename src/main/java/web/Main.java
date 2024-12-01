package web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Main {
    private static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        String sessionId = response.getHeaders().get("Set-Cookie").get(0);
        headers.set("Cookie", sessionId);

        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpEntity<User> entitySave = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> responseSave =
                restTemplate.exchange(URL, HttpMethod.POST, entitySave, String.class);

        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> entityUpdate = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> responseUpdate =
                restTemplate.exchange(URL, HttpMethod.PUT, entityUpdate, String.class);

        HttpEntity<String> entityDelete = new HttpEntity<>(headers);
        ResponseEntity<String> responseDelete =
                restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entityDelete, String.class);

        String code = responseSave.getBody() + responseUpdate.getBody() + responseDelete.getBody();
        System.out.println(code);
        System.out.println(code.length());
    }
}
