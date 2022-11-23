#!bin/bash
docker pull bricefrisco/minecount-be:latest
docker pull bricefrisco/minecount-fe:latest
docker stop minecount-be
docker rm minecount-be
docker run -d -p 8080:8080 --memory=512m --cpus=0.5 --name=minecount-be bricefrisco/minecount-be:latest --env-file /etc/minecount/minecount-be.env
docker stop minecount-fe
docker rm minecount-fe
docker run -d -p 3000:3000 --memory=512m --cpus=0.5 --name=minecount-fe bricefrisco/minecount-fe:latest --env-file /etc/minecount/minecount-fe.env
docker system prune -f