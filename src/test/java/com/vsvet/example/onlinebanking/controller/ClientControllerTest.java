package com.vsvet.example.onlinebanking.controller;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.config.ObjectMapperConfig;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ClientController.class})
public class ClientControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<ClientView> clientViewCaptor;

    @MockBean
    private ClientApi clientApi;

    @Test
    public void testSignUp() throws Exception {
        this.mockMvc.perform(
                post("/clients")
                        .content(ObjectMapperConfig.objectMapper().writeValueAsBytes(clientView()))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
        verify(clientApi).signUp(clientViewCaptor.capture());
        assertEquals(CLIENT_EMAIL, clientViewCaptor.getValue().getEmail());
        assertEquals(CLIENT_ADDRESS, clientViewCaptor.getValue().getAddress());
        assertEquals(CLIENT_FIRSTNAME, clientViewCaptor.getValue().getFirstName());
        assertEquals(CLIENT_LASTNAME, clientViewCaptor.getValue().getLastName());
        assertEquals(CLIENT_PHONE_NUMBER, clientViewCaptor.getValue().getPhoneNumber());
        assertEquals(CLIENT_PASSWORD, clientViewCaptor.getValue().getPassword());
        assertEquals(CLIENT_PASSWORD, clientViewCaptor.getValue().getConfirmPassword());
    }

    @Test
    public void testGetClientDetails() throws Exception {
        when(clientApi.findClient(CLIENT_EMAIL)).thenReturn(Optional.of(clientView()));
        byte[] response = this.mockMvc.perform(get("/clients/{email}/", CLIENT_EMAIL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsByteArray();
        ClientView view = ObjectMapperConfig.objectMapper().readValue(response, ClientView.class);
        assertEquals(CLIENT_EMAIL, view.getEmail());
        assertEquals(CLIENT_ADDRESS, view.getAddress());
        assertEquals(CLIENT_FIRSTNAME, view.getFirstName());
        assertEquals(CLIENT_LASTNAME, view.getLastName());
        assertEquals(CLIENT_PHONE_NUMBER, view.getPhoneNumber());
    }


}