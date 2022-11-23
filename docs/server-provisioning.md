# Server Provisioning

### Specs

**Operating System**: Ubuntu 20.04  
**CPU**: 2vCPU  
**RAM**: 8GB  
**PostgreSQL Version**: 14

## Adding Hostname

Reference: https://www.globo.tech/learning-center/sudo-unable-to-resolve-host-explained/

Steps at the time of writing:

1. Modify hosts file:

   ```console
   vi /etc/hosts
   ```

2. Add hostname with a loopback address:
   ```
   127.0.0.1    minecount
   ```

## PostgreSQL + TimescaleDB Installation

Reference: https://docs.timescale.com/install/latest/self-hosted/installation-debian/

Steps at the time of writing:

1. Add PostgreSQL third party repository:

   ```console
   apt install gnupg postgresql-common apt-transport-https lsb-release wget
   ```

2. Run PostgreSQL repository startup script:

   ```console
   /usr/share/postgresql-common/pgdg/apt.postgresql.org.sh
   ```

3. Add TimescaleDB third party repository:

   ```console
   echo "deb https://packagecloud.io/timescale/timescaledb/ubuntu/ $(lsb_release -c -s) main" | sudo tee /etc/apt/sources.list.d/timescaledb.list
   ```

4. Install Timescale GPG key:

   ```console
   wget --quiet -O - https://packagecloud.io/timescale/timescaledb/gpgkey | sudo apt-key add -
   ```

5. Update local repository list:

   ```console
   apt update
   ```

6. Install TimescaleDB:
   ```console
   apt install timescaledb-2-postgresql-14
   ```
7. Install postgresql-client package:

   ```console
   sudo apt-get install postgresql-client
   ```

8. Tune TimescaleDB:

   ```console
   sudo timescaledb-tune -pg-config=/etc/postgresql/14/main/postgresql.conf -pg-version=14
   ```

9. Restart service after tuning:

   ```console
   systemctl restart postgresql
   ```

10. Connect to PostgreSQL instance as `postgres` superuser:

    ```console
    su postgres -c psql
    ```

11. Change the password for postgres:

    ```console
    sudo -u postgres psql
    \password postgres
    ```

12. Create database:

    ```sql
    CREATE database minecount;
    ```

13. Connect to database:

    ```console
    \c minecount
    ```

14. Add TimescaleDB extension:

    ```sql
    CREATE EXTENSION IF NOT EXISTS timescaledb;
    ```

15. Validate installation:

    ```console
    \dx
    ```

16. Quit psql:

    ```console
    \q
    ```

17. Modify postgresql.conf and add this to the file:

    ```console
    vi /etc/postgresql/14/main/postgresql.conf
    listen_addresses = '*'
    ```

18. Modify pg_hba.conf and add these lines to the end of the file:

    ```console
    vi /etc/postgresql/14/main/pg_hba.conf
    host    all             all              0.0.0.0/0                       md5
    host    all             all              ::/0                            md5
    ```

19. Restart postgresql:
    ```console
    systemctl restart postgresql
    ```

## Docker Installation

Reference: https://docs.docker.com/engine/install/ubuntu/

Steps at the time of writing:

1. Uninstall older versions of Docker:

   ```console
   sudo apt-get remove docker docker-engine docker.io containerd runc
   ```

2. Update apt package index

   ```console
   sudo apt-get update
   ```

3. Install packages to allow apt to use a repository over HTTPS

   ```console
   sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
   ```

4. Add Docker's official GPG key

   ```console
   sudo mkdir -p /etc/apt/keyrings
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
   ```

5. Use the followwing command to set up the repository:

   ```console
   echo \
   "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   ```

6. Update the apt package index

   ```console
   sudo apt-get update
   ```

7. Install Docker Engine, containerd, and Docker Compose

   ```console
   sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
   ```

## Environment Variables / Secrets

1. Create a file `/etc/minecount/minecount-be.env` containing the back-end environment variables.
2. Create a file `/etc/minecount/minecount-fe.env` containing the front-end environment variables.

## Release Webhook

Reference: https://github.com/adnanh/webhook

Steps at the time of writing:

1. Install webhook

   ```console
   sudo apt-get install webhook
   ```

2. Create `/var/scripts/redeploy.sh` (use `redeploy.sh` as reference).

3. Modify permissions for redeploy by running

   ```console
   chmod +x /var/scripts/redeploy.sh
   ```

4. Create `/var/webhook/redeploy-webhook.json` using a randomly generated secret key (use `redeploy-webhook.json` as reference).

5. Start webhook in background

   ```console
   webhook -hooks /var/webhook/redeploy-webhook.json -verbose &> /var/webhook/log.out
   ```
