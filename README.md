Vaadin Hibernate Portlet Example
=====

This is an attempt to port the [Vaadin hibernate example application][vhbn] to a Liferay portlet and ported to be built using Maven. For more info, see the [original project in Vaadin incubator][vhbn], [Developing Vaadin Applications as Liferay Portlets][vplt], and [Using Vaadin with Maven][vmvn].

This example now uses the [HbnContainer Vaadin Add-on][hbnpl]. 

*This portlet currently will deploy, but does nothing. Hope to have this fixed in the future, but please feel free to contribute.*

Feel free to contact me via GitHub about it/submit issues to this GitHub project or leave a note on [this Vaadin forum thread][forumthread].

### Quickstart

This is only quick if you've done most of it already and know what you are doing, and assumes *nix.

#### Download/Install Prequisites

Java (JDK): http://www.oracle.com/technetwork/java/javase/downloads/index.html

Maven: http://maven.apache.org/

Git: http://git-scm.com/

#### Download/Install Liferay

1. Download from: http://www.liferay.com/downloads/liferay-portal/available-releases
2. Extract to your home directory.

#### Get a Local Copy of the Source Code Repository

    cd ~
    git clone git://github.com/garysweaver/vaadin-hibernate-portlet.git

#### Build the Portlet

    cd ~/vaadin-hibernate-portlet
    mvn clean install

#### Start Liferay

    cd ~/liferay-portal-6.0.6/tomcat-6.0.29/
    ./startup.sh
    
This may open up a browser, when it is done. Otherwise, verify portal started correctly or "fix" and repeat:

    tail -f ~/liferay-portal-6.0.6/tomcat-6.0.29/catalina.out

#### Deploy the Portlet

    cp ~/vaadin-hibernate-portlet/target/vaadin-hibernate-portlet.war ~/liferay-portal-6.0.6/tomcat-6.0.29/deploy/
    
#### Verify Deployment

    tail -f ~/liferay-portal-6.0.6/tomcat-6.0.29/catalina.out

#### Login to Liferay

1. Go to: http://localhost:8080/

2. Login as the default Liferay user: username: bruno@7cogs.com password: bruno

#### Add the Portlet to Your View

1. Click *Add* at the very top left and then below that choose *More...*

2. Click *Vaadin*

3. Click to add *vaadin-hibernate-portlet*

### License

All modifications to the [original application][vhbn] are released under the copyright (c) 2011 Gary S. Weaver, released under the [MIT license][lic]. The [original application][vhbn]'s license is unknown but is open source and probably falls under the [Apache 2.0 license][apache]. Vaadin is released under the [Apache 2.0 license][apache]. 

[vhbn]: http://dev.vaadin.com/svn/incubator/hbncontainer/
[vplt]: http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/Developing+Vaadin+Applications+as+Liferay+Portlets
[vmvn]: http://vaadin.com/wiki/-/wiki/Main/Using%20Vaadin%20with%20Maven
[hbnpl]: http://vaadin.com/directory/-/directory/addon/hbncontainer
[lic]: http://github.com/garysweaver/vaadin-hibernate-portlet/blob/master/LICENSE
[apache]: http://www.apache.org/licenses/LICENSE-2.0
[forumthread]: http://vaadin.com/forum/-/message_boards/view_message/442390