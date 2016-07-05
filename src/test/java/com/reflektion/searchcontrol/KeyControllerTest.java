package com.reflektion.searchcontrol;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.reflektion.searchcontrol.controller.KeyController;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.service.MockKeyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class KeyControllerTest {

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(new KeyController(new MockKeyService())).build();
	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/key")
				.header("userId","userIdTest")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
