# df-transferencias
Case - API Transfêrencia

<h1>API Transfêrencias</h1>
<p>Swagger para visualização e testes da API: http://localhost:[port]/swagger-ui.html</p>

<p>Na configuração padrão da aplicação, caso a porta não seja alterada no application.properties: <a href="http://localhost:9876/swagger-ui.html" rel="nofollow">http://localhost:8888/swagger-ui.html</a></p>

<h3>Current version of API is v1</h3>


<h2>Descrição Básica da API</h2>
<h2>Clientes</h2>
<p>Endpoints com operações ref. Clientes</p>
<ul>
  <li>
  	<h4>GET</h4>
  	<pre><code>/api/v1/customers - Busca todos os Clientes cadastrados</code></pre>
  	<p><strong>Exemplo de Response Body (200)</strong></p>
[
   <br>&emsp;{
        <br>&emsp;"id": 1,
        <br>&emsp;"nome": "Renan Meireles  ",
        <br>&emsp;"numeroConta": "010123400",
        <br>&emsp;"saldoConta": 1100.75,
        <br>&emsp;"version": 0
    <br>&emsp;},
    <br>&emsp;{
        <br>&emsp;"id": 2,
        <br>&emsp;"nome": "Renan Meireles 2",
        <br>&emsp;"numeroConta": "020123499",
        <br>&emsp;"saldoConta": 200.00,
        <br>&emsp;"version": 0
    <br>&emsp;}
<br>]

  	<h4>POST</h4>
    <pre><code>/api/v1/customers - Cadastra um novo Cliente no sistema</code></pre>
    <p><strong>Exemplo de Request Body</strong></p>

{
  <br>"nome": "Renan Meireles",</n>
  <br>"numeroConta": "010123400",</n>
  <br>"saldoConta": 1100.75</n>
<br>}

   <p><strong>Exemplo de Response Body (201)</strong></p>
{
	<br>"id": 1,
    <br>"nome": "Renan Meireles  ",
    <br>"numeroConta": "010123400",
    <br>"saldoConta": 1100.75,
    <br>"version": 0
<br>}
    
   <h4>GET</h4>
	<pre><code>/api/v1/customers/{id} - Busca o Cliente por id</code></pre>
	<p><strong>Exemplo de Response Body (200)</strong></p>
{
	<br>"id": 1,
    <br>"nome": "Renan Meireles  ",
    <br>"numeroConta": "010123400",
    <br>"saldoConta": 1100.75,
    <br>"version": 0
<br>}
    <h4>GET</h4>
	<pre><code>/api/v1/customers/account/{numeroConta} - Busca o Cliente pelo Numero de Conta cadastrado</code></pre>
	<p><strong>Exemplo de Response Body (200)</strong></p>
{
	<br>"id": 1,
    <br>"nome": "Renan Meireles  ",
    <br>"numeroConta": "010123400",
    <br>"saldoConta": 1100.75,
    <br>"version": 0
<br>}
  </li>
</ul>
  
<h2>Transfêrencias</h2>
<p>Endpoints com operações ref. Transfêrencias</p>

<ul>
  <li>
    <h4>POST</h4>
    <pre><code>/api/v1/transferences - Efetua transfêrencias entre Contas de 2 clientes cadastrados no sistema e atualiza saldo(valor máximo R$ 1000.00)</code></pre>
    <p><strong>Exemplo de Request Body</strong></p>

{
  <br>"contaOrigem": "010123400",
  <br>"contaDestino": "020123499",
  <br>"valorTransferencia": 759.90
<br>}

<p><strong>Exemplo de Response Body (200)</strong></p>
{
        <br>"id": 1,
        <br>"contaOrigem": "010123400",
        <br>"contaDestino": "020123499",
        <br>"valorTransferencia": 759.90,
        <br>"dataTransferencia": "2020-11-08T04:33:30.775+00:00",
        <br>"status": "SUCCESS",
        <br>"detalhes": "Transfêrencia efetuada com sucesso",
        <br>"version": 0
<br>}

  
<h4>GET</h4>
  	<pre><code>/api/v1/transferences/account/{contaOrigem} - Busca todas as transfêrencias feitas pelo Cliente informado</code></pre>
  	<p><strong>Exemplo de Response Body (200)</strong></p>
  	
[
    <br>&emsp;{
        <br>&emsp;"id": 3,
        <br>&emsp;"contaOrigem": "010123400",
        <br>&emsp;"contaDestino": "020123499",
        <br>&emsp;"valorTransferencia": 700.0,
        <br>&emsp;"dataTransferencia": "2020-11-08T04:34:13.241+00:00",
        <br>&emsp;"status": "FAILED",
        <br>&emsp;"detalhes": "Transfêrencia não efetuada - Motivo: Saldo Insuficiente",
        <br>&emsp;"version": 0
    <br>&emsp;},
    <br>&emsp;{
        <br>&emsp;"id": 2,
        <br>&emsp;"contaOrigem": "010123400",
        <br>&emsp;"contaDestino": "020123499",
        <br>&emsp;"valorTransferencia": 1110.0,
        <br>&emsp;"dataTransferencia": "2020-11-08T04:33:48.870+00:00",
        <br>&emsp;"status": "FAILED",
        <br>&emsp;"detalhes": "Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00",
        <br>&emsp;"version": 0
    <br>&emsp;},
    <br>&emsp;{
        <br>&emsp;"id": 1,
        <br>&emsp;"contaOrigem": "010123400",
        <br>&emsp;"contaDestino": "020123499",
        <br>&emsp;"valorTransferencia": 759.90,
        <br>&emsp;"dataTransferencia": "2020-11-08T04:33:30.775+00:00",
        <br>&emsp;"status": "SUCCESS",
        <br>&emsp;"detalhes": "Transfêrencia efetuada com sucesso",
        <br>&emsp;"version": 0
    <br>&emsp;}
<br>]
  </li>
</ul>
