# coffee order

Идея проекта - избавиться от очередей за кофе. Сделать заказ через rest client, подойти за готовой чашкой кофе спустя 5 минут. Проект состоит из клиентской и серверной части.
<br><br> Клиентская часть позволяет оставить заказ, а также включает в себя часть администратора (бариста), в которой видно поступившие заказы.<br>Серверная часть обрабатывает поступающие запросы, от клиенсткой части, взаимодействуя с базой данных.

### Запуск docker-compose

Для быстрого запуска добавлен файл docker-compose, который включает в себя сборку и запуск серверной и клиентской
частей, а также разворачивает postgres базу данных с заготовленным dump примером.

1. Соберите проект, для этого из корня проекта введите в терминале команду:

```
gradle build 
```

2. Поднимите контейнеры с базой данных, сервером и клиентом. Для этого можно воспользоваться docker compose:

```
docker compose up -d
```

### Deploy в кластере k8s

1. Собрать все docker images, они не опубликованы на dockerhub, запускаются из локально собранных образов.

```
docker compose build
```

2. Применим конфигурацию к кластеру k8s

```
kubectl apply -f postgres-deploy.yml
kubectl apply -f postgres-service.yml
kubectl apply -f client-deploy.yml
kubectl apply -f client-service.yml
kubectl apply -f server-deploy.yml
kubectl apply -f server-service.yml
```

Клиентская часть с меню доступна по адресу: http://localhost:8080/ (если deploy в кластере, порт 3000)

![Меню](client/src/main/resources/readmeImages/menuorder.png)

Клиентская часть с окном для бариста доступна по адресу: http://localhost:8080/barista <br><br>
Логин и пароль задаются в БД сервера (таблица barista_user) <br>

пароль хранится в формате SHA-256.

в данном примере можете использовать следующую пару логина и пароля:
<br><br> user: test <br> password: test <br>

![Панель бариста](client/src/main/resources/readmeImages/barista.png)

Схема базы данных:

![Схема базы](server/src/main/resources/readmeImage/databasediagram.png)
