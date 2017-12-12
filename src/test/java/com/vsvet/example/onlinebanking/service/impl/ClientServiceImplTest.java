package com.vsvet.example.onlinebanking.service.impl;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.domain.ClientStatus;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.service.ClientService;
import com.vsvet.example.onlinebanking.service.ClientServiceTestConfig;
import com.vsvet.example.onlinebanking.view.ClientStatusView;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ClientServiceTestConfig.class})
public class ClientServiceImplTest extends AbstractTest {

    private ClientView clientView;

    @Autowired
    private ClientRepository clientRepository;
    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;

    @Autowired
    private ClientService toTest;

    @Before
    public void setup() {
        prepareClientRepository();
        clientView = clientView();
    }

    @After
    public void cleanup() {
        reset(clientRepository);
    }

    @Test
    public void testClientCreatedWithExpectedFieldValues() {
        toTest.create(clientView);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(CLIENT_EMAIL, clientArgumentCaptor.getValue().getEmail());
        assertEquals(CLIENT_FIRSTNAME, clientArgumentCaptor.getValue().getFirstName());
        assertEquals(CLIENT_LASTNAME, clientArgumentCaptor.getValue().getLastName());
        assertEquals(CLIENT_ADDRESS, clientArgumentCaptor.getValue().getAddress());
        assertEquals(CLIENT_ID_CARD, clientArgumentCaptor.getValue().getIdentityCard());
        assertEquals(CLIENT_PASSWORD, clientArgumentCaptor.getValue().getPassword());
        assertEquals(CLIENT_PHONE_NUMBER, clientArgumentCaptor.getValue().getPhoneNumber());
    }

    @Test
    public void testClientIsActivatedOnCreate() {
        toTest.create(clientView);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(ClientStatus.ACTIVE, clientArgumentCaptor.getValue().getStatus());
    }

    @Test
    public void testFindExistingClientByEmail() {
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(client()));
        Optional<ClientView> view = toTest.find(CLIENT_EMAIL);
        assertTrue(view.isPresent());
        assertEquals(CLIENT_EMAIL, view.get().getEmail());
        assertEquals(CLIENT_FIRSTNAME, view.get().getFirstName());
        assertEquals(CLIENT_LASTNAME, view.get().getLastName());
        assertEquals(CLIENT_ADDRESS, view.get().getAddress());
        assertEquals(CLIENT_ID_CARD, view.get().getIdentityCard());
        assertNull(view.get().getPassword());
        assertEquals(CLIENT_PHONE_NUMBER, view.get().getPhoneNumber());
        assertEquals(ClientStatusView.valueOf(CLIENT_STATUS.name()), view.get().getStatus());
    }

    @Test
    public void testFindNotExistingClientByEmail() {
        Optional<ClientView> view = toTest.find(CLIENT_EMAIL);
        assertFalse(view.isPresent());
    }

    private void prepareClientRepository() {
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.empty());
    }

}