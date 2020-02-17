rm -rf backend
git clone https://github.com/magm3333/backend
cd backend
dt1=`date +%d_%m_%Y_%H%M%S`
echo "app.version=$dt1" >  src/main/resources/version.properties

mvn clean package
/home/user/apache-tomcat-8.5.38/bin/shutdown.sh
sudo rm -rf /home/user/apache-tomcat-8.5.38/webapps/ROOT
cp target/backend-0.0.1-SNAPSHOT.war /home/user/apache-tomcat-8.5.38/webapps/ROOT.war
sudo /home/user/apache-tomcat-8.5.38/bin/startup.sh 
