![[GitHub Release Date](https://img.shields.io/github/release-date/dropbox/dropbox-sdk-java)](https://img.shields.io/github/release-date/roberthcorgosinho/avaliacao-tecnica-db?style=flat-square)
![GitHub](https://img.shields.io/badge/SPRING%20BOOT-3.0.0-green?style=flat-square)
# avaliacao-tecnica-db
Prova de avaliação técnica como parte de entrevista para uma empresa de consultoria e software house.

O Projeto foi feito com Sprint Boot 3 e Java 17.

# Enunciado do desafio

## Votação

### Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação.
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

- Cadastrar uma nova pauta
- Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por
  um tempo determinado na chamada de abertura ou 1 minuto por default)
- Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado
  é identificado por um id único e pode votar apenas uma vez por pauta)
- Contabilizar os votos e dar o resultado da votação na pauta

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A solução deve ser construída em java, usando Spring-boot, mas os frameworks e bibliotecas são de livre escolha (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

O foco dessa avaliação é a comunicação entre o backend e o aplicativo mobile. Essa comunicação é feita através de mensagens no formato JSON, onde essas mensagens serão interpretadas pelo cliente para montar as telas onde o usuário vai interagir com o sistema. A aplicação cliente não faz parte da avaliação, apenas os componentes do servidor. O formato padrão dessas mensagens será detalhado no anexo 1.

### Como proceder

Por favor, realize o FORK desse repositório e implemente sua solução no FORK em seu repositório GItHub, ao final, notifique da conclusão para que possamos analisar o código implementado.

Lembre de deixar todas as orientações necessárias para executar o seu código.

#### Tarefas bônus

- Tarefa Bônus 1 - Integração com sistemas externos
    - Criar uma Facade/Client Fake que retorna aleátoriamente se um CPF recebido é válido ou não.
    - Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos
    - Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação. Essa operação retorna resultados aleatórios, portanto um mesmo CPF pode funcionar em um teste e não funcionar no outro.

Exemplos de retorno do serviço: 

```
// CPF Ok para votar
{
    "status": "ABLE_TO_VOTE
}
// CPF Nao Ok para votar - retornar 404 no client tb
{
    "status": "UNABLE_TO_VOTE
}
```

#### Tarefa Bônus 2 - Performance

- Imagine que sua aplicação possa ser usada em cenários que existam centenas de
  milhares de votos. Ela deve se comportar de maneira performática nesses
  cenários
- Testes de performance são uma boa maneira de garantir e observar como sua
  aplicação se comporta

#### Tarefa Bônus 3 - Versionamento da API

○ Como você versionaria a API da sua aplicação? Que estratégia usar?

- Resposta: Versionamento com GIT ou outro controlador de versão. Documentação do código-fonte 
conforme as especificações do javaDoc e o uso de alguma ferramenta como o Swagger 
para disponibilizar a documentação dos endpoints da api.

### O que será analisado

- Simplicidade no design da solução (evitar over engineering)
- Organização do código
- Arquitetura do projeto
- Boas práticas de programação (manutenibilidade, legibilidade etc)
- Possíveis bugs
- Tratamento de erros e exceções
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução
- Uso de testes automatizados e ferramentas de qualidade
- Limpeza do código
- Documentação do código e da API
- Logs da aplicação
- Mensagens e organização dos commits

### Dicas

- Teste bem sua solução, evite bugs
- Deixe o domínio das URLs de callback passiveis de alteração via configuração, para facilitar
  o teste tanto no emulador, quanto em dispositivos fisicos.
  Observações importantes
- Não inicie o teste sem sanar todas as dúvidas
- Iremos executar a aplicação para testá-la, cuide com qualquer dependência externa e
  deixe claro caso haja instruções especiais para execução do mesmo
  Classificação da informação: Uso Interno

### Anexo 1

#### Introdução

A seguir serão detalhados os tipos de tela que o cliente mobile suporta, assim como os tipos de campos disponíveis para a interação do usuário.

#### Tipo de tela – FORMULARIO

A tela do tipo FORMULARIO exibe uma coleção de campos (itens) e possui um ou dois botões de ação na parte inferior.

O aplicativo envia uma requisição POST para a url informada e com o body definido pelo objeto dentro de cada botão quando o mesmo é acionado. Nos casos onde temos campos de entrada
de dados na tela, os valores informados pelo usuário são adicionados ao corpo da requisição. Abaixo o exemplo da requisição que o aplicativo vai fazer quando o botão “Ação 1” for acionado:

```
POST http://seudominio.com/ACAO1
{
    “campo1”: “valor1”,
    “campo2”: 123,
    “idCampoTexto”: “Texto”,
    “idCampoNumerico": 999
    “idCampoData”: “01/01/2000”
}
```

Obs: o formato da url acima é meramente ilustrativo e não define qualquer padrão de formato.

#### Tipo de tela – SELECAO

A tela do tipo SELECAO exibe uma lista de opções para que o usuário.

O aplicativo envia uma requisição POST para a url informada e com o body definido pelo objeto dentro de cada item da lista de seleção, quando o mesmo é acionado, semelhando ao funcionamento dos botões da tela FORMULARIO.

---

# Considerações do candidato

Gostaria de ter feito um trabalho melhor, no entanto, fazendo este teste e trabalhando simultâneamente não tive tempo hábil para fazer algo mais adequado, mais performático e com melhores práticas tanto de codificação quanto de documentação.

Com relação ao Anexo 1, foi-me informado que não haveria necessidade de implementação ou de se realizar qualquer outra ação a respeito.

No enunciado do problema, na descrição do objetivo, cita que a implementação deve ser uma **API REST**:

```
### Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação.
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST...
```

Logo, foi seguido o padrão **REST** e não o **RESTFUL** como "cobrado" na seguinte mensagem que me foi enviada:

```
...
- Padrão RESTFUL não foi seguido, no mesmo Controller temos ações de Pauta, Sessão e Votação junto, isso dificulta a manutenabilidade futura;
...
```

No entanto, ajustes foram feitos para se tentar adequar as normas e princípios para se considerar API dentro do padrão RESTFUL (arquitetura cliente-servidor, comunicação stateless, designe que possibilita o uso de cache, interface uniforme, sistema de camadas).

## Tarefas bônus:

### Tarefa Bônus 1 - Integração com sistemas externos

- Implementado e disponibilizado em https://github.com/roberthcorgosinho/userInfo

### Tarefa Bônus 2 - Performance

- Foi disponibilizado um arquivo junto ao projeto (ver abaixo) com um plano de teste de performance

### Tarefa Bônus 3 - Versionamento da API

- Este código está versionado utilizando-se o GITHUB
- Foi criado um documento OPENAPI (Swagger) para documentação da API Criada

Foi feita a seguinte pergunta: ```Como você versionaria a API da sua aplicação? Que estratégia usar?```

Minha resposta: versionamento através de algum sistema de versionamento (GIT, SVN, etc.), uso da OPENAPI (Swagger) para documentação da API, uso do JAVADOC para documentação direta no código-fonte da aplicação, implementação de testes unitários e testes de integração automatizados para garantir a corretude do código, uso de ferramentas como SONAR para garantir uma maior qualidade do código-fonte e uso de ferramentas de integração contínua como o JENKINS. Já para projetos em estágio inicial, minha recomendação é a utilização também do Flyway para versionamento das modificações a serem realizadas no banco de dados da aplicação

#### SWAGGER DA MINHA APLICAÇÃO

- Para acessar a documentação OPENAPI (Swagger) da minha aplicação, entre no seguinte endereço:

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs

http://localhost:8080/api-docs.yaml (arquivo yaml)

## Arquivos disponibilizados junto ao projeto

- No arquivo ```/utilitarios-testes/DBServer.postman_collection.json``` existem as chamadas dos endpoints para testes
- No arquivo ```/utilitarios-testes/TestePerformanceDBRoberthJMETER.jmx``` existe o plano de testes de performance criado utilizando-se o aplicativo JMETER da Apache (disponilizado junto ao código)
- Na pasta ```/utilitarios-testes/apache-jmeter-5.6.2``` está disponibilizado o aplicativo JMETER para a execução do plano de teste de performance.
