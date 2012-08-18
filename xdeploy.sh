#!/bin/bash


echo "*** Starting remove the (remote) old project"
ssh root@betty.sytes.net "rm -rf /var/lib/tomcat6/webapps/$1"
ssh root@betty.sytes.net "mkdir /var/lib/tomcat6/webapps/$1"

echo "*** Done remove, starting copy local files"
rm -fr /tmp/bett
mkdir /tmp/bett
cp -r war/* /tmp/bett/
rm -r /tmp/bett/WEB-INF/lib
rm -r /tmp/bett/WEB-INF/deploy
rm -r /tmp/bett/betty_gwtp/sc

echo "*** Sending data .."
scp -r /tmp/bett/* root@betty.sytes.net:/var/lib/tomcat6/webapps/$1

echo "*** Copying libraries in the remote war directory"
ssh root@betty.sytes.net "cp -r /var/lib/tomcat6/webapps/all_libs /var/lib/tomcat6/webapps/$1/WEB-INF/lib"
ssh root@betty.sytes.net "cp -r /var/lib/tomcat6/webapps/skins /var/lib/tomcat6/webapps/$1/betty_gwtp/sc"


echo "*** Let's restart tomcat.."
ssh root@betty.sytes.net '/etc/init.d/tomcat6 restart'
