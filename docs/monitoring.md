# Monitoring

Monitoring is done via [Grafana and Loki](https://grafana.com/oss/loki/)

## Installation

Reference: https://grafana.com/docs/loki/latest/clients/docker-driver/

At the time of writing:

1. Install the grafana/loki docker plugin

   ```console
   docker plugin install grafana/loki-docker-driver:latest --alias loki --grant-all-permissions
   ```

2. Check that it was successfully installed

   ```console
   docker plugin ls
   ```

## Configuration

Reference: https://grafana.com/docs/loki/latest/clients/docker-driver/configuration/

At the time of writing:

1. In the Grafana console, navigate to `Integrations and Connections` and select `Hosted logs`.
2. Choose an API key name, and copy the `client.url` value
3. Start this docker container which forwards logs, replacing the deprecated `/api/prom/push` endpoint with the newer `/loki/api/v1/push`.

   ```console
   docker run -d --name=grafana-loki \
   --log-driver=loki \
   --log-opt loki-url="https://<user_id>:<password>@logs-us-west1.grafana.net/loki/api/v1/push" \
   --log-opt loki-retries=5 \
   --log-opt loki-batch-size=400 \
   grafana/grafana
   ```
4. Change the Loki logging driver to be the default for all containers by creating or modifying `/etc/docker/daemon.json` and set the value of `log-driver` to `loki`:

   ```json
   {
      "debug": true,
      "log-driver": "loki"
   }
   ```

5. Restart Docker daemon for the changes to take effect. All *newly created* containers from that host will then send logs to Loki via the driver.