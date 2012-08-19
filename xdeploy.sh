#!/bin/bash



echo "*** Copy local files in /tmp"
rm -fr /tmp/bett
mkdir /tmp/bett
cp -r war/* /tmp/bett/

echo "*** Ok, you can go back to work ;) "
rm -r /tmp/bett/WEB-INF/lib
rm -r /tmp/bett/WEB-INF/deploy
rm -r /tmp/bett/betty_gwtp/sc

echo "*** Remove the (remote) old project"
ssh root@betty.sytes.net "rm -rf /var/lib/tomcat6/webapps/betty/*"

echo "*** Sending data .."
scp -r /tmp/bett/* root@betty.sytes.net:/var/lib/tomcat6/webapps/betty/

echo "*** Copying libraries in the remote war directory"
ssh root@betty.sytes.net "cp -r /var/lib/tomcat6/all_libs /var/lib/tomcat6/webapps/betty/WEB-INF/lib"
ssh root@betty.sytes.net "cp -r /var/lib/tomcat6/skins /var/lib/tomcat6/webapps/betty/betty_gwtp/sc"


echo "*** Let's restart tomcat.."
ssh root@betty.sytes.net '/etc/init.d/tomcat6 restart'
