Vaadin Hibernate Portlet Example
=====

This is an attempt to port the [Vaadin hibernate example application][vhbn] to a Liferay portlet and ported to be built using Maven. For more info, see the [original project in Vaadin incubator][vhbn], [Developing Vaadin Applications as Liferay Portlets][vplt], and [Using Vaadin with Maven][vmvn].

*This portlet currently will deploy, but does nothing. Hope to have this fixed in the future, but please feel free to contribute.*

### Build the Portlet

Install Maven 2 and Java 6 and build with:

    mvn clean install

### Deploy the Portlet

Then copy target/vaadin-hibernate-portlet.war to your (Liferay 6.x)/deploy directory to deploy (if Tomcat is running and using the default distribution of Liferay 6.0.6 or later).

### License

All modifications to the [original application][vhbn] are released under the copyright (c) 2011 Gary S. Weaver, released under the [MIT license][lic]. The [original application][vhbn]'s license is unknown but is open source and probably falls under the [Apache 2.0 license][apache]. Vaadin is released under the [Apache 2.0 license][apache]. 

[vhbn]: http://dev.vaadin.com/svn/incubator/hbncontainer/
[vplt]: http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/Developing+Vaadin+Applications+as+Liferay+Portlets
[vmvn]: http://vaadin.com/wiki/-/wiki/Main/Using%20Vaadin%20with%20Maven
[lic]: http://github.com/garysweaver/vaadin-hibernate-portlet/blob/master/LICENSE
[apache]: http://www.apache.org/licenses/LICENSE-2.0