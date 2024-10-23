-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Tempo de geração: 18/06/2024 às 07:12
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `TrabalhoPOO`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `agenda`
--

CREATE TABLE `agenda` (
  `id_agenda` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `login_usuario` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `agenda`
--

INSERT INTO `agenda` (`id_agenda`, `nome`, `descricao`, `login_usuario`) VALUES
(1, 'Profissional', 'Agenda', 'Luiz'),
(1, 'Profissional', 'Para o trabalho', 'Milena'),
(1, 'Profissional', 'Agenda profissional', 'Rafael'),
(2, 'Aniversarios', 'Eu sempre esqueco dos aniversarios', 'Luiz'),
(2, 'Aniversarios', 'Eu sempre esqueco dos aniversarios', 'Milena'),
(2, 'Aniversarios', 'Agenda para relembrar aniversarios', 'Rafael'),
(3, 'Festas', 'Agenda para ir no bar da esquina', 'Luiz'),
(3, 'Festas', 'Descricao', 'Milena'),
(3, 'Festas', 'Agenda para festas', 'Rafael');

-- --------------------------------------------------------

--
-- Estrutura para tabela `compromisso`
--

CREATE TABLE `compromisso` (
  `id_compromisso` int(11) NOT NULL,
  `local` varchar(100) DEFAULT NULL,
  `titulo` varchar(30) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_termino` date NOT NULL,
  `horario_inicio` time NOT NULL,
  `horario_termino` time NOT NULL,
  `momento_notificacao` timestamp NULL DEFAULT NULL,
  `id_agenda` int(11) NOT NULL,
  `login_usuario` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `compromisso`
--

INSERT INTO `compromisso` (`id_compromisso`, `local`, `titulo`, `descricao`, `data_inicio`, `data_termino`, `horario_inicio`, `horario_termino`, `momento_notificacao`, `id_agenda`, `login_usuario`) VALUES
(1, '', 'Aniversario rafael', 'Esquecer', '2024-02-18', '2024-02-18', '00:00:00', '01:00:00', '2024-02-18 11:30:00', 2, 'Luiz'),
(1, 'UTFPR', 'Aniversario do luiz', 'Dar parabens', '2024-12-23', '2024-12-23', '00:00:00', '23:00:00', '2024-12-23 14:00:00', 2, 'Milena'),
(1, 'DISCORD', 'Aniversario Milena', 'Mandar virus no discord', '2024-11-01', '2024-11-01', '00:00:00', '23:00:00', '2024-11-01 18:00:00', 2, 'Rafael'),
(1, 'Bar no centro', 'Ir ao barzinho', 'Pegar o nossa senhora das graças e o santa monica', '2024-07-01', '2024-07-01', '16:00:00', '23:00:00', '2024-07-01 20:00:00', 3, 'Luiz'),
(1, 'na UEPG', 'PASSEI', 'PASSEI', '2024-07-01', '2024-07-01', '16:00:00', '23:00:00', '2024-07-01 21:00:00', 3, 'Milena'),
(1, 'Bar perto da UEPG', 'Festa pos banco de dados', 'festinha pra quem passou na disciplina', '2024-07-01', '2024-07-01', '16:00:00', '23:00:00', '2024-07-01 21:00:00', 3, 'Rafael'),
(4, 'Casa', 'Dar água pro gato', 'Não vai ter ninguem em casa e ela precisa de agua', '2024-12-23', '2024-12-23', '17:00:00', '18:00:00', '2024-07-18 22:00:00', 1, 'Rafael'),
(5, 'Praça', 'Andar', 'Ireii para utfpr', '2024-01-01', '2024-01-02', '16:00:00', '17:30:00', '2024-12-31 18:00:00', 1, 'Rafael');

-- --------------------------------------------------------

--
-- Estrutura para tabela `convite`
--

CREATE TABLE `convite` (
  `login_remetente` varchar(30) NOT NULL,
  `login_destinatario` varchar(30) NOT NULL,
  `id_agenda` int(11) NOT NULL,
  `id_compromisso` int(11) NOT NULL,
  `aceito` enum('ACEITO','RECUSADO','PENDENTE','') NOT NULL DEFAULT 'PENDENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `convite`
--

INSERT INTO `convite` (`login_remetente`, `login_destinatario`, `id_agenda`, `id_compromisso`, `aceito`) VALUES
('Luiz', 'Milena', 2, 1, 'PENDENTE'),
('Luiz', 'Milena', 3, 1, 'ACEITO'),
('Rafael', 'Luiz', 1, 5, 'ACEITO'),
('Rafael', 'Milena', 1, 4, 'RECUSADO'),
('Rafael', 'Milena', 1, 5, 'PENDENTE');

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `login` varchar(20) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `genero` varchar(20) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `nome_completo` varchar(100) DEFAULT NULL,
  `foto_pessoal` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`login`, `senha`, `email`, `genero`, `data_nascimento`, `nome_completo`, `foto_pessoal`) VALUES
('Luiz', '168', 'luiz@gmail', 'nao opinar', '2024-06-03', 'Luiz Felipe Hildebrant', 'D:fotos/usuario/usuario.png'),
('Milena', '184', 'milena@gmail.com', 'feminino', '2024-06-02', 'Milena Alves Andrade', 'C:usuarios/imagens/fotoAnime.png'),
('Rafael', '206', 'rafael@gmail', 'masculino', '2024-06-01', 'Rafael Boldt Rodrigues de Souza', 'C:usuarios/fotoPerfil.jpeg');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`id_agenda`,`login_usuario`) USING BTREE,
  ADD KEY `fk_login_usuario` (`login_usuario`);

--
-- Índices de tabela `compromisso`
--
ALTER TABLE `compromisso`
  ADD PRIMARY KEY (`id_compromisso`,`id_agenda`,`login_usuario`) USING BTREE,
  ADD KEY `fk_agenda` (`id_agenda`,`login_usuario`) USING BTREE;

--
-- Índices de tabela `convite`
--
ALTER TABLE `convite`
  ADD PRIMARY KEY (`login_remetente`,`login_destinatario`,`id_agenda`,`id_compromisso`),
  ADD KEY `fk_id_compromisso` (`id_compromisso`,`id_agenda`,`login_remetente`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`login`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `agenda`
--
ALTER TABLE `agenda`
  MODIFY `id_agenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de tabela `compromisso`
--
ALTER TABLE `compromisso`
  MODIFY `id_compromisso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `agenda`
--
ALTER TABLE `agenda`
  ADD CONSTRAINT `fk_login_usuario` FOREIGN KEY (`login_usuario`) REFERENCES `usuario` (`login`);

--
-- Restrições para tabelas `convite`
--
ALTER TABLE `convite`
  ADD CONSTRAINT `fk_id_compromisso` FOREIGN KEY (`id_compromisso`,`id_agenda`,`login_remetente`) REFERENCES `compromisso` (`id_compromisso`, `id_agenda`, `login_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
