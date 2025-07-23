FROM postgres:15
ENV POSTGRES_DB coffee_db
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD izpodvipodverta
COPY test_dump.sql /docker-entrypoint-initdb.d/test_dump.sql
