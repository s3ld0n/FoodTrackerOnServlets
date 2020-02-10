--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.11
-- Dumped by pg_dump version 9.6.11

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: biometrics; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.biometrics (
    id bigint NOT NULL,
    age numeric(19,2) NOT NULL,
    height numeric(19,2) NOT NULL,
    lifestyle character varying(255) NOT NULL,
    sex character varying(255) NOT NULL,
    weight numeric(19,2) NOT NULL,
    user_id bigint
);


ALTER TABLE public.biometrics OWNER TO postgres;

--
-- Name: consumed_foods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consumed_foods (
    id bigint NOT NULL,
    amount numeric(19,2),
    name character varying(255) NOT NULL,
    "time" time without time zone NOT NULL,
    total_calories numeric(19,2),
    day_id bigint
);


ALTER TABLE public.consumed_foods OWNER TO postgres;

--
-- Name: days; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.days (
    id bigint NOT NULL,
    date date NOT NULL,
    total_calories numeric(19,2),
    user_id bigint
);


ALTER TABLE public.days OWNER TO postgres;

--
-- Name: days_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.days_id_seq
    START WITH 81
    INCREMENT BY 1
    MINVALUE 81
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.days_id_seq OWNER TO postgres;

--
-- Name: days_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.days_id_seq OWNED BY public.days.id;


--
-- Name: foods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.foods (
    id bigint NOT NULL,
    calories numeric(19,2) NOT NULL,
    name character varying(255),
    user_id bigint
);


ALTER TABLE public.foods OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    active boolean NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    first_name character varying(100),
    last_name character varying(100),
    daily_norm_calories numeric(19,2)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: days id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.days ALTER COLUMN id SET DEFAULT nextval('public.days_id_seq'::regclass);


--
-- Data for Name: biometrics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.biometrics (id, age, height, lifestyle, sex, weight, user_id) FROM stdin;
1	45.00	221.00	SEDENTARY	MALE	123.00	2
3	44.00	123.00	SEDENTARY	MALE	123.00	\N
5	44.00	123.00	SEDENTARY	MALE	123.00	\N
10	22.00	123.00	VIGOROUS	MALE	50.00	11
\.


--
-- Data for Name: consumed_foods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.consumed_foods (id, amount, name, "time", total_calories, day_id) FROM stdin;
71	1.00	soup	17:11:55	120.00	68
73	1.00	asd	17:12:43	1000.00	68
74	1.00	asd	17:12:44	1000.00	68
75	1.00	asd	17:12:44	1000.00	68
76	1.00	asd	17:12:45	1000.00	68
77	1.00	asd	17:12:45	1000.00	68
78	1.00	asd	17:12:46	1000.00	68
79	1.00	asd	17:12:46	1000.00	68
81	10.00	asd	17:13:39	10000.00	80
83	1.00	asd	18:51:26	1000.00	81
84	1.00	soup	18:51:28	120.00	81
86	1.00	Pork	18:51:33	12.00	81
\.


--
-- Data for Name: days; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.days (id, date, total_calories, user_id) FROM stdin;
7	2020-01-28	0.00	2
30	2020-01-26	3123.00	11
25	2020-01-25	2246.00	11
68	2020-01-27	7120.00	11
80	2020-01-28	10000.00	11
81	2020-02-03	1132.00	11
84	2020-02-05	0.00	11
85	2020-02-08	0.00	11
107	2020-02-09	0.00	11
118	2020-02-10	0.00	11
\.


--
-- Name: days_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.days_id_seq', 128, true);


--
-- Data for Name: foods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.foods (id, calories, name, user_id) FROM stdin;
69	120.00	soup	\N
70	120.00	soup	11
82	12.00	Pork	\N
72	1000.00	Well-Done Steak	11
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 86, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, active, email, password, role, username, first_name, last_name, daily_norm_calories) FROM stdin;
2	t	a@a.a	$2a$08$4DROFRW0XObzQoAmpKhoo.vqvXLRyrqL260FfMRhXjv/Z7CeSA9N6	ADMIN	a	a	a	2341.00
11	t	d	$2a$08$25kwkPbNI.Nblg2CZ3y0b.a93Rtn1YNxxnYo.P5tZpM83KctZQ.pK	USER	d	d	d	3241.00
\.


--
-- Name: biometrics biometrics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.biometrics
    ADD CONSTRAINT biometrics_pkey PRIMARY KEY (id);


--
-- Name: consumed_foods consumed_foods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consumed_foods
    ADD CONSTRAINT consumed_foods_pkey PRIMARY KEY (id);


--
-- Name: days days_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.days
    ADD CONSTRAINT days_pkey PRIMARY KEY (id);


--
-- Name: foods food_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods
    ADD CONSTRAINT food_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: days uklxyc94ywrtvnmx6kpymvtnxs0; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.days
    ADD CONSTRAINT uklxyc94ywrtvnmx6kpymvtnxs0 UNIQUE (date, user_id);


--
-- Name: foods uko2nkfcauy9in8r3f3ieqfavjq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods
    ADD CONSTRAINT uko2nkfcauy9in8r3f3ieqfavjq UNIQUE (name, user_id);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: days fkabs3l4rw11qk2t7fj61rhliy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.days
    ADD CONSTRAINT fkabs3l4rw11qk2t7fj61rhliy FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: biometrics fkh08q65mp6khiyqir861hqod1r; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.biometrics
    ADD CONSTRAINT fkh08q65mp6khiyqir861hqod1r FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: foods fkonuv85jhrw21c6y3c9cg0ep6o; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foods
    ADD CONSTRAINT fkonuv85jhrw21c6y3c9cg0ep6o FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: consumed_foods fkr9etekoduct4xmo3udf4sadi3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consumed_foods
    ADD CONSTRAINT fkr9etekoduct4xmo3udf4sadi3 FOREIGN KEY (day_id) REFERENCES public.days(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

