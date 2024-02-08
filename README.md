# Certification NLW

```bash
mvn spring-boot:run
```

```application.properties
server.port=8085
spring.datasource.url=jdbc:postgresql://localhost:5434/pg_nlw
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

```bash
docker-compose up -d
docker ps
```

## API REST

Metodos Comuns

- GET - Buscar uma informacao
- PUT - Alterar uma informacao
- POST - Inserir uma informacao
- DETELE - Remover uma informacao
- PATCH - Alterar PONTUAL de uma informacao

## Tipos de parametros

- Body - { }
- Query Params - http://localhost:8085/users?nome=Sergio&idade=35 OPICIONAIS
- Parametros de rotas - http://localhost:8085/users/{nome}/{idade} OBRIGATORIO

## ORM - Object Relational Mapping

## REPOSITORY - Interagir com o banco de dados
