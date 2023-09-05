--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.3 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: dessert; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dessert (
    id integer NOT NULL,
    name character varying(255),
    price integer
);


ALTER TABLE public.dessert OWNER TO postgres;

--
-- Name: dessert_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dessert_order (
    order_id integer NOT NULL,
    dessert_id integer NOT NULL
);


ALTER TABLE public.dessert_order OWNER TO postgres;

--
-- Name: drink; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.drink (
    id integer NOT NULL,
    name character varying(255),
    price integer,
    size integer,
    coffee boolean
);


ALTER TABLE public.drink OWNER TO postgres;

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
-- Name: milk; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.milk (
    id integer NOT NULL,
    name character varying(255),
    price integer
);


ALTER TABLE public.milk OWNER TO postgres;

--
-- Name: new_order_create; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.new_order_create (
    id integer NOT NULL,
    update boolean NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE public.new_order_create OWNER TO postgres;

--
-- Name: ordering; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ordering (
    id integer NOT NULL,
    comment character varying(255),
    date_create timestamp without time zone,
    date_ready timestamp without time zone,
    price integer NOT NULL,
    status boolean NOT NULL,
    drink_id integer,
    milk_id integer,
    syrup_id integer
);


ALTER TABLE public.ordering OWNER TO postgres;

--
-- Name: seq_dessert_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_dessert_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_dessert_id OWNER TO postgres;

--
-- Name: seq_drink_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_drink_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_drink_id OWNER TO postgres;

--
-- Name: seq_milk_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_milk_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_milk_id OWNER TO postgres;

--
-- Name: seq_ordering_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_ordering_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_ordering_id OWNER TO postgres;

--
-- Name: seq_syrup_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_syrup_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_syrup_id OWNER TO postgres;

--
-- Name: syrup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.syrup (
    id integer NOT NULL,
    name character varying(255),
    price integer
);


ALTER TABLE public.syrup OWNER TO postgres;

--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Data for Name: dessert; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dessert (id, name, price) FROM stdin;
1	Twix	70
2	pie	300
\.


--
-- Data for Name: dessert_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dessert_order (order_id, dessert_id) FROM stdin;
108	1
108	2
109	1
109	2
111	2
116	1
\.


--
-- Data for Name: drink; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.drink (id, name, price, size, coffee) FROM stdin;
1	Латте	200	0	t
2	Капучино	200	0	t
3	Капучино	220	1	t
4	Латте	220	1	t
5	Американо	150	0	t
6	Флэт уайт	200	0	t
7	Раф	220	0	t
8	Раф	250	1	t
9	Чай латте	180	0	f
10	Чай латте	200	1	f
11	Какао	200	0	f
12	Какао	220	1	f
13	Зеленый чай	120	0	f
14	Зеленый чай	140	1	f
15	Черный чай	120	0	f
16	Черный чай	140	1	f
17	Облепиховый чай	120	0	f
18	Облепиховый чай	140	1	f
\.


--
-- Data for Name: milk; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.milk (id, name, price) FROM stdin;
1	безлактозное	40
2	соевое	40
3	миндальное	40
4	овсяное	40
5	кокосовое	40
6	банановое	40
7	классическое	0
\.


--
-- Data for Name: new_order_create; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.new_order_create (id, update, update_time) FROM stdin;
1	f	2023-06-23 11:24:28.614
\.


--
-- Data for Name: ordering; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ordering (id, comment, date_create, date_ready, price, status, drink_id, milk_id, syrup_id) FROM stdin;
107		2023-05-23 20:35:03.158	2023-05-23 20:35:16.54	0	t	\N	\N	\N
108		2023-05-23 20:35:24.917	2023-05-23 20:35:44.243	570	t	11	\N	\N
109		2023-05-23 20:35:37.32	2023-05-23 20:35:45.251	510	t	14	\N	\N
110		2023-05-23 20:42:11.931	2023-05-23 21:00:48.841	140	t	16	\N	\N
111		2023-05-23 21:02:50.945	2023-05-23 21:03:19.895	480	t	9	\N	\N
112		2023-05-23 22:24:30.253	2023-05-23 22:24:46.331	140	t	18	\N	\N
113		2023-05-23 22:24:42.578	2023-05-23 22:24:46.941	0	t	\N	\N	\N
114		2023-05-24 19:05:03.55	2023-05-24 19:05:11.656	180	t	9	\N	\N
115		2023-05-24 19:06:15.095	2023-05-24 19:06:24.172	200	t	2	\N	\N
116		2023-06-23 11:24:17.451	2023-06-23 11:24:28.538	270	t	11	\N	\N
\.


--
-- Data for Name: syrup; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.syrup (id, name, price) FROM stdin;
1	миндаль	40
2	кокос	40
9	шоколад	40
5	ваниль	40
4	лесной орех	40
7	лаванда	40
8	соленая карамель	40
11	клубника	40
3	банан	40
6	груша	40
10	мята	40
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 154, true);


--
-- Name: seq_dessert_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_dessert_id', 2, true);


--
-- Name: seq_drink_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_drink_id', 18, true);


--
-- Name: seq_milk_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_milk_id', 7, true);


--
-- Name: seq_ordering_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_ordering_id', 116, true);


--
-- Name: seq_syrup_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_syrup_id', 11, true);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: dessert dessert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dessert
    ADD CONSTRAINT dessert_pkey PRIMARY KEY (id);


--
-- Name: drink drink_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.drink
    ADD CONSTRAINT drink_pkey PRIMARY KEY (id);


--
-- Name: milk milk_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.milk
    ADD CONSTRAINT milk_pkey PRIMARY KEY (id);


--
-- Name: new_order_create new_order_create_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.new_order_create
    ADD CONSTRAINT new_order_create_pkey PRIMARY KEY (id);


--
-- Name: ordering ordering_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordering
    ADD CONSTRAINT ordering_pkey PRIMARY KEY (id);


--
-- Name: syrup syrup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.syrup
    ADD CONSTRAINT syrup_pkey PRIMARY KEY (id);


--
-- Name: ordering fk2u1oxj9kaf7v7tdcw9lrjjkpi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordering
    ADD CONSTRAINT fk2u1oxj9kaf7v7tdcw9lrjjkpi FOREIGN KEY (syrup_id) REFERENCES public.syrup(id);


--
-- Name: dessert_order fk3cus4btdrny1aomronm0glnwg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dessert_order
    ADD CONSTRAINT fk3cus4btdrny1aomronm0glnwg FOREIGN KEY (order_id) REFERENCES public.ordering(id);


--
-- Name: ordering fkgnevg2us3oriwjck30jigjwit; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordering
    ADD CONSTRAINT fkgnevg2us3oriwjck30jigjwit FOREIGN KEY (drink_id) REFERENCES public.drink(id);


--
-- Name: dessert_order fkje2oa9orunlpe3d3xb17p4wwh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dessert_order
    ADD CONSTRAINT fkje2oa9orunlpe3d3xb17p4wwh FOREIGN KEY (dessert_id) REFERENCES public.dessert(id);


--
-- Name: ordering fkqm5r1lindvialrqi8k8vwhd8w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ordering
    ADD CONSTRAINT fkqm5r1lindvialrqi8k8vwhd8w FOREIGN KEY (milk_id) REFERENCES public.milk(id);


--
-- PostgreSQL database dump complete
--

