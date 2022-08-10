package com.omdb.api.services.impl;

import com.omdb.api.exception.util.UnAuthorizedBasicUser;
import com.omdb.api.model.Movie;
import com.omdb.api.repositories.MoviesRepository;
import com.omdb.api.services.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OmdbServiceImpl implements OmdbService {
    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Value("${omdb.api.url}")
    private String omdbApiUrl;


    @Override
    public Movie getMovieDetail(Map<String, Object> params, User user) throws UnAuthorizedBasicUser {
        String apiURL=omdbApiUrl;
        for (Map.Entry entry : params.entrySet()) {
            apiURL=apiURL+entry.getKey()+"="+entry.getValue()+"&";
        }
        apiURL=apiURL+"apikey="+apiKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        System.out.println(apiURL);
        ResponseEntity<Movie> response=restTemplate.exchange(apiURL, HttpMethod.GET, entity, Movie.class);
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASIC")) && response.getBody().getYe_ar()!=null && !response.getBody().getYe_ar().trim().equalsIgnoreCase("") && Integer.parseInt(response.getBody().getYe_ar().trim())<2010){
            throw new UnAuthorizedBasicUser("You are UnAuthorized to access movies before Year 2010 as you are BASIC user");
        }
        if(response.getStatusCode().is2xxSuccessful() && response.getBody()!=null && response.getBody().getResponse().equalsIgnoreCase("True") && moviesRepository.findByImdbID(response.getBody().getImdbID())==null){
            moviesRepository.save(response.getBody());
        }

        return response.getBody();

    }

    @Override
    public List<Movie> fetchAllMovies(User user, String sort, String by) throws UnAuthorizedBasicUser {
        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASIC"))){
            throw new UnAuthorizedBasicUser("UnAuthorized to fetch All Movies");
        }

        if(sort!=null){
            return moviesRepository.findAll(Sort.by(by!=null && by.equalsIgnoreCase("ASC")?Sort.Direction.ASC:Sort.Direction.DESC, sort));
        }else{
            return moviesRepository.findAll();
        }

    }
}
