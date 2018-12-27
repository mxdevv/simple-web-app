cd ./servlet_files
cp -r ../src/* ./WEB-INF/classes/
mv ./WEB-INF/classes/servlet/* ./WEB-INF/classes/
rm -r ./WEB-INF/classes/servlet
jar cvf ../servlet.war ./
