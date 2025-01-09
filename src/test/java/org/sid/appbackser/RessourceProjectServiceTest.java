package org.sid.appbackser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.RessourceProject;
import org.sid.appbackser.repositories.RessourceProjectRepository;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.RessourceProjectService;
import org.sid.appbackser.services.implementations.RessourceProjectServiceImp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RessourceProjectServiceTest {

    @Mock
    private DepotService depotService;

    @Mock
    private RessourceProjectRepository ressourceProjectRepository;

    @InjectMocks
    private RessourceProjectServiceImp ressourceProjectService;

    @Test
    void testCreateRessourceProject() {
        // Mock the behavior of depotService to not create actual folders
        when(depotService.createDepot(anyString(), any())).thenReturn(new Depot());

        // Perform the action
        Project project = new Project();  // Dummy project
        RessourceProject ressourceProject = ressourceProjectService.createRessourceProject(project);

        // Verify that the mocked createDepot method was called 9 times
        verify(depotService, times(9)).createDepot(anyString(), any());
        
        // Check that the repository's save method was called once
        verify(ressourceProjectRepository, times(1)).save(any(RessourceProject.class));
        
        // Optionally, assert that the service returned the expected result
        assertNotNull(ressourceProject);
    }
}


