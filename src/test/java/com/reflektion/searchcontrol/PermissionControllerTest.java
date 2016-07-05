package com.reflektion.searchcontrol;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.controller.PermissionController;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.repository.KeyRepository;
import com.reflektion.searchcontrol.repository.PermissionKeyRepository;
import com.reflektion.searchcontrol.repository.PermissionRepository;
import com.reflektion.searchcontrol.repository.UserRepository;
import com.reflektion.searchcontrol.service.PermissionService;
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
public class PermissionControllerTest {

	private MockMvc mvc;

    @Mock
    private PermissionRepository permissionRepository;

	@Mock
    private KeyRepository keyRepository;

	@Mock
	private PermissionKeyRepository permissionKeyRepository;

    @Mock
    private UserRepository userRepository;



    @Before
	public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(permissionRepository.findPermissionByroleIn(Arrays.asList(1L))).thenReturn(Sets.newHashSet(new Permission(1L ,"p1")));
		mvc = MockMvcBuilders.standaloneSetup(new PermissionController(
                new PermissionService(permissionRepository, permissionKeyRepository, keyRepository, userRepository))).build();
	}

	@Test
	public void getPermissions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/permission?roleId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
}
