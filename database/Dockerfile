FROM postgres:15
ENV POSTGRES_DB coffee_db
ENV POSTGRES_PASSWORD postgres
COPY coffee_db-dump.sql /docker-entrypoint-initdb.d/coffee_db-dump.sql