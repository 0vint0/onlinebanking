package com.vsvet.example.onlinebanking.service;

import com.vsvet.example.onlinebanking.AbstractTest;
import com.vsvet.example.onlinebanking.ErrorMessageMatcher;
import com.vsvet.example.onlinebanking.ValidationTestConfig;
import com.vsvet.example.onlinebanking.domain.Client;
import com.vsvet.example.onlinebanking.repository.ClientRepository;
import com.vsvet.example.onlinebanking.view.ClientView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static com.vsvet.example.onlinebanking.BeanTestUtils.*;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Tests almost only validation layer of service
 */
@ContextConfiguration(classes = {ClientServiceTestConfig.class, ValidationTestConfig.class})
public class ClientServiceTest extends AbstractTest {

    private ClientView clientView;

    @Mock
    private Client existingClient;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService toTest;

    @Before
    public void setup() {
        clientView = clientView();
        prepareClientRepository();
    }

    @After
    public void cleanup(){
        reset(clientRepository);
    }

    //Creating client validations
    @Test
    public void testCreateClientPassingValidationWithSuccess() {
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoEmailProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'email' must not be blank"));
        clientView.setEmail(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedEmailHasInvalidFormat() {
        thrown.expect(ErrorMessageMatcher.matches("Invalid email format!"));
        clientView.setEmail("sda78e");
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoFirstNameProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'firstName' must not be blank"));
        clientView.setFirstName(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoLastNameProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'lastName' must not be blank"));
        clientView.setLastName(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoAddressProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'address' must not be blank"));
        clientView.setAddress(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoIdentityCardProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'identityCard' must not be blank"));
        clientView.setIdentityCard(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoPhoneProvided() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'phoneNumber' must not be blank"));
        clientView.setPhoneNumber(null);
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedNoPhoneNumberHasInvalidFormat() {
        thrown.expect(ErrorMessageMatcher.matches("Invalid phone format 'yyyy' , expect to be ^((0))[0-9]{9}$ !"));
        clientView.setPhoneNumber("yyyy");
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedEmailIsAlreadyUsed() {
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.of(existingClient));
        thrown.expect(ErrorMessageMatcher.matches("Email '" + CLIENT_EMAIL + "' is already in use!"));
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedPasswordHasInValidLength() {
        thrown.expect(ErrorMessageMatcher.matches("Field 'password' has invalid length, expected [min>=8, max<infinite]!"));
        clientView.setPassword("12");
        toTest.create(clientView);
    }

    @Test
    public void testCreateClientFailedConfirmPasswordIsDifferent() {
        thrown.expect(ErrorMessageMatcher.matches("Confirm password is different!"));
        clientView.setConfirmPassword("123333333333333");
        toTest.create(clientView);
    }
    //

    private void prepareClientRepository() {
        when(clientRepository.findOneByEmail(CLIENT_EMAIL)).thenReturn(Optional.empty());
        when(clientRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
    }
}