package com.raysmond.blog.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sega.viewer.models.Process;
/**
 *
 * @author: Raysmond
 */
@RunWith(MockitoJUnitRunner.class)
public class ModelsTest {

    @Mock
    private IModel model;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void modelShouldnotBeNull() {

        assertThat(model != null);
    }

}

