#!bin/bash
docker pull bricefrisco/minecount-be:latest
docker pull bricefrisco/minecount-fe:latest
docker stop minecount-be
docker rm minecount-be
docker run -d --network=host --restart=unless-stopped -p 8080:8080 --memory=512m --cpus=0.5 --name=minecount-be --env-file /etc/minecount/minecount-be.env bricefrisco/minecount-be:latest
docker stop minecount-fe
docker rm minecount-fe
docker run -d --network=host --restart=unless-stopped -p 3000:3000 --memory=512m --cpus=0.5 --name=minecount-fe --env-file /etc/minecount/minecount-fe.env bricefrisco/minecount-fe:latest
docker system prune -f