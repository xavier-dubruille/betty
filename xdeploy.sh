#!/bin/bash


echo "*** Starting remove the (remote) old project"
ssh root@betty.sytes.net 'rm -rf /var/lib/tomcat6/webapps/betty/*'

echo "*** Done remove, starting moving local files"
mv war/WEB-INF/lib /tmp
mv war/WEB-INF/deploy /tmp

echo "*** Sending data .."
scp -r war/* root@betty.sytes.net:/var/lib/tomcat6/webapps/betty

echo "*** Data sent, putting local files in place."
mv /tmp/lib war/WEB-INF/
mv /tmp/deploy war/WEB-INF/

echo "*** Copying libraries in the remote war directory"
ssh root@betty.sytes.net 'cp -r /var/lib/tomcat6/webapps/complete_lib2 /var/lib/tomcat6/webapps/betty/WEB-INF/lib'


echo "*** Let's restart tomcat.."
ssh root@betty.sytes.net '/etc/init.d/tomcat6 restart'
