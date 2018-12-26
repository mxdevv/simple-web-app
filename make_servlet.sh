FILE_NAME="Servlet"
FILE_PATH="./src/servlet/"
CLASS_PATH="./src/:./src/servlet/"
SOURCE_PATH=$CLASS_PATH
LIBS=":/usr/share/jdbc-mysql/lib/jdbc-mysql.jar:/usr/share/glassfish-servlet-api-3.1.1/lib/glassfish-servlet-api.jar:/opt/glassfish4/glassfish/lib/javaee.jar"

set -x

javac -classpath $CLASS_PATH$LIBS -sourcepath $SOURCE_PATH $FILE_PATH$FILE_NAME.java
