--- Setting up Headless Eclipse for testing---

Adapted from

	http://www.sable.mcgill.ca/ppa/tut_4.html

Write the "main" by implementing the IApplication interface

	Add the extension org.eclipse.core.runtime.applications (in the Extensions view)
	
	ID is the extension id (needed to invoke the application)
	
	Add "run" (right click application --> New --> run)
	The class specified is the "main" (click "class" to create a new class or verify)


To test it in Eclipse:
	
	Run --> Run Configurations 

	Program to Run --> Run an application --> <plugin-id>.<extension-id>


To run it command-line:
	
	Pre-install eclipse on $ECLIPSE_DIR: /diskless/local/annie/workspaces/20140228-summarizer/headless-eclipse/
	
	Export --> Deployable plugins and fragments

	Directory: $ECLIPSE_DIR/eclipse
	
	cd $ECLIPSE_DIR/eclipse

	Copy res directory to here
	
	./eclipse -nosplash -application EclipseServer.id # <plugin-id>.<extension-id> 


References:

	http://blogs.operationaldynamics.com/andrew/software/java-gnome/eclipse-code-format-from-command-line


--- Packaging it ---

Set variables for the directories we need

	export WORKSPACE=/diskless/local/annie/workspaces/20150223-headlessEclipseServer/
	
	export ZIP_DIR=/diskless/local/annie/workspaces/20140228-summarizer/headless-eclipse/zipped

Generating the binary files

	Export --> Deployable plugins and fragments

	Archive it ($ZIP_DIR/formatter-eclipse-server.zip/plugins/*.jar)

Add res directory to the zip file

	cd $ZIP_DIR
	
	cp -r $WORKSPACE/FormatterEclipseServer/res $ZIP_DIR
	
	zip -r $ZIP_DIR/FormatterEclipseServer.zip ./
	
	
	
--- Running it on aspect ---

Pre-install eclipse 

	export FORMATTER_SERVER_DIR=/local/annie/formatter/

Copy the zip with the plugins to the aspect server

	scp $ZIP_DIR/FormatterEclipseServer.zip aying1@aspect.cs.mcgill.ca:/local/annie/formatter/
	
Unzip the plugins in the right place on aspect server

	cd $FORMATTER_SERVER_DIR
	
	mv FormatterEclipseServer.zip eclipse
	
	unzip FormatterEclipseServer.zip
	
Run the server

	./eclipse -nosplash -application FormatterEclipseServer.id # <plugin-id>.<extension-id> 
	
	

--- Generating formatting file (check-style.xml) ---
 
Go to runtime Eclipse -> Window -> Preference -> Java -> CodeStyle 
Import /change profile

References:
https://github.com/sevntu-checkstyle/sevntu.checkstyle/wiki/Java-code-Formater-and-Checkstyle-configuration-for-development
http://stackoverflow.com/questions/984778/how-to-generate-an-eclipse-formatter-configuration-from-a-checkstyle-configurati

Programmatically:
http://denizstij.blogspot.ca/2009/10/amending-code-style-templates.html

