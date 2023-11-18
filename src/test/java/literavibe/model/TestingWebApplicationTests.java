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
import literavibe.model.dto.BookDto;
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


}