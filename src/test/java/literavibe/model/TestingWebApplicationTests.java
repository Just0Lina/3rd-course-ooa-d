package literavibe.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import literavibe.config.Constants;
import literavibe.controllers.BookApiController;
import literavibe.model.dto.IngredientsDistributionDto;
import literavibe.model.dto.BookDto;
import literavibe.model.dto.StepDto;
import literavibe.model.entities.Book;
import literavibe.model.entities.Role;
import literavibe.model.entities.User;
import literavibe.respository.BookRepository;
import literavibe.respository.UserRepository;
import literavibe.security.domain.JwtAuthentication;
import literavibe.services.impl.BookServiceImpl;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class TestingWebApplicationTests {
    @Autowired
    private BookApiController bookApiController;

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void findBookByIdReturnsBook() throws Exception {
        Book book = Book.builder().id(2L).author(User.builder().id("admin").id(1L).build()).name(
                "Super dish").cookTimeMins(30).build();
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        this.mockMvc.perform(get(Constants.BASE_API_PATH + "/books/2")).andExpect(status().isOk()).andExpect(
                content().contentType("application/json")).andExpect(jsonPath("$.id", is(2))).andExpect(
                jsonPath("$.author_id", is("admin"))).andExpect(jsonPath("$.name", is("Super dish"))).andExpect(
                jsonPath("$.cook_time_mins", is(30)));
    }

    @Test
    public void findBookByIdReturnsNotFoundExc() throws Exception {
        this.mockMvc.perform(get(Constants.BASE_API_PATH + "/books/2")).andExpect(status().isNotFound());
    }


    @Test
    public void findBookByNameReturnsNotFoundExc() throws Exception {
        this.mockMvc.perform(get(Constants.BASE_API_PATH + "/books/search/Jr")).andExpect(status().isNotFound());
    }

    @Test
    public void findBookByNameReturnsListOfBooks() throws Exception {
        Book book = Book.builder().id(2L).author(User.builder().id("admin").id(1L).build()).name(
                "Не очень горячая курица").cookTimeMins(40).build();
        Book book2 = Book.builder().id(3L).author(User.builder().id("admin").id(1L).build()).name(
                "Очень-очень горячая курица").cookTimeMins(30).build();
        List<Book> bookList = Arrays.asList(book, book2);
        when(bookRepository.findByNameContaining("Очень", 0)).thenReturn(bookList);

        this.mockMvc.perform(get(Constants.BASE_API_PATH + "/books/search/Очень")).andExpect(
                status().isOk()).andExpect(content().contentType("application/json")).andExpect(
                jsonPath("$[0].id", is(2))).andExpect(jsonPath("$[0].author_id", is("admin"))).andExpect(
                jsonPath("$[0].name", is("Не очень горячая курица"))).andExpect(
                jsonPath("$[0].cook_time_mins", is(40))).andExpect(content().contentType("application/json")).andExpect(
                jsonPath("$[1].id", is(3))).andExpect(jsonPath("$[1].author_id", is("admin"))).andExpect(
                jsonPath("$[1].name", is("Очень-очень горячая курица"))).andExpect(
                jsonPath("$[1].cook_time_mins", is(30)));
    }

    @Test
    public void deleteBookByIdUnauthorized() throws Exception {
        this.mockMvc.perform(delete(Constants.BASE_API_PATH + "/books/2")).andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteBookByIdAdmin() throws Exception {
        Book book = Book.builder().id(2L).author(User.builder().id("admin").id(1L).build()).name(
                "Super dish").cookTimeMins(30).build();
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));

        // Set the authentication context with the desired roles
        Authentication auth = SecurityTestUtils.createAuthenticationWithRoles(roles, "user");

        this.mockMvc.perform(delete(Constants.BASE_API_PATH + "/books/2").with(authentication(auth))).andExpect(
                status().isOk());
    }

    @Test
    public void PostBookUnauthorized() throws Exception {
        Set<Role> roles = new HashSet<>();

        when(userRepository.findById("user")).thenReturn(Optional.of(User.builder().id("user").id(2L).build()));
        Authentication auth = SecurityTestUtils.createAuthenticationWithRoles(roles, "user");
        BookDto bookDto = new BookDto().id(1L).name("Super dish").cookTimeMins(3).ingredientsDistributions(
                Collections.singletonList(
                        new IngredientsDistributionDto().ingredientId(3L).name("salt").measureUnitName(
                                "th"))).authorId("user").mediaId(3L).steps(
                Collections.singletonList(new StepDto().stepNum(1).description("some")));
        Book book = Book.builder().id(1L).author(User.builder().id("user").id(2L).build()).name(
                "Super dish").cookTimeMins(30).build();

        mockMvc.perform(post(Constants.BASE_API_PATH + "/books").contentType(MediaType.APPLICATION_JSON).content(
                asJsonString(bookDto)).with(authentication(auth))).andExpect(status().isForbidden());
        verify(bookRepository, never()).delete(book);
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public class SecurityTestUtils {

        public static Authentication createAuthenticationWithRoles(Set<Role> roles, String login) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication();
            jwtAuthentication.setAuthenticated(true);
            jwtAuthentication.setLogin(login);
            jwtAuthentication.setRoles(roles);
            return jwtAuthentication;
        }
    }
}