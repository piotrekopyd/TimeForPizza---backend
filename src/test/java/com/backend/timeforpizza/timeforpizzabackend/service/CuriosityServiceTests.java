//package com.backend.timeforpizza.timeforpizzabackend.service;
//
//import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
//import com.backend.timeforpizza.timeforpizzabackend.model.Curiosity;
//import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityRequestDTO;
//import com.backend.timeforpizza.timeforpizzabackend.dto.CuriosityResponseDTO;
//import com.backend.timeforpizza.timeforpizzabackend.repository.CuriosityRepository;
//import com.backend.timeforpizza.timeforpizzabackend.util.ModelMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//public class CuriosityServiceTests {
//    @Mock
//    CuriosityRepository curiosityRepository;
//
//    @InjectMocks
//    CuriosityService curiosityService;
//
//    private static final Long CURIOSITY_ID = 1L;
//    private static final Curiosity NEILS_CURIOSITY = new Curiosity(
//            CURIOSITY_ID,
//            "THE THREE MUSKETEERS AUTHOR ALEXANDRE DUMAS WAS ONE OF THE FIRST PEOPLE TO TAKE NOTE OF THE PIZZA TREND.",
//            "In 1835, Alexandre Dumas, author of The Three Musketeers, traveled to Naples, where he observed that the Neapolitan poor ate nothing but watermelon during the summer and pizza during the winter.",
//            "Neil deGrasse Tyson"
//    );
//    private static final CuriosityRequestDTO CURIOSITY_REQUEST = new CuriosityRequestDTO(
//            "THE THREE MUSKETEERS AUTHOR ALEXANDRE DUMAS WAS ONE OF THE FIRST PEOPLE TO TAKE NOTE OF THE PIZZA TREND.",
//            "In 1835, Alexandre Dumas, author of The Three Musketeers, traveled to Naples, where he observed that the Neapolitan poor ate nothing but watermelon during the summer and pizza during the winter.",
//            "Neil deGrasse Tyson"
//    );
//    private static final CuriosityResponseDTO CURIOSITY_RESPONSE = new CuriosityResponseDTO(
//            CURIOSITY_ID,
//            "THE THREE MUSKETEERS AUTHOR ALEXANDRE DUMAS WAS ONE OF THE FIRST PEOPLE TO TAKE NOTE OF THE PIZZA TREND.",
//            "In 1835, Alexandre Dumas, author of The Three Musketeers, traveled to Naples, where he observed that the Neapolitan poor ate nothing but watermelon during the summer and pizza during the winter.",
//            "Neil deGrasse Tyson"
//    );
//
//    @Test
//    void shouldAddCuriosity() {
//        // given
//        Curiosity curiosity = ModelMapper.mapToCuriosity(CURIOSITY_REQUEST);
//        when(curiosityRepository.save(curiosity)).thenReturn(NEILS_CURIOSITY);
//
//        // when
//        CuriosityResponseDTO curiosityResponse = curiosityService.addCuriosity(CURIOSITY_REQUEST);
//
//        // then
//        assertEquals(CURIOSITY_RESPONSE, curiosityResponse);
//    }
//
//    @Test
//    void shouldGetAllCuriosities() {
//        // given
//        when(curiosityRepository.findAll()).thenReturn(List.of(NEILS_CURIOSITY));
//
//        // when
//        var givenCuriosities = curiosityService.getAllCuriosities();
//
//        // then
//        assertEquals(List.of(CURIOSITY_RESPONSE), givenCuriosities);
//    }
//
//    @Test
//    void shouldReturnEmptyListWhenNoCuriositiesFound() {
//        // given
//        when(curiosityRepository.findAll()).thenReturn(List.of());
//
//        // when
//        var givenCuriosities = curiosityService.getAllCuriosities();
//
//        // then
//        assertEquals(List.of(), givenCuriosities);
//    }
//
//    @Test
//    void shouldGetCuriosityById() {
//        // given
//        when(curiosityRepository.findById(CURIOSITY_ID)).thenReturn(Optional.of(NEILS_CURIOSITY));
//
//        // when
//        var givenCuriosity = curiosityService.getCuriosityById(CURIOSITY_ID);
//
//        // then
//        assertEquals(CURIOSITY_RESPONSE, givenCuriosity);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenCuriosityNowFoundById() {
//        // given
//        when(curiosityRepository.findById(CURIOSITY_ID)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> curiosityService.getCuriosityById(CURIOSITY_ID));
//    }
//
//    @Test
//    void shouldDeleteCuriosityById() {
//        // given
//        doNothing().when(curiosityRepository).deleteById(CURIOSITY_ID);
//
//        // when
//        curiosityService.deleteCuriosityById(CURIOSITY_ID);
//
//        // then
//        verify(curiosityRepository).deleteById(CURIOSITY_ID);
//    }
//
//    @Test
//    void shouldUpdateCuriosity() {
//        // given
//        Long curiosityId = 1L;
//        String curiosityAuthor = "Bolesław I the Brave";
//        Curiosity oldCuriosity = new Curiosity(curiosityId, "THE WORD PIZZA DATES BACK TO 997 CE.",
//                "The word pizza dates back over a thousand years.",
//                curiosityAuthor);
//        CuriosityRequestDTO curiosityRequest = new CuriosityRequestDTO("THE WORD PIZZA DATES BACK TO 997 CE.",
//                "The word pizza dates back over a thousand years; it was first mentioned in a Latin text written in southern Italy in 997 CE.",
//                curiosityAuthor);
//        Curiosity newCuriosity = new Curiosity(curiosityId, curiosityRequest.getTitle(), curiosityRequest.getCuriosity(), curiosityAuthor);
//        CuriosityResponseDTO expectedResponse = ModelMapper.mapToCuriosityResponse(newCuriosity);
//        when(curiosityRepository.findById(CURIOSITY_ID)).thenReturn(Optional.of(oldCuriosity));
//        when(curiosityRepository.save(newCuriosity)).thenReturn(newCuriosity);
//
//        // when
//        var givenCuriosity = curiosityService.updateCuriosity(curiosityId, curiosityRequest);
//
//        // then
//        assertEquals(expectedResponse, givenCuriosity);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenCuriosityToUpdateNotFoundById() {
//        // given
//        when(curiosityRepository.findById(CURIOSITY_ID)).thenReturn(Optional.empty());
//
//        // then
//        assertThrows(ResourceNotFoundException.class, () -> curiosityService.updateCuriosity(CURIOSITY_ID, CURIOSITY_REQUEST));
//    }
//}
