# Certification NLW

```bash
mvn spring-boot:run
```

```application.properties
server.port=8085
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

## Modelos

### Questions

- id
- description
- technology

### Alternatives

- id
- description
- question_id
- is_correct

### Students

- email
- List of Certification

### Certifications

- student_id
- id
- technology
- List of AnswersCertification

### AnswersCertification

- certification_id
- student_id
- question_id
- answer_id
- is_correct
