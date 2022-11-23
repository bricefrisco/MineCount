#!bin/bash
docker pull bricefrisco/minecount-be:latest
docker pull bricefrisco/minecount-fe:latest
docker system prune -f
docker run -d=true --expose=8080:8080 --memory=512m --cpus=0.5 --name=minecount-be bricefrisco/minecount-be:latest --env-file /etc/minecount/minecount-be.env
docker run -d=true --expose=3000:3000 --memory=512m --cpus=0.5 --name=minecount-fe bricefrisco/minecount-fe:latest --env-file /etc/minecount/minecount-fe.env