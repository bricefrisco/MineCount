# Database Schema

## Servers

```sql
CREATE TABLE servers(
	id SERIAL PRIMARY KEY,
	ip TEXT UNIQUE NOT NULL,
	name TEXT UNIQUE,
	favicon TEXT NOT NULL,
	motd TEXT NOT NULL,
	version_name TEXT NOT NULL,
	version_protocol INTEGER NOT NULL,
	player_count INTEGER NOT NULL,
	max_players INTEGER NOT NULL,
	approved BOOLEAN,
	notes TEXT,
	approver TEXT,
	website TEXT,
	discord TEXT,
	last_pinged TIMESTAMPTZ,
	successful_pings INTEGER NOT NULL DEFAULT 0,
	unsuccessful_pings INTEGER NOT NULL DEFAULT 0,
	consecutive_unsuccessful_pings INTEGER NOT NULL DEFAULT 0,
	created_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	last_modified_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
);
```

## Server IPs

```sql
CREATE TABLE server_ips(
	id SERIAL PRIMARY KEY,
	server_id INTEGER references servers(id) NOT NULL,
	ip TEXT NOT NULL,
	created_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	last_modified_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX server_ips_ip ON server_ips (ip);
```

## Server Pings

```sql
CREATE TABLE server_pings(
	time TIMESTAMPTZ NOT NULL,
	server_id INTEGER references servers(id) NOT NULL,
	player_count INTEGER NOT NULL
);

SELECT create_hypertable('server_pings', 'time');
SELECT set_chunk_time_interval('server_pings', INTERVAL '1 month');
SELECT add_retention_policy('server_pings', INTERVAL '5 days');
```

## Continuous Aggregates

### 10 minutes

Used when querying data for one day.

```sql
CREATE MATERIALIZED VIEW server_pings_10m
WITH (timescaledb.continuous) AS
SELECT server_id,
       TIME_BUCKET(interval '10 minutes', time) AS bucket,
       AVG(player_count),
       MIN(player_count),
       MAX(player_count)
FROM server_pings
GROUP BY server_id, bucket;

SELECT add_continuous_aggregate_policy('server_pings_10m',
    start_offset => INTERVAL '30 minutes',
    end_offset => INTERVAL '10 minutes',
    schedule_interval => INTERVAL '10 minutes'
);

SELECT add_retention_policy('server_pings_10m', INTERVAL '2 days');
```

### 1 hour

Used when querying data for one week.

```sql
CREATE MATERIALIZED VIEW server_pings_1h
WITH (timescaledb.continuous) AS
SELECT server_id,
       TIME_BUCKET(interval '1 hour', time) AS bucket,
       AVG(player_count),
       MIN(player_count),
       MAX(player_count)
FROM server_pings
GROUP BY server_id, bucket;

SELECT add_continuous_aggregate_policy('server_pings_1h',
    start_offset => INTERVAL '3 hours',
    end_offset => INTERVAL '1 hour',
    schedule_interval => INTERVAL '1 hour'
);

SELECT add_retention_policy('server_pings_1h', INTERVAL '2 weeks');
```

### 4 hours

Used when querying data for one month.

```sql
CREATE MATERIALIZED VIEW server_pings_4h
WITH (timescaledb.continuous) AS
SELECT server_id,
       TIME_BUCKET(interval '4 hours', time) AS bucket,
       AVG(player_count),
       MIN(player_count),
       MAX(player_count)
FROM server_pings
GROUP BY server_id, bucket;

SELECT add_continuous_aggregate_policy('server_pings_4h',
    start_offset => INTERVAL '12 hours',
    end_offset => INTERVAL '4 hours',
    schedule_interval => INTERVAL '4 hours'
);

SELECT add_retention_policy('server_pings_4h', INTERVAL '2 months');
```

### 1 day

Used when querying data for anything over 1 year.
There is no retention policy for this interval.

```sql
CREATE MATERIALIZED VIEW server_pings_1d
WITH (timescaledb.continuous) AS
SELECT server_id,
       TIME_BUCKET(interval '1 day', time) AS bucket,
       AVG(player_count),
       MIN(player_count),
       MAX(player_count)
FROM server_pings
GROUP BY server_id, bucket;

SELECT add_continuous_aggregate_policy('server_pings_1d',
    start_offset => INTERVAL '3 days',
    end_offset => INTERVAL '1 day',
    schedule_interval => INTERVAL '1 day'
);
```

### Server total counts
Dummy server used to track total player count across all tracked servers

```sql
INSERT INTO SERVERS (name, favicon, motd, version_name, version_protocol, approved, ip, successful_pings, unsuccessful_pings, consecutive_unsuccessful_pings, max_players, player_count)
VALUES              ('server-total-counts', 'n/a', 'n/a', 'n/a', 0, false, 'n/a', 0, 0, 0, 0, 0);
```