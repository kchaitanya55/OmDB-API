package com.omdb.api.contoller;

import com.omdb.api.exception.util.UnAuthorizedBasicUser;
import com.omdb.api.model.Movie;
import com.omdb.api.services.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/movies")
public class OmdbController {
    @Autowired
    private OmdbService omdbService;

    @GetMapping("/search")
    public Movie getMovieDetails(@RequestParam Map<String,Object> params,@AuthenticationPrincipal User user) throws UnAuthorizedBasicUser {
        return omdbService.getMovieDetail(params,user);
    }

    @GetMapping("/")
    public List<Movie> fetchAllMovies(@RequestParam(value = "sort",required = false) String sort,@RequestParam(value = "by",required = false) String by,@AuthenticationPrincipal User user) throws UnAuthorizedBasicUser {
        return omdbService.fetchAllMovies(user,sort,by);
    }



}
