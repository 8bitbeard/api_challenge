# Itau Challenge API

Esta API foi desenvolvida em um desafio ténico para uma de desenvolvedor Backend.
A documentação dessa API pode ser acessada localmente (Após subir o servidor local da aplicação) através da URL: http://localhost:8080/swagger-ui/index.html, mas também pode ser acessada (E completamente testada) através da URL de produção: http://15.228.243.48:8080/swagger-ui/index.html#/

# Tecnologias utilizadas
- Java
- Spring boot
- Spring Security
- PostgreSQL
- Kafka
- Docker
- Docker Compose
- Amazon EC2

# Instalando o seu ambiente de desenvolvimento local

## Pré-requisitos:
- Docker
- docker-compose
- Maven

É necessário possuir o `Maven` instalado, para poder gerar o `.jar` da aplicação que é utilizado para construir o container da aplicação Spring.

Após instalar o `Maven`, e clonar este repositório para sua máquina, seguir os seguintes passos:

```bash
# Entrar na pasta do repo clonado
$ cd api_challenge 

# Buildar a aplicação e gerar o .jar utilizando o Maven
$ mvn clean package -DskipTests

# Construir os containers dos serviços presentes do docker-compose
$ docker-compose up -d --build
```

Atenção: O projeto faz uso das portas `8080`, `5432`, `9092` e `2181`, então para que tudo suba corretamente, é necessário garantir que essas portas não estão sendo usadas por nenhuma outra aplicação!

Se tudo correr como o esperado, ao executar o comando `docker-compose ps`, você deve receber o seguinte retorno:
```bash
$ docker-compose ps    
       
NAME                       COMMAND                  SERVICE             STATUS              PORTS
itau-challenge_app         "java -jar itauchall…"   challenge_app       running             0.0.0.0:8080->8080/tcp, :::8080->8080/tcp
itau-challenge_db          "docker-entrypoint.s…"   compose_db          running             0.0.0.0:5432->5432/tcp, :::5432->5432/tcp
itau-challenge_kafka       "start-kafka.sh"         kafka               running             0.0.0.0:9092->9092/tcp, :::9092->9092/tcp
itau-challenge_zookeeper   "/bin/sh -c '/usr/sb…"   zookeeper           running             0.0.0.0:2181->2181/tcp, :::2181->2181/tcp
```

Após isso, a aplicação string estará rodando localmente (`localhost`) na porta `8080`, e o bando de dados estará na porta `5432`

# Collection da API:
O projeto inclui uma collection já pronta do Insomnia, caso queira utilizá-la, o arquivo está na pasta `/insomnia`

# Utilizando a API em produção:
Esse projeto foi deployado em uma instância do AWS EC2. Para interagir com a aplicação em produção, basta acessála na url: http://15.228.243.48:8080 (A collection inclusa no projeto já possui um environment de Produção)