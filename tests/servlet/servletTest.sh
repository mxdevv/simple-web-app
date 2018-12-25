CLASSPATH=".:../../src/:/usr/share/jdbc-mysql/lib/jdbc-mysql.jar:/usr/share/glassfish-servlet-api-3.1.1/lib/glassfish-servlet-api.jar"
NAME="servletTest"

echo -e "\e[32mrun $NAME\e[0m"
java -cp $CLASSPATH $NAME
echo ""
