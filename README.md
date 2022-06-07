
<h1 align="center">
    <img alt="Projeto Trilhas" src="./docs/readme/logo.png" width="400px" />
</h1>

<h3 align="center">
  Não sabe qual caminho de estudos trilhar? O Projeto Trilhas vai te ajudar!
</h3>

<p align="center">

  <a href="">
    <img alt="Made by Edmarcos, Emanunel, Giulian" src="https://img.shields.io/badge/made%20by-Edmarcos, Emanunel, Giulian-%2FBB86">
  </a>

  <a href="LICENSE" >
    <img alt="License" src="https://img.shields.io/badge/license-MIT-%2FBB86">
  </a>

</p>

<p align="center">
  <a href="#calendar-sobre">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; 
  <a href="#memo-licença">Licença</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; 
</p>



## :calendar: Sobre

Esse projeto foi desenvolvido pelos alunos do 3 AII do curso técnico em Informática para a Internet do [Instituto Federal de Educação, Ciência e Tecnologia  Baiano](https://ifbaiano.edu.br) para o Projeto de Conclusão de Curso


## :computer: Como executar o projeto

### Frotend:

✅ Para executar a aplicação seŕa necessário possuir o runtime Node.js Instalado em sua máquina.

Em Sistemas windows recomendamos realizar o processo por meio da ferramenta chocolatey. Confira a documentação oficial para a realização do processo:

https://chocolatey.org/install

Após a instalação, o node pode ser obtido em seu windows com o seguinte comando:

``` 
choco install nodejs.install
 ```

Em sistema Linux é possível baixa-lo por meio do gerenciador de pacotes da sua distribuição, poŕem estará desatualizado. O método recomendado é atraves da instalação do Nvm ( Node version manager), que será abordado a seguir.

Para instalar o nvm use um dos seguintes comandos abaixo:

``` 
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash 
```

``` 
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash 
```

Reinicie o terminal para as mudanças entrarem em vigor.

Execute um comando de teste:

``` 
nvm -v
 ```

Se receber o output com número de versão, tudo ocorreu corretamente. Em caso de algum problema visite o repositório oficial da ferramenta:

https://github.com/nvm-sh/nvm#installing-and-updating

Com o nvm instalado basta um comando para instalar o Node.js. ( a flag --lts baixa a ultima versão estável, o que recomendamos).

``` 
nvm install --lts 
```

Após o processo, reinicie o terminal e você deve conseguir um output de versão para o node ao digitar:

``` 
node -v
 ```

Se tudo ocorreu bem, você já possui o Node.js instalado e pronto para usar.

Com o Node instalado, é possivel usar seu gerenciador de dependencias, o npm, para instalar as dependencias do projeto.

Vá para a pasta frontend e digite no terminal:

``` 
npm install
 ```

Aguarde todas as dependencias serem instaladas. 

✅ Depois disso, resta apenas configurar as variaveis de ambiente utilizadas no projeto, crie um arquivo .env na pasta frontend e adicione as seguintes linhas de código:

```
REACT_APP_API_URL=http://localhost:8080
REACT_APP_LOCAL_API_URL=http://localhost:8080
```

Isso permitirá a conexão com a API do projeto, cujo setup está descrito abaixo.

Agora o projeto está pronto para rodar. Na pasta frontend digite:

``` 
npm start
 ```

✅ Pronto, o frontend está funcionando. Agora vamos aprender como configurar o backend para que ele tenha de onde consumir dados.




### Backend:

#### Requisitos mínimos para a execução:

✅ Você precisa ter o Java Development Kit (Versão 11 LTS) instalada em seu computador para conseguir compilar a aplicação.
Recomendamos o JDK da Azul, que você pode encontrar aqui: https://www.azul.com/downloads/?package=jdk

✅ Você precisa ter instalado e configurado em sua máquina um banco de dados Postgres para a correta inicialização do sistema.
 - Você tem duas formas de realizar tal instalação:<br>
1. Por meio da instalação do Postgres de forma normal em sua máquina, por meio do link (note que é recomendável que o usuário do postgres chame-se: 'postgres'): https://www.postgresql.org/download/ 
3. (Recomendada) Por meio da instalação do Container Postgres em seu Docker, utilizando o comando ```docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres``` 

✅ Para agilizar o processo de configuração e execução da aplicação, recomendamos o uso de uma IDEA. Nós utilizamos o Intellij e Spring Tools Suite para tal. Entretanto, esse tutorial somente abordará a segunda opção. Para execuar a IDEA, descompacte a pasta e abra o arquivo executável.
	
- Links para download da ferramenta: <br>
1. Spring Tools Suite (Windows): https://drive.google.com/file/d/17KTwxMWAMEgTNqNbhY76ETkMDyOUtnS4/view?usp=sharing
2. Spring Tools Suite (Linux): https://drive.google.com/file/d/1MFEC6fjdgsBX0qUyM8Da_uVDWyPVU9_r/view?usp=sharing

#### Como executar:
Com todas as dependências instaladas em sua máquina, clone este repositório e realize o seguinte passo a passo no Spring Tools Suite:
##### Importando o projeto no STS:
1. Vá no canto superior direito em file > import.
2. Selecione a opção Existing Maven Projects, e prossiga.
3. Clique em Browse e procure a pasta BACKEND do projeto.
4. Clique em Finish e espere o STS carregar o projeto e ler as pastas e arquivos.

##### Baixando as dependências com o Maven:
Com o projeto já importado, siga estes passos:
1. Clique com o botão direito encima do nome 'trailsProject'
2. Vá na opção 'Run As' e clique em '8 Maven install'
3. (Opcional) caso apareça algum aviso sobre o console, clique em Remind me later
4. Espere o Maven baixar as dependências. 
5. Após isso, ele tentará rodar o projeto, mas ocasionará um erro pois as variáveis de ambiente ainda não estão configuradas.

##### Configurando as variáveis de ambiente:
1. Vá novamente na opção  'Run As' e clique em 'Run as SpringBootApplication' para ele gerar o perfil de execução do projeto no STS.
2. Após o projeto tentar rodar e dar erro, vá em  'Run As' e na opção 'Run Configurations'
3. Em Spring Boot App, procure a opção traisProject - TrailsProject e vá na aba 'Environment'.
4. Aqui você deverá adicionar as seguintes variáveis de ambiente na opção 'Add' (Uma variável por vez).

  | Nome| Valor |
   |---|---|
   |DATABASEUSER| {Usário do banco de dados Postgres, caso não informado, será por padrão 'postgres'}
   | DATABASEPASS| {Senha do banco de dados postgres} |
   | EMAIl_AUTH_USERNAME |{Endereço de e-mail do g-mail para o envio de mensagens}
   |EMAIL_AUTH_PASSWORD | {Senha de app gerada no g-mail para o acesso do projeto ao e-mail}
5. Clique em 'Apply'.

##### Executando a aplicação:
Após a realização de todos os passos, a inicialização do projeto é bem simples.
1. Inicie o banco de dados Postgres para o projeto realizar a conexão.
2. Clique com o botão direito em 'trailsProject' no STS.
3. Vá na opção  'Run As' e clique em 'Run as SpringBootApplication' 
4. Observe no console. Assim que a mensagem 'Started TrailsProjectApplication in X.XXX seconds (JVM running for X.XXX)', aparecer, o projeto estará completamente inicializado e pronto para receber requisições.
Note que as requisições devem ser direcionadas para a porta 8080 da sua máquina.
## :memo: Licença

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](/LICENSE) para mais detalhes.

Please, if you are going to create another project with the same images and artwork, give credit to the authors: <br>
<a href="https://storyset.com/business">SVG illustrations by Storyset</a><br>
<a href="https://www.blobmaker.app/">Blobs SVG made by Blobmaker</a>


Feito com :heart: por  Edmarcos, Emanuel e Giulian<br>
