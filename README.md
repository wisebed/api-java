WISEBED API
======
This project provides JAXB Web service bindings for the official WISEBED Web services.
The classes provided here were created using the [official WSDL files](http://wisebed.eu/api/wsdl/)
using the JAXB compiler and manually enhanced with documentation and some little code tweaks.


Building 
======
No installation is required. To build, you need 
Java 6 or higher and [Maven 2](http://maven.apache.org/) or higher. 

Before cloning this repository, be sure to enable automatic conversion 
of CRLF/LF on your machine using ```git config --global core.autocrlf input```. 
For more information, please refer to [this article](http://help.github.com/dealing-with-lineendings/).

Clone the repository using ```git clone git@github.com:wisebed/api-java.git```.
To build, run ```mvn install```, this will build the program and place the 
generated jar file in target/ and in your local Maven repository.

Use in your Maven project
======

Add the following dependency to your pom.xml:
  
	<dependency>
		<groupId>eu.wisebed</groupId>
		<artifactId>api</artifactId>
		<version>2.3</version>
	</dependency>
	
Add the following repository to your pom.xml:

```
<repositories>
	<repository>
		<id>wisebed-maven-repository-releases</id>
		<url>http://wisebed.eu/maven/releases/</url>
		<releases>
			<enabled>true</enabled>
		</releases>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
	</repository>
</repositories>
```
  
If you also want to work with SNAPSHOT dependencies, also add:

```
<repositories>
  <repository>
		<id>wisebed-maven-repository-snapshots</id>
		<url>http://wisebed.eu/maven/snapshots/</url>
		<releases>
			<enabled>false</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
</repositories>
```

Contact
======
Any feedback will be greatly appreciated, at the GitHub project page
(https://github.com/wisebed/api-java) or by contacting
[danbim](mailto:bimschas@itm.uni-luebeck.de) or [pfisterer](mailto:pfisterer@itm.uni-luebeck.de).