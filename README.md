# Techmentor

O TechMentor tem como objetivo facilitar a interação dos universitários com a monitoria. O projeto apresenta várias funções que facilitam a vida universitária do usuário, como por exemplo:<br>
.Calendário: Nele podemos colocar datas de aulões, provas, eventos, palestra, etc.<br>
.Monitores: O usuário pode consultar todos os monitores que são separados entre online e offline.<br>
.Repositórios: O usuário tem acesso a uma página com varios repositórios importantes ou úteis para o dia a dia.<br>
.Atendimento: Para os monitores, o projeto apresenta uma página de formulário onde o monitor pode preencher o atendimento que ele fez.<br>
.Gestão: O projeto tambem apresenta funcionalidades de gestão, como contas Coordenadores que podem adicionar e retirar monitores.<br>

## Alunos integrantes da equipe

* Lucas Fernandes Marinho
* Felipe Portes Antunes
* Mateus Fasciani Viggiani Rios de Castro
* Letícia Duarte Gomes

## Professores responsáveis

* Rommel Vieira Carneiro
* Luciana Mara Freitas Diniz

## Instruções de utilização

### Dependências:
1. **Java Development Kit (JDK)**:
   - **Versão:** JDK 21 ou superior.
   - **Instalação no Windows:**
     - [Download e instalação do JDK](https://adoptium.net/)
     - Certifique-se de adicionar o `JAVA_HOME` nas variáveis de ambiente e que o `java` e `javac` estejam acessíveis no terminal.
   - **Instalação no Linux:**
     - Para distribuições baseadas em Debian/Ubuntu:
       ```bash
       sudo apt update
       sudo apt install openjdk-11-jdk
       java -version
       ```
     - Para distribuições baseadas em Fedora:
       ```bash
       sudo dnf update
       sudo dnf install java-11-openjdk-devel
       java -version
       ```
2. **Apache Maven**:
   - **Versão:** 3.6.0 ou superior.
   - **Instalação no Windows:**
     - [Download e instalação do Maven](https://maven.apache.org/download.cgi)
     - Configure o `MAVEN_HOME` nas variáveis de ambiente.
     - Verifique se a instalação foi bem-sucedida rodando:
       ```bash
       mvn -v
       ```
    - **Instalação no Linux:**
      - Para distribuições baseadas em Debian/Ubuntu:
         ```bash
         sudo apt update
         sudo apt install maven
         mvn -v
         ```
3. **PostgreSQL**:
   - **Versão:** 42.7.4.
   - **Instalação no Windows:**
     - [Download do PostgreSQL](https://www.postgresql.org/download/)
     - Durante a instalação, configure o nome do usuário, senha e porta do banco de dados.
   - **Instalação no Linux:**
     - Para distribuições baseadas em Debian/Ubuntu:
       ```bash
       sudo apt update
       sudo apt install postgresql postgresql-contrib
       sudo systemctl start postgresql
       ```
     - Para distribuições baseadas em Fedora:
       ```bash
       sudo dnf update
       sudo dnf install postgresql-server postgresql-contrib
       sudo systemctl start postgresql
       ```

4. **Git**:
   - **Instalação no Windows:**
     - [Download do Git](https://git-scm.com/)
     - Verifique a instalação com:
       ```bash
       git --version
       ```
   - **Instalação no Linux:**
     - Para distribuições baseadas em Debian/Ubuntu:
       ```bash
       sudo apt update
       sudo apt install git
       git --version
       ```
     - Para distribuições baseadas em Fedora:
       ```bash
       sudo dnf update
       sudo dnf install git
       git --version
       ```
### Execução:
1. **Clone o repositório**:
   ```bash
   git clone https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2024-2-g20-techmentor.git
   ```
2. **Entre no diretório do projeto na pasta Codigo**:
   ```bash
   cd seu_diretorio/plmg-cc-ti2-2024-2-g20-techmentor/Codigo
   ```
3. **Compile e execute o projeto(há um arquivo executavel pronto para isso)**:
   ```bash
   ./exec
   ```   
