package org.example.parkbuddy.service;

import org.example.parkbuddy.dto.CreateParkingSpaceDTO;
import org.example.parkbuddy.model.ParkingSpace;
import org.example.parkbuddy.repository.ParkingSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParkingSpaceServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<ParkingSpace> parkingSpaceList = new ArrayList<>();

    @BeforeEach
    void setup() {
        parkingSpaceRepository.deleteAll();

        ArrayList<ParkingSpace> parkingSpaces = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setName("Parking Space " + i);
            parkingSpaces.add(parkingSpace);
        }

        parkingSpaceList = parkingSpaceRepository.saveAll(parkingSpaces);
    }

    @Test
    void shouldGetAllParkingSpaces() throws Exception {
        mockMvc.perform(get("/parking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name").value("Parking Space 1"))
                .andExpect(jsonPath("$[1].name").value("Parking Space 2"))
                .andExpect(jsonPath("$[2].name").value("Parking Space 3"));
    }

    @Test
    void shouldGetParkingSpaceById() throws Exception {
        mockMvc.perform(get(String.format("/parking/get?id=%d", parkingSpaceList.getFirst().getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(parkingSpaceList.getFirst().getId()))
                .andExpect(jsonPath("$.name").value("Parking Space 1"));
    }

    @Test
    void shouldThrowExceptionWhenGetParkingSpaceByIdNotFound() throws Exception {
        mockMvc.perform(get(String.format("/parking/get?id=%d", 0)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Parking space (#0) not found"));
    }

    @Test
    void shouldCreateParkingSpace() throws Exception {
        CreateParkingSpaceDTO dto = new CreateParkingSpaceDTO();
        dto.name = "new parking space";

        mockMvc.perform(
                post("/parking/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(parkingSpaceList.getLast().getId() + 1))
                .andExpect(jsonPath("$.name").value("new parking space"));
    }

    @Test
    void shouldRemoveParkingSpace() throws Exception {
        mockMvc.perform(delete(String.format("/parking/delete?id=%d", parkingSpaceList.getFirst().getId())))
                .andExpect(status().isOk());
    }
}
