NAME=servletTest
CLASSPATH=.:../../src/
SOURCEPATH=$(CLASSPATH)
LIBS=:/usr/share/jdbc-mysql/lib/jdbc-mysql.jar:/usr/share/glassfish-servlet-api-3.1.1/lib/glassfish-servlet-api.jar

all:
	javac -classpath $(CLASSPATH)$(LIBS) \
		-sourcepath $(SOURCEPATH) $(NAME).java
clean:
	rm $(NAME).class
