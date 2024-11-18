package com.jewel.ergon.jobs.controllers;


import com.jewel.ergon.jobs.model.Contract;
import com.jewel.ergon.jobs.model.ContractType;
import com.jewel.ergon.jobs.model.Currency;
import com.jewel.ergon.jobs.services.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    private Contract contract1;
    private Contract contract2;

    @BeforeEach
    void setUp() {
        contract1 = Contract.builder()
                .id(1L)
                .contractTitle("lawer")
                .contractNumber("C265889")
                .contractType(ContractType.CDI)
                .isCdi(true)
                .startDate(LocalDate.ofEpochDay(2023-10-10))
                .isActive(true)
                .salary(BigDecimal.valueOf(1000L))
                .build();
        contract2 = Contract.builder()
                .id(1L)
                .contractTitle("Masterlawer")
                .contractNumber("C2652229")
                .contractType(ContractType.CDI)
                .isCdi(true)
                .startDate(LocalDate.ofEpochDay(2024-10-10))
                .isActive(true)
                .salary(BigDecimal.valueOf(1500L))
                .build();;
    }

    @Test
    void shouldReturnAllContracts() throws Exception {
        List<Contract> contracts = Arrays.asList(contract1, contract2);
        when(contractService.findAll()).thenReturn(contracts);

        mockMvc.perform(get("/api/v1/contracts/getAllContracts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Contracts retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(contract1.getId()))
                .andExpect(jsonPath("$.data[1].id").value(contract2.getId()));
    }

    @Test
    void shouldReturnContractById() throws Exception {
        when(contractService.findById(1L)).thenReturn(Optional.of(contract1));

        mockMvc.perform(get("/api/v1/contracts/getContractById/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Contract retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(contract1.getId()));
    }


    //TODO pass this test
    @Test
    void shouldReturn404WhenContractNotFound() throws Exception {
        when(contractService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/contracts/getContractById/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewContract() throws Exception {
        when(contractService.save(any(Contract.class))).thenReturn(contract1);

        mockMvc.perform(post("/api/v1/contracts/createContract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Contract 1\",\"details\":\"Details about Contract 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Contract created successfully"))
                .andExpect(jsonPath("$.data.contractTitle").value(contract1.getContractTitle()));
    }

    @Test
    void shouldUpdateExistingContract() throws Exception {
        when(contractService.save(any(Contract.class))).thenReturn(contract1);

        mockMvc.perform(put("/api/v1/contracts/updateContract/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Contract\",\"details\":\"Updated details\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Contract updated successfully"))
                .andExpect(jsonPath("$.data.contractTitle").value(contract1.getContractTitle()));
    }

    @Test
    void shouldDeleteContract() throws Exception {
        doNothing().when(contractService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/contracts/deleteContract/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(204))
                .andExpect(jsonPath("$.message").value("Contract with id: 1 is deleted"));
    }
}
