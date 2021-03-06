CREATE DATABASE IF NOT EXISTS the_voting_game;
USE the_voting_game;

CREATE TABLE IF NOT EXISTS players (
	e_mail VARCHAR(40) NOT NULL,
	f_name VARCHAR(20) NOT NULL,
	l_name VARCHAR(20) NOT NULL,
	u_name VARCHAR(20) NOT NULL,
	g_won INT DEFAULT 0,
	PRIMARY KEY (u_name)
);

CREATE TABLE IF NOT EXISTS cards (
	card_no INT NOT NULL AUTO_INCREMENT,
	question BLOB NOT NULL,
	creator VARCHAR(20) NOT NULL DEFAULT 'admin',
	PRIMARY KEY (card_no)
);

CREATE TABLE IF NOT EXISTS gameids (
	g_id INT NOT NULL AUTO_INCREMENT,
	is_started BOOLEAN NOT NULL DEFAULT=0
	PRIMARY KEY (g_id)
);

CREATE TABLE IF NOT EXISTS games (
    g_id INT NOT NULL,
    g_creator VARCHAR(20) NOT NULL,
    p_u_name VARCHAR(20) NOT NULL,
    is_started BOOLEAN NOT NULL DEFAULT 0,
    p_joined INT NOT NULL DEFAULT 0,
    p_score INT NOT NULL DEFAULT 0,
    g_round INT NOT NULL DEFAULT 0,
    g_winner VARCHAR(20) NOT NULL DEFAULT "none",
    PRIMARY KEY (g_id, g_creator, p_u_name)
);

CREATE TABLE IF NOT EXISTS gameplay (
    g_id INT NOT NULL,
    g_creator VARCHAR(20) NOT NULL,
    p_u_name VARCHAR(20) NOT NULL,
    p_score INT NOT NULL DEFAULT 0,
    g_round INT NOT NULL DEFAULT 0,
    g_winner VARCHAR(20) NOT NULL DEFAULT "none",
    PRIMARY KEY (g_id, g_creator, p_u_name)
);