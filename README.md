# Sistema de Reservas de Hotel

Este projeto é um sistema para gerenciar reservas de quartos de hotel, desenvolvido como parte da disciplina de **Padrões de Projeto** do Instituto Federal da Paraíba. O sistema permite que os usuários busquem por disponibilidade, reservem quartos, cancelem reservas e visualizem detalhes de suas reservas. Os administradores podem gerenciar quartos e clientes, atualizando perfis, removendo clientes, e visualizando detalhes das reservas.

## Funcionalidades

### Usuários
- **Buscar Disponibilidade**: Consulte a disponibilidade de quartos no período desejado.
- **Reservar Quartos**: Efetue reservas em quartos disponíveis.
- **Cancelar Reservas**: Cancele uma reserva existente.
- **Visualizar Detalhes**: Veja detalhes de uma reserva existente.

### Administradores
- **Adicionar/Remover Quartos**: Gerencie a lista de quartos disponíveis no hotel.
- **Atualizar Perfil de Cliente**: Atualize informações de clientes cadastrados.
- **Remover Cliente**: Exclua um cliente do sistema.
- **Ver Detalhes de um Cliente**: Acesse as informações detalhadas de um cliente.

## Arquitetura e Padrões de Projeto

Este sistema foi projetado utilizando vários padrões de projeto para garantir uma solução escalável, flexível e de fácil manutenção:

- **Singleton**: Gerencia a instância única do gerenciador de reservas, garantindo que todos os componentes do sistema utilizem a mesma instância.
- **Abstract Factory**: Facilita a criação de diferentes tipos de quartos (simples, luxo, suítes), permitindo a expansão futura do sistema para outros tipos de acomodação.
- **Builder**: Utilizado para construir objetos de reserva complexos que podem incluir várias opções, como data de entrada, saída.
- **Prototype**: Possibilita a réplica de objetos complexos para abstrair a criação dos mesmos.
- **Observer**: Permite a notificação de usuários sobre novos quartos adicionados.
- **Strategy**: Implementa diferentes estratégias de cálculo de preço, como tarifas sazonais e descontos para clientes frequentes, permitindo uma fácil alteração e adição de novas estratégias.

## Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada no desenvolvimento do sistema.
- **MySQL**: Banco de dados relacional para armazenamento das informações de reservas, quartos e usuários.
- **Maven**: Ferramenta de automação de build e gerenciamento de dependências.
- **JDBC**: API para conexão com o banco de dados MySQL.
- **ModelMapper**: Utilizada para automatizar o processo de mapeamento de objetos.

## Estrutura de Pastas

- `src/main/java`: Código-fonte principal do projeto.

## Configuração do Banco de Dados

Para o correto funcionamento do projeto, o banco de dados deve ser configurado conforme descrito abaixo. Certifique-se de que as tabelas necessárias estejam criadas antes de executar o sistema.

### Estrutura das Tabelas

#### Tabela `usuarios`

```sql
CREATE TABLE `usuarios` (
  `cpf` varchar(50) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `telefone` varchar(50) DEFAULT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cpf`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `email` (`email`)
);
```
#### Tabela `quartos`

```sql
CREATE TABLE `quartos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(100) NOT NULL,
  `preco_diaria` decimal(10,2) NOT NULL,
  `numero` int NOT NULL,
  `andar` int NOT NULL,
  `capacidade` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

#### Tabela `reservas`
```sql
CREATE TABLE `reservas` (
  `id` int NOT NULL,
  `id_cliente` varchar(50) NOT NULL,
  `id_quarto` int NOT NULL,
  `data_checkin` date NOT NULL,
  `data_checkout` date NOT NULL,
  `preco_total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente` (`id_cliente`),
  KEY `reservas_ibfk_2` (`id_quarto`),
  CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `usuarios` (`cpf`),
  CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`id_quarto`) REFERENCES `quartos` (`id`)
);
```

### Configurar Variáveis de Ambiente

Para que o seu projeto funcione corretamente, é necessário configurar algumas variáveis de ambiente. Essas variáveis são utilizadas para conectar ao banco de dados e para o envio de e-mails. Siga as instruções abaixo para configurar as variáveis de ambiente no seu sistema:

#### 1. Configuração para o Banco de Dados e Envio de E-mail

Você precisa definir as seguintes variáveis de ambiente para acessar o banco de dados e enviar e-mails:

- `USUARIO` - Nome de usuário para acessar o banco de dados.
- `SENHA` - Senha do banco de dados.
- `BD` - Nome do banco de dados.
- `EMAIL` - Seu e-mail usado para enviar as mensagens.
- `SENHAEMAIL` - Senha do seu e-mail ou uma senha específica para aplicativos, se aplicável.

**Para definir essas variáveis em um sistema Unix-like (Linux/MacOS):**

Abra o terminal e execute os seguintes comandos:

```sh
export USUARIO="seu_usuario_bd"
export SENHA="sua_senha_bd"
export BD="nome_do_banco_de_dados"
export EMAIL="seu_email"
export SENHAEMAIL="senha_email"
```

**Para definir essas variáveis no Windows:**

Abra o terminal e execute os seguintes comandos:

```sh
set USUARIO=seu_usuario_bd
set SENHA=sua_senha_bd
set BD=nome_do_banco_de_dados
set EMAIL=seu_email
set SENHAEMAIL=senha_email
```

Com essas configurações o projeto fica pronto para ser executado.

## 👥 Contribuidores
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/estertrvs" title="GitHub">
        <img src="https://avatars.githubusercontent.com/u/141650957?v=4" width="100px;" alt="Foto de Ester"/><br>
        <sub>
          <b>Ester Trevisan</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/analiciafsoares" title="GitHub">
        <img src="https://avatars.githubusercontent.com/u/144076062?v=4" width="100px;" alt="Foto de Ana"/><br>
        <sub>
          <b>Ana Licia Soares</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Joaopaulomedeirosdesouza" title="GitHub">
        <img src="https://avatars.githubusercontent.com/u/148402008?v=4" width="100px;" alt="Foto de João Paulo"/><br>
        <sub>
          <b>João Paulo Medeiros</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/TATA255" title="GitHub">
        <img src="https://avatars.githubusercontent.com/u/119708989?v=4" width="100px;" alt="Foto de Otávio"/><br>
        <sub>
          <b>Otávio Estendio</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/mateuszzinn" title="GitHub">
        <img src="https://avatars.githubusercontent.com/u/103861262?v=4" width="100px;" alt="Foto de Mateus"/><br>
        <sub>
          <b>Mateus Lima</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

---

**Instituto Federal da Paraíba** - Disciplina de **Padrões de Projeto**.
