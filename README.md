# AWS_Device_Farm_Android_Sample(Android Messaging App)
This repo contains AWS Device Farm enabled test cases for Android

This is a collection of example Appium TestNG tests written for the AWS Device Farm [Android](https://github.com/awslabs/aws-device-farm-sample-app-for-android) sample app. Please use these tests as a reference for your own AWS Device Farm Appium TestNG tests.

**This test suite uses the [Appium page model](http://appium.io/slate/en/tutorial/android?java#page-object-pattern) in order to separate the tests from the logic.**

## Getting Started
1. Follow the **[official Appium getting started guide](http://appium.io/slate/en/tutorial/android.html?java#getting-started-with-appium)** and install the Appium server and dependencies. 

	**AWS Device Farm supports Appium versions 1.6.5/1.7.1/1.7.2. Using a different version locally may cause unexpected results when running Appium tests on AWS Device Farm.**
  
2. In order to use 1.7.2, download Appium through NPM with this command: 
	```
	npm install -g appium@1.7.2
	```
  
3. Verify that you have Appium installed with this command: 
	```
	appium -v
	```
   You should get "1.7.2" as the output


## Creating a new Java Appium Test Project Using Maven
1. Create a new Maven project using a Java IDE. **The example in this tutorial is for [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/download/)**.

2. Save the following XML assembly to *src/main/assembly/zip.xml.*
    - The following XML is an assembly definition that, when configured, instructs Maven to build a .zip file containing everything in the root of your build output directory and the dependency-jars directory:
        ```
        <assembly
        xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd">
      <id>zip</id>
      <formats>
        <format>zip</format>
      </formats>
      <includeBaseDirectory>false</includeBaseDirectory>
      <fileSets>
        <fileSet>
          <directory>${project.build.directory}</directory>
          <outputDirectory>./</outputDirectory>
          <includes>
            <include>*.jar</include>
          </includes>
        </fileSet>
        <fileSet>
          <directory>${project.build.directory}</directory>
          <outputDirectory>./</outputDirectory>
          <includes>
            <include>/dependency-jars/</include>
          </includes>
        </fileSet>
      </fileSets>
        </assembly>
        ```

3. Set up your POM file using the official AWS Device Farm documentation for [TestNG](http://docs.aws.amazon.com/devicefarm/latest/developerguide/test-types-android-appium-java-testng.html)
	- You will need the following dependencies in your POM file
		
        ```
	    <dependencies>
    	    <dependency>
        	    <groupId>org.testng</groupId>
            	<artifactId>testng</artifactId>
	            <version>6.11</version>
    	        <scope>test</scope>
        	</dependency>
	        <dependency>
    	        <groupId>io.appium</groupId>
        	    <artifactId>java-client</artifactId>
            	<version>4.1.2</version>
	        </dependency>
    	</dependencies>
        ```
	- So after following [Amazon Device Farm](https://docs.aws.amazon.com/devicefarm/latest/developerguide/test-types-android-appium-java-testng.html) tutorial your final *pom.xml* file will look like:

        ```
        <artifactId>AWS_Device_Farm_Android_Sample</artifactId>
        <version>1.0-SNAPSHOT</version>
        <packaging>jar</packaging>
        <url>http://maven.apache.org</url>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-jar-plugin</artifactId>
                    <version>2.6</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>test-jar</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-dependency-plugin</artifactId>
                    <version>2.10</version>
                    <executions>
                        <execution>
                            <id>copy-dependencies</id>
                            <phase>package</phase>
                            <goals>
                                <goal>copy-dependencies</goal>
                            </goals>
                            <configuration>
                                <outputDirectory>${project.build.directory}/dependency-jars/</outputDirectory>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
                <plugin>
                    <artifactId>maven-assembly-plugin</artifactId>
                    <version>2.5.4</version>
                    <executions>
                        <execution>
                            <phase>package</phase>
                            <goals>
                                <goal>single</goal>
                            </goals>
                            <configuration>
                                <finalName>zip-with-dependencies</finalName>
                                <appendAssemblyId>false</appendAssemblyId>
                                <descriptors>
                                    <descriptor>src/main/assembly/zip.xml</descriptor>
                                </descriptors>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>1.7</source>
                        <target>1.7</target>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        ```

### You can execute these test cases on 1) Locally or 2) AWS Device Farm        
#### 1) Local Execution
 - First, move to the **src/test/resources/configuration.properties** and change the properties.
- Make sure to set 
	```
    execution.type=local
	```
 - Mow move to **/src/test/java/tests/TestCases.java** and execute the test cases

#### 2) AWS Device Farm Execution
##### Step 1: Verify the Project Set-up
First Read the [Device Farm documentation](http://docs.aws.amazon.com/devicefarm/latest/developerguide/test-types-android-calabash.html). Ensure that all the steps are completed and that your project and POM file are set up correctly. 

##### Step 2: Go into your Maven Appium Directory
Go into your Appium Maven project directory in the terminal or command prompt.

##### Step 3: Package the Test Content
Run the following Maven command to package the test content.
```
mvn clean package -DskipTests=true
```
##### Step 4: Locate the zip-with-dependencies.zip file
Once the Maven command above is finished it will produce a "zip-with-dependencies.zip" file in your target folder. You will upload this file when [creating a run](http://docs.aws.amazon.com/devicefarm/latest/developerguide/how-to-create-test-run.html) on AWS Device Farm.

<img src="https://github.com/awslabs/aws-device-farm-appium-tests-for-sample-app/blob/master/readme_images/appium-tests.gif" width="400">


