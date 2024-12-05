//package com.tbf.browse;
//
//import com.tbf.browse.controller.SearchController;
//import com.tbf.browse.service.SearchService;
//import com.tbf.browse.service.RedisService;
//import com.tbf.browse.entiry.Movie;
//import com.tbf.browse.utils.UtilsMockData;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class BrowseServiceApplicationTests {
//
//	private MockMvc mockMvc;
//
//	@Mock
//	private SearchService searchService;
//
//	@Mock
//	private RedisService redisService;
//
//	@Value("${baseurl}")
//	private String baseUrl;
//
//	@InjectMocks
//	private SearchController movieController;
//
//	@BeforeEach
//	public void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
//	}
//
//	@Test
//	public void testFindMovies() throws Exception {
//		List<Movie> mockMovies = UtilsMockData.getMovies();
//
//		when(redisService.getFromRedis("agra")).thenReturn(mockMovies);
//		when(searchService.findMovies("agra", null, null, null)).thenReturn(mockMovies);
//
//		mockMvc.perform(get("/api/v1/agra"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].title").value("Inception"))
//				.andExpect(jsonPath("$[0].language").value("English"));
//
//		verify(redisService).getFromRedis("agra");
//	}
//
//	@Test
//	public void testFindMoviesWithFallback() throws Exception {
//		List<Movie> mockMovies = UtilsMockData.getMovies();
//
//		when(redisService.getFromRedis("agra")).thenReturn(null);
//		when(searchService.findMovies("agra", null, null, null)).thenReturn(mockMovies);
//
//		mockMvc.perform(get("/api/v1/movie/agra"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].title").value("Inception"))
//				.andExpect(jsonPath("$[0].language").value("English"));
//
//		verify(redisService).getFromRedis("agra");
//		verify(searchService).findMovies("agra", null, null, null);UtilsMockData.getMovies();
//	}
//
//	@Test
//	public void testFindMoviesWhenNoMoviesFound() throws Exception {
//		List<Movie> emptyList = List.of();
//
//		when(redisService.getFromRedis("agra")).thenReturn(emptyList);
//		when(searchService.findMovies("agra", null, null, null)).thenReturn(emptyList);
//
//
//		mockMvc.perform(get("/api/v1/movie/agra"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$").isEmpty()); // Check if no movies are returned
//
//		verify(redisService).getFromRedis("agra");
//		verify(searchService).findMovies("agra", null, null, null);UtilsMockData.getMovies();
//	}
//
//	@Test
//	public void testFindMoviesWithRedisCache() throws Exception {
//		List<Movie> mockMovies = UtilsMockData.getMovies();
//
//		when(redisService.getFromRedis("agra")).thenReturn(mockMovies);
//
//
//		mockMvc.perform(get("/api/v1/movie/agra"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].title").value("Inception"))
//				.andExpect(jsonPath("$[0].language").value("English"));
//
//		verify(redisService).getFromRedis("agra");
//	}
//}
