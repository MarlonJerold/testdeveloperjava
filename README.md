O teste está rodando nesse link

https://testdeveloperjava-deploy.onrender.com/swagger-ui/index.html

##  Início

[Link para a Redação](https://github.com/MarlonJerold/testdeveloperjava/blob/master/redacao_teste_tecnico.md) <br><br>
[Link para as Respostas do teste](https://github.com/MarlonJerold/testdeveloperjava/blob/master/respostas_teste_tecnico.md)

Para a realização do teste técnico para a vaga, realizei uma API com Java utilizando Springboot, juntamente com JPA e Hibernate, utilizando PostgreSQL, assim como criações de testes unitários utilizando JUnit e Mockito para a simulação dos testes do repository. Também, estou utilizando o jacoco para a metrificação de cobertura.

```
 mvn sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml -Dsonar.login={token}
```

Esse comando faz com que a cobertura seja atualizada de acordo com as atualizações em código, e em seguida, você pode utilizar o sonarqube para poder visualiza-lo 

Utilizando o container do docker, você pode utilizar esse comando para visualizar o sonarqube e visualizar as métricas desejadas, seja para clean code, ou atualizações recomendadas pela ferramenta visando um código limpo.

```
mvn sonar:sonar   -Dsonar.host.url=http://localhost:9000   -Dsonar.login={token}
```


##  Cobertura 80%

![image](https://github.com/MarlonJerold/testdeveloperjava/assets/63025001/b183a0b0-8476-45f7-8f6f-fc5f19d42a2c)

Caso você deseja utilizar o sonarqube, será necessário ter docker instalado para a utilização do docker-compose, o arquivo está contido no projeto.

## Documentação Swagger

Foi realizado também, criação de documentação via Swagger, você poderá testar a API utilizando o SWAGGER.

![image](https://github.com/MarlonJerold/testdeveloperjava/assets/63025001/b4f0340d-49d3-431b-bbef-49c526c96b18)

## Diagrama de Classes

Estou realizando o relacionamento um para muitos, o que significa uma Person poderá ter mais de um Address, porém, um Address só poderá ter uma Person associada, a tabela Address no banco, contém o person_Id como chave.

![image](https://github.com/MarlonJerold/testdeveloperjava/assets/63025001/d043f9ba-dc3f-4a30-bad8-8bf2a4d8a4a3)

