package com.omdb.api.services;

import com.omdb.api.exception.util.UnAuthorizedBasicUser;
import com.omdb.api.model.Movie;
import com.omdb.api.repositories.MoviesRepository;
import com.omdb.api.services.impl.OmdbServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OmdbServiceTest {
    @InjectMocks
    private OmdbServiceImpl omdbService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MoviesRepository moviesRepository;

    @Test
    void getMovieDetailTestPositive() throws UnAuthorizedBasicUser {
        Map<String,Object> params=mock(Map.class);
        params.put("t","a");
        User user=mock(User.class);
        ResponseEntity<Movie> responseEntity=mock(ResponseEntity.class);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);
        Assertions.assertDoesNotThrow(()->omdbService.getMovieDetail(params,user));
    }

    @Test
    void getMovieDetailTestNegative() throws UnAuthorizedBasicUser {
        Map<String,Object> params=mock(Map.class);
        params.put("t","a");
        User user=mock(User.class);
        when(user.getAuthorities()).thenReturn(Collections.singleton(new SimpleGrantedAuthority("ROLE_BASIC")));
        ResponseEntity<Movie> responseEntity=mock(ResponseEntity.class);
        Movie movie=new Movie();
        movie.setYe_ar("2009");
        when(responseEntity.getBody()).thenReturn(movie);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class),any(HttpEntity.class), any(Class.class))).thenReturn(responseEntity);
        Assertions.assertThrows(UnAuthorizedBasicUser.class,()->omdbService.getMovieDetail(params,user));
    }

    @Test
    void fetchAllMoviesPositiveTest() throws UnAuthorizedBasicUser {
        User user=mock(User.class);
        when(moviesRepository.findAll()).thenReturn(new ArrayList<>());
        Assertions.assertDoesNotThrow(()->omdbService.fetchAllMovies(user,null,null));;

    }

    @Test
    void fetchAllMoviesNegativeTest() throws UnAuthorizedBasicUser {
        User user=mock(User.class);
        when(user.getAuthorities()).thenReturn(Collections.singleton(new SimpleGrantedAuthority("ROLE_BASIC")));
        Assertions.assertThrows(UnAuthorizedBasicUser.class,()->omdbService.fetchAllMovies(user,null,null));;

    }


}
