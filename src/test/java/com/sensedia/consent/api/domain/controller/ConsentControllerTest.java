package com.sensedia.consent.api.domain.controller;

import com.sensedia.consent.api.domain.enums.Status;
import com.sensedia.consent.api.domain.model.Consent;
import com.sensedia.consent.api.dto.req.ConsentUpdatedRequestDto;
import com.sensedia.consent.api.dto.res.ConsentCreatedResponseDto;
import com.sensedia.consent.api.mapper.ConsentMapper;
import com.sensedia.consent.api.repository.ConsentRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConsentControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:18"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    ConsentRepository consentRepository;

    @Autowired
    ConsentMapper consentMapper;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        consentRepository.deleteAll();
    }

    @Test
    @DisplayName("[1 - Scenario]: Should return all consents correctly")
    void Scenario1_should_get_all_consents() {
        List<Consent> consents = List.of(
                new Consent(null,
                        "463.650.020-25",
                        Status.ACTIVE,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "Consent created"),
                new Consent(null,
                        "810.045.920-78",
                        Status.ACTIVE,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "Consent created")
        );
        consentRepository.saveAll(consents);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/consents")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    @Test
    @DisplayName("[2 - Scenario]: Should create a consent")
    void Scenario2_should_create_consent() {
        Consent consent =
                new Consent(null,
                        "546.020.470-41",
                        Status.ACTIVE,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "Consent created");
        consentRepository.save(consent);

        String response = "{'consentId': '" + consent.getId() + "','message': 'Consent created', 'status': '"+ consent.getStatus() +"'} ";

        given()
                .contentType(ContentType.JSON)
                .body(response)
                .when()
                .post("/consents")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("[Scenario 3]: Should return a consent by id")
    void Scenario3_should_return_consent_by_id() {
        Consent consent = new Consent(null,
                        "463.650.020-25",
                        Status.ACTIVE,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "Consent created");
        var consentSaved = consentRepository.save(consent);

        consentRepository.findById(consentSaved.getId());

        var response = consentMapper.consentToConsentResponseDto(consent);

        given()
                .contentType(ContentType.JSON)
                .body(response)
                .when()
                .get("/consents/{id}", consent.getId())
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("[Scenario 4]: Should update a consent by id")
    void Scenario4_update_consent_by_id() {
        Consent consent = new Consent(null,
                "463.650.020-25",
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Consent created");
        var consentSaved = consentRepository.save(consent);

        consentSaved.setStatus(Status.REVOKED);
        consentSaved.setAdditionalInfo("Content updated");

        consentRepository.save(consentSaved);

        ConsentUpdatedRequestDto request = new ConsentUpdatedRequestDto(
                LocalDateTime.now(),
                Status.REVOKED,
    "Consent updated"
        );

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/consents/{id}", consentSaved.getId())
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("[Scenario 5]: Should logically delete the consent status by id")
    void Scenario5_should_logically_delete_consent_status() {
        Consent consent = new Consent(null,
                "463.650.020-25",
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "Consent created");
        var consentSaved = consentRepository.save(consent);

        consentSaved.setStatus(Status.REVOKED);

        consentRepository.save(consentSaved);

        given()
                .contentType(ContentType.JSON)
                .body("Consent deleted")
                .when()
                .delete("/consents/{id}", consentSaved.getId())
                .then()
                .statusCode(200);
    }
}