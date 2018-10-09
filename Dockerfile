FROM library/postgres

ENV POSTGRES_USER timetracker
ENV POSTGRES_PASSWORD timetracker
ENV POSTGRES_DB timetracker

COPY init.sql /docker-entrypoint-initdb.d/
