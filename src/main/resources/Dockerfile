# Usa la imagen oficial de Tomcat 9
FROM tomcat:9.0.90

# Elimina las aplicaciones de ejemplo de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia el archivo WAR al directorio webapps de Tomcat
COPY target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/demo.war

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
