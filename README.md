# raspberry-api
API RESTful para possibilitar a leitura da lista de indicados e vencedores da  categoria Pior Filme do Golden Raspberry Awards. 

## Funcionalidades

- Exposição de endpoint REST `/producers/intervals` para retornar:
- Produtores com **menor intervalo** entre vitórias.
- Produtores com **maior intervalo** entre vitórias.

---

###  Pré-requisitos

- Java 17+
- Maven 3.8+


##  Como executar o projeto
1. Clone o repositório:

   ```bash
   git clone https://github.com/korrones/raspberry-api.git
   cd golden-raspberry-api
2. Rodar a aplicação
3. Acessar em: http://localhost:8080/producers/intervals o resultado da api.
   




