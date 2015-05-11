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
WORKDIR $CATALINA_HOME

ENV TOMCAT_MAJOR 7
ENV TOMCAT_VERSION 7.0.57
ENV TOMCAT_TGZ_URL https://www.apache.org/dist/tomcat/tomcat-$TOMCAT_MAJOR/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz

RUN wget -O tomcat.tar.gz http://apache.fayea.com/tomcat/tomcat-8/v8.0.22/bin/apache-tomcat-8.0.22.tar.gz \
	&& tar -xvf tomcat.tar.gz --strip-components=1 \
	&& rm bin/*.bat \
	&& rm tomcat.tar.gz* && rm -fr /usr/local/tomcat/webapps/* && \
	mkdir /usr/local/tomcat/webapps/ROOT
  
RUN apt-get update && \
	apt-get install -y --force-yes zip git && git clone --depth=1 https://github.com/Rekoe/Rk_Cms.git && cd Rk_Cms && \
    /usr/bin/mvn -Dmaven.repo.local=/tmp/clean-repo package war:war && unzip -d /usr/local/tomcat/webapps/ROOT/ target/rk_cms.war && \
	cd .. && rm -fr Rk_Cms && rm -fr /usr/share/maven && rm -fr /usr/bin/mvn && \
	apt-get remove -y zip git && apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
	cd $CATALINA_HOME/webapps/ROOT/WEB-INF/classes/ && sed -i 's/192.168.3.157/tomysql/g' jdbc.properties

EXPOSE 8080
CMD ["catalina.sh", "run"]