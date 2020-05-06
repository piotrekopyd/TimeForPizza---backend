package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
import com.backend.timeforpizza.timeforpizzabackend.utils.ModelMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CuriosityServiceTests {
    @Mock
    CuriosityRepository curiosityRepository;

    @InjectMocks
    CuriosityService curiosityService;

    private List<Curiosity> curiosityData = new ArrayList<>();

    @BeforeEach
    void init() {
        when(curiosityRepository.findById(anyLong())).then(arg -> curiosityData.stream()
                .filter(e -> e.getCuriosityId().equals(arg.getArgument(0)))
                .findFirst());
    }

    @AfterEach
    void clear() {
        curiosityData = new ArrayList<>();
    }

//    @Test
//    public void shouldAddCuriosity() {
//        // GIVEN
//        Curiosity curiosity1 = new Curiosity("first title", "Some curiosity");
//        CuriosityRequestDTO curiosity2 = new CuriosityRequestDTO("second title", "Test curiosity");
//        Curiosity expectedCuriosity1 = new Curiosity(0L, "first title", "Some curiosity");
//        CuriosityResponseDTO expectedCuriosity2 = new CuriosityResponseDTO(1L, "second title", "Test curiosity");
//
//        // WHEN
//        when(curiosityRepository.save(any(Curiosity.class))).then(arg -> {
//            Curiosity curiosity = arg.getArgument(0);
//            if(curiosity.getCuriosityId() != null) {
//                curiosityData.removeIf(e -> e.getCuriosityId().equals(curiosity.getCuriosityId()));
//            } else {
//                long id = curiosityData.size();
//                curiosity.setCuriosityId(id);
//            }
//            curiosityData.add(curiosity);
//            return curiosity;
//        });
//        Curiosity givenCuriosity1 = curiosityService.addCuriosity(curiosity1);
//        CuriosityResponseDTO givenCuriosity2 = curiosityService.addCuriosity(curiosity2);
//
//        // THEN
//        assertEquals(expectedCuriosity1, givenCuriosity1);
//        assertEquals(expectedCuriosity2, givenCuriosity2);
//    }

//    @Test
//    public void shouldGetCuriosityById() {
//        // GIVEN
//        Curiosity curiosity = new Curiosity(1L,"test title", "test curiosity");
//        Curiosity curiosity1 = new Curiosity(2L, "test title1", "test curiosity1");
//        Curiosity curiosity2 = new Curiosity(10L,"test title2", "test curiosity2");
//        CuriosityResponseDTO expectedCuriosity = new CuriosityResponseDTO(1L,"test title", "test curiosity");
//        CuriosityResponseDTO expectedCuriosity2 = new CuriosityResponseDTO(10L,"test title2", "test curiosity2");
//        curiosityData.addAll(List.of(curiosity,curiosity1,curiosity2));
//
//        // WHEN
//        CuriosityResponseDTO givenCuriosity = curiosityService.getCuriosityById(1L);
//        CuriosityResponseDTO givenCuriosity2 = curiosityService.getCuriosityById(10L);
//
//        // THEN
//        assertEquals(expectedCuriosity, givenCuriosity);
//        assertEquals(expectedCuriosity2, givenCuriosity2);
//    }

//    @Test
//    public void shouldThrowExceptionWhenCuriosityNotFoundById() {
//        // GIVEN
//        Curiosity curiosity = new Curiosity(1L,"test title", "test curiosity");
//        Curiosity curiosity1 = new Curiosity(2L, "test title1", "test curiosity1");
//        curiosityData.addAll(List.of(curiosity,curiosity1));
//
//        // THEN
//        assertThrows(ResourceNotFoundException.class, () -> curiosityService.getCuriosityById(3L));
//    }

//    @Test
//    public void shouldGetAllCuriosities() {
//        // GIVEN
//        Curiosity curiosity = new Curiosity(1L,"test title", "test curiosity");
//        Curiosity curiosity1 = new Curiosity(2L, "test title1", "test curiosity1");
//        curiosityData = List.of(curiosity,curiosity1);
//        List<CuriosityResponseDTO> expectedCuriosities = curiosityData.stream()
//                .map(ModelMapper::mapCuriosityToCuriosityResponse)
//                .collect(Collectors.toList());
//
//        // WHEN
//        when(curiosityRepository.findAll()).thenReturn(curiosityData);
//        List<CuriosityResponseDTO> givenCuriosities = curiosityService.getAllCuriosities();
//
//        // THEN
//        assertEquals(expectedCuriosities, givenCuriosities);
//    }

//    @Test
//    public void shouldDeleteCuriosityById() {
//        // GIVEN
//        long curiosityId = 2L;
//
//        // WHEN
//        curiosityService.deleteCuriosityById(curiosityId);
//
//        // THEN
//        verify(curiosityRepository, times(1)).deleteById(eq(curiosityId));
//    }
}
