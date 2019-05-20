package com.tallrye.wlearn.controller;


import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.security.UserPrincipal;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractEntityControllerTest {

    public static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = TestUtils.createDummyCurrentUser();
    }
}
