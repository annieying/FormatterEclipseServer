Installing Eclipse

	Unzip Eclipse in $ECLIPSE_DIR
	
	
Installing the server

	cd $ECLIPSE_DIR$/eclipse
	
	Copy FormatterEclipseServer.zip to $ECLIPSE_DIR$/eclipse 
   
	Unzip FormatterEclipseServer.zip - which creates plugins/*.jar and res 
   	

Running the server as headless Eclipse

	cd $ECLISE_DIR/eclipse
   
	./eclipse -nosplash -application FormatterEclipseServer.id # <plugin-id>.<extension-id> 

	References:
		http://blogs.operationaldynamics.com/andrew/software/java-gnome/eclipse-code-format-from-command-line
		
		
Accessing the web site

	http://annieying.ca:8846/Formatter