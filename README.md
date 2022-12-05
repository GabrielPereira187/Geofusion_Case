## Atenção
Nesta prova será necessário implementar os métodos das classes conforme descrições dos Javadocs correspondentes.

Deverá ter interfaces REST para as seguintes funcionalidades:

    * Cadastro de produtos.
    * Alteração de preços.
    * Criação de carrinhos.

## Regras:

* Você poderá criar novos atributos, classes, métodos e reorganizar os pacotes.
* É proibido mudar a assinatura dos métodos e construtores já existentes, exceto o construtor da classe ShoppingCart.
* Você tem liberdade de adicionar frameworks e bibliotecas.
* Não será necessário implementar nenhum tipo de interface gráfica.
* A prova deve ser resolvida utilizando obrigatoriamente Java (Versão 8 ou superior).
* É opcional o uso de bancos de dados e ferramentas de persistência. 
(Caso opte por utilizar, deverá encaminhar no readme as instruções de inicialização do projeto).
* É opcional a implementação de cobertura de testes.
* Sua prova precisa compilar via Maven, impreterivelmente.
* A entrega do teste deverá ser por meio de um repositório público ou anexada ao email.


A ferramenta utilizada para persistência dos dados foi o JPA(Java Persistence Api) e o banco de dados usado 
foi o H2, um banco de dados em memória.

Para acessá-lo, basta usar a url: http://localhost:8080/h2-console e clicar em connect na tela de login.

A aplicação está documentada utilizando o swagger, para acessá-la, basta usar a url: http://localhost:8080/swagger-ui/
e verificar as chamadas existentes.