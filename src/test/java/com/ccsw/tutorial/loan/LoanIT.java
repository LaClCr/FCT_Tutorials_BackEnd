package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.loan.model.LoanDto;

/**
 * @author LaClCr
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;
    private static final Long NOT_EXISTS_GAME_ID = 0L;
    private static final Long EXISTS_GAME_ID = 1L;
    private static final Long NOT_EXISTS_CLIENT_ID = 0L;
    private static final Long EXISTS_CLIENT_ID = 1L;

    private static final String GAME_ID_PARAM = "game";
    private static final String CLIENT_ID_PARAM = "client";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    private String getUrlWithParams() {

        return LOCALHOST + port + SERVICE_PATH;

    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {
        int LOANS_WITHOUT_FILTER = 4;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITHOUT_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameShouldReturnLoans() {
        int LOANS_WITH_FILTER = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> gameData = new HashMap<>();
        gameData.put("id", EXISTS_GAME_ID);
        requestBody.put("game", gameData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsClientShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> clientData = new HashMap<>();
        clientData.put("id", EXISTS_CLIENT_ID);
        requestBody.put("client", clientData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameAndClientShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> gameData = new HashMap<>();
        gameData.put("id", EXISTS_GAME_ID);
        requestBody.put("game", gameData);

        Map<String, Long> clientData = new HashMap<>();
        clientData.put("id", EXISTS_CLIENT_ID);
        requestBody.put("client", clientData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsGameShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> gameData = new HashMap<>();
        gameData.put("id", NOT_EXISTS_GAME_ID);
        requestBody.put("game", gameData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> clientData = new HashMap<>();
        clientData.put("id", NOT_EXISTS_CLIENT_ID);
        requestBody.put("client", clientData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsTitleOrClientShouldReturnEmpty() {

        int LOANS_WITH_FILTER = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Long> gameData = new HashMap<>();
        gameData.put("id", NOT_EXISTS_GAME_ID);
        requestBody.put("game", gameData);

        Map<String, Long> clientData = new HashMap<>();
        clientData.put("id", NOT_EXISTS_CLIENT_ID);
        requestBody.put("client", clientData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                requestEntity, responseType);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

}