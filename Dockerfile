FROM java:8-jdk

MAINTAINER wendal "wendal1985@gmail.com"

# add maven

ENV MAVEN_VERSION 3.2.5

RUN curl -sSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven

# add tomcat
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
RUN mkdir -p "$CATALINA_HOME"

RUN cd $CATALINA_HOME \
	&&wget -O tomcat.tar.gz http://apache.fayea.com/tomcat/tomcat-8/v8.0.22/bin/apache-tomcat-8.0.22.tar.gz \
	&& tar -xvf tomcat.tar.gz --strip-components=1 \
	&& cd $CATALINA_HOME \
	&& rm bin/*.bat \
	&& rm tomcat.tar.gz* && rm -fr /usr/local/tomcat/webapps/* && \
	mkdir -p /usr/local/tomcat/webapps/ROOT
  
RUN apt-get update && apt-get install -y --force-yes zip git

RUN cd /tmp && \
	git clone https://github.com/wendal/Rk_Cms.git \
	&& cd Rk_Cms \
	&& /usr/bin/mvn -Dmaven.repo.local=/tmp/clean-repo package war:war \
	&& unzip -d /usr/local/tomcat/webapps/ROOT/ target/rk_cms.war \
	&& cd .. && rm -fr Rk_Cms && rm -fr /usr/share/maven && rm -fr /usr/bin/mvn \
	&& apt-get remove -y zip && apt-get autoremove -y \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* \
	&& cd $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/ \
	&& sed -i 's/192.168.3.157/tomysql/g' jdbc.properties
	
WORKDIR $CATALINA_HOME

EXPOSE 8080
CMD ["catalina.sh", "run"]