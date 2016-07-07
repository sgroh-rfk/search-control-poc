FROM buildpack-deps:jessie
MAINTAINER Sebastian Groh <sebastian.groh@reflektion.com>
# add webupd8 repository
RUN \
    echo "===> add webupd8 repository..."  && \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886  && \
    apt-get update  && \
    \
    \
    echo "===> install Java"  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default  && \
    \
    \
    echo "===> clean up..."  && \
    rm -rf /var/cache/oracle-jdk8-installer  && \
    apt-get clean  && \
    rm -rf /var/lib/apt/lists/*


# Create app directory
RUN mkdir -p /opt/reflektion/search-control-poc
WORKDIR /opt/reflektion/search-control-poc

#The idea is to run this on this docker image once it's created
#java -Dserver.port=8090 -Dspring.profiles.active=local-dev -jar /opt/reflektion/search-control-poc/rfk-pc-0.1.0.jar

EXPOSE 8090
CMD java -Dserver.port=8090 -Dspring.profiles.active=$ENVIRONMENT -jar rfk-pc-0.1.0.jar

#docker build -t search-control-poc .

#sample command to run the nodejs box
#docker run -v $PWD/public/src:/opt/reflektion/dashboard/public/src -v $PWD/apps:/opt/reflektion/dashboard/apps -v $PWD/lib:/opt/reflektion/dashboard/lib -p 3000:3000 -d dashboard-node

