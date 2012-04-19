package org.openengsb.labs.paxexam.karaf.bundle.reference.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import static org.junit.Assert.fail;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.*;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class BundleReferenceTest {
	@Inject
	BundleContext bc;

	@Configuration
	public Option[] config() {
		return new Option[] {
				karafDistributionConfiguration()
						.frameworkUrl(
								maven().groupId("org.apache.karaf")
										.artifactId("apache-karaf").type("zip")
										.versionAsInProject())
						.karafVersion("2.2.6").name("Apache Karaf"),
				provision(mavenBundle("org.slf4j", "slf4j-api", "1.6.1"))
				// provision(bundle("reference:file:///home/aromanov/.m2/repository/org/slf4j/slf4j-api/1.6.1/slf4j-api-1.6.1.jar"))
				};
	}

	@Test
	public void slf4jIsInstalled() {
		for (Bundle b : bc.getBundles()) {
			if (b.getSymbolicName().equals("slf4j.api")) {
				return;
			}
		}
		fail("slf4j-api is not provisioned");
	}
}
