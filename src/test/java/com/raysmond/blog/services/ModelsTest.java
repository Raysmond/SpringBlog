package com.raysmond.blog.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.raysmond.blog.repositories.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author: Raysmond
 */
@RunWith(MockitoJUnitRunner.class)
public class ModelsTest {
    @Mock
    private UserRepository users;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void modelShouldnotBeNull() {

        assertThat(users != null);
    }

}

