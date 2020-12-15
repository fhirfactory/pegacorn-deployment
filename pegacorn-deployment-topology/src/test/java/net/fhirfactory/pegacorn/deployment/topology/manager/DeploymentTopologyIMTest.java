package net.fhirfactory.pegacorn.deployment.topology.manager;

import net.fhirfactory.pegacorn.deployment.topology.map.archetypes.PegacornSolutionTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RunWith(Arquillian.class)
public class DeploymentTopologyIMTest {
    private static final Logger LOG = LoggerFactory.getLogger(PegacornSolutionTest.class);

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive testWAR;

        PomEquippedResolveStage pomEquippedResolver = Maven.resolver().loadPomFromFile("pom.xml");
        PomEquippedResolveStage pomEquippedResolverWithRuntimeDependencies = pomEquippedResolver.importRuntimeDependencies();
        MavenStrategyStage mavenResolver = pomEquippedResolverWithRuntimeDependencies.resolve();
        MavenFormatStage mavenFormat = mavenResolver.withTransitivity();
        File[] fileSet = mavenFormat.asFile();
        LOG.debug(".createDeployment(): ShrinkWrap Library Set for run-time equivalent, length --> {}", fileSet.length);
        for (int counter = 0; counter < fileSet.length; counter++) {
            File currentFile = fileSet[counter];
            LOG.trace(".createDeployment(): Shrinkwrap Entry --> {}", currentFile.getName());
        }
        testWAR = ShrinkWrap.create(WebArchive.class, "pegacorn-deployment-topology.war")
                .addAsLibraries(fileSet)
                .addPackages(true, "net.fhirfactory.pegacorn.deployment.topology")
                .addAsManifestResource("META-INF/beans.xml", "WEB-INF/beans.xml");
        if (LOG.isTraceEnabled()) {
            Map<ArchivePath, Node> content = testWAR.getContent();
            Set<ArchivePath> contentPathSet = content.keySet();
            Iterator<ArchivePath> contentPathSetIterator = contentPathSet.iterator();
            while (contentPathSetIterator.hasNext()) {
                ArchivePath currentPath = contentPathSetIterator.next();
                LOG.trace(".createDeployment(): testWAR Entry Path --> {}", currentPath.get());
            }
        }
        return (testWAR);
    }

    @Test
    public void getProcessingPlantInstanceID() {
    }

    @Test
    public void getWUPIdentifier() {
    }

    @Test
    public void getProcessingPlantFunctionToken() {
    }

    @Test
    public void getWUPFunctionToken() {
    }

    @Test
    public void registerNode() {
    }

    @Test
    public void addContainedNodeToNode() {
    }

    @Test
    public void unregisterNode() {
    }

    @Test
    public void getNodeSet() {
    }

    @Test
    public void getNode() {
    }

    @Test
    public void testGetNode() {
    }

    @Test
    public void getNodeByKey() {
    }

    @Test
    public void registerLink() {
    }

    @Test
    public void unregisterLink() {
    }

    @Test
    public void getLinkSet() {
    }

    @Test
    public void getLink() {
    }

    @Test
    public void registerEndpoint() {
    }

    @Test
    public void unregisterEndpoint() {
    }

    @Test
    public void getEndpointSet() {
    }

    @Test
    public void getEndpoint() {
    }

    @Test
    public void getNodesWithMatchinUnqualifiedInstanceName() {
    }

    @Test
    public void getSolutionID() {
    }

    @Test
    public void getConcurrencyMode() {
    }

    @Test
    public void getDeploymentResilienceMode() {
    }

    @Test
    public void setNodeInstantiated() {
    }
}
