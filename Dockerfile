FROM registry.dev.cargo-partner.com/osjava:8_144a

COPY target/test-infinispan-hollow-swarm.jar test-infinispan-hollow-swarm.jar
COPY target/test-infinispan.war test-infinispan.war
COPY src/main/resources/project-h2.yml project-h2.yml
CMD java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9009,suspend=n \
	-jar test-infinispan-hollow-swarm.jar test-infinispan.war \
	 -Dswarm.bind.address=0.0.0.0 -Dswarm.bind.port=8080 -Djava.net.preferIPv4Stack=true -s project-h2.yml
