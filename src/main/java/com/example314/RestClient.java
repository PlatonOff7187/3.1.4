package com.example314;

import com.example314.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestClient {
    private static final String URL = "http://91.241.64.178:7081/api/users";
    private static final RestTemplate rest = new RestTemplate();

    public static void main(String[] args) {
        RestClient restClient = new RestClient();
        restClient.getInfo();
    }

    private void getInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> user = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = rest.exchange(URL, HttpMethod.GET, user, String.class);
        List<String> cookie = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", String.join(";", cookie));
        //first
        User newUser = new User(3L, "James", "Brown", (byte) 10);
        HttpEntity<User> entity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> responseEntity1 = rest.postForEntity(URL, entity, String.class);
        System.out.println(responseEntity1.getBody());
        //second
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> update = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> responseEntity2 = rest.exchange(URL, HttpMethod.PUT, update, String.class);
        System.out.println(responseEntity2.getBody());
        //third
        HttpEntity<Object> detete = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity3 = rest.exchange(URL + "/3", HttpMethod.DELETE, detete, String.class);
        System.out.println(responseEntity3.getBody());

    }

}