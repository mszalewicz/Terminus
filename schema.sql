PRAGMA foreign_keys = ON;
PRAGMA journal_mode = WAL;
PRAGMA case_sensitive_like = ON;
PRAGMA synchronous = FULL;
PRAGMA temp_store = MEMORY;

CREATE TABLE IF NOT EXISTS self (
	name TEXT NOT NULL
) STRICT;

CREATE TABLE IF NOT EXISTS peers (
	id TEXT PRIMARY KEY,
	last_known_ip_socket TEXT NULL
) STRICT;

CREATE TABLE IF NOT EXISTS sessions (
	id TEXT PRIMARY KEY,
	peer_id TEXT NOT NULL REFERENCES peers(id),
	status TEXT NOT NULL
) STRICT;

CREATE TABLE IF NOT EXISTS sessions_files (
	session_id TEXT NOT NULL REFERENCES sessions(id),
	peer_id TEXT NOT NULL REFERENCES peers(id),
	filename TEXT NOT NULL,
	status TEXT NOT NULL
) STRICT;
