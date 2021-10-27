 #!/bin/bash
 set -e
 
 #update package
 echo "Step1-1. update apt"
apt update
echo "\n\n\nStep1-2. upgrade apt"
apt upgrade
echo "\n\n\nStep1-3. install curl"
apt install curl
echo "\n\n\nStep1-4. install snapd"
apt install snapd

#install docker
echo "\n\n\n\n\n\nStep2. install docker"
apt install docker.io
systemctl enable --now docker
usermod -aG docker eden
echo "\n\n[Version Info]"
docker --version

#install docker compose
echo "\n\n\n\n\n\nStep3. install docker-compose"
curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
echo "\n\n[Version Info]"
docker-compose -v

#install java 11
echo "\n\n\n\n\n\nStep4. install Java 11"
apt install openjdk-11-jre-headless
echo "\n\n[Version Info]"
java -version

#run dockerbuild
echo "\n\n\n\n\n\nStep5. run dockerbuild.sh"

if [ "$1" == "local" ]; then
  docker-compose -f docker-compose-local.yaml down
  docker-compose -f docker-compose-local.yaml up -d
  exit 1
elif [ "$1" == "dev" ]; then
  docker-compose -f docker-compose-dev.yaml down
  docker-compose -f docker-compose-dev.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-dev.yaml up -d
elif [ "$1" == "prod" ]; then
  docker-compose -f docker-compose-prod.yaml down
  docker-compose -f docker-compose-prod.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-prod.yaml up -d
else
  docker-compose -f docker-compose-dev.yaml down
  docker-compose -f docker-compose-dev.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-dev.yaml up -d
fi
echo "wait for running..."
sleep 40

curl -H "Content-Type: application/json" -X GET http://localhost:8080/v1/api/health

echo "\n"

exit 0
