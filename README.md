(using GitHub as temp. backup)

### Installation:
* Go to the "releases" tab in this GitHub page and download the latest version of the .jar
* Stop nuxeo server
* Drop the ccglobal-utils-{version}-SNAPSHOT.jar file in the "bundles" directory of your nuxeo server
	** And remove any previous version
* Start nuxeo server

### Build the plug-in
Assuming maven (minimum 3.2.3) versio is installed on your computer:
```
cd /path/to/a/folder
# Clone this repository
git clone git://github.com/ThibArg/ccglobal-nuxeo.git
# Build the plug-in
cd ccglobal-nuxeo/ccglobal-utils
mvn clean package
# The .jar is in ccglobal-nuxeo/ccglobal-utils/target
```