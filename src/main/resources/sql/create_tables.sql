create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create sequence users_seq;

alter sequence users_seq owner to postgres;

create sequence biometrics_seq;

alter sequence biometrics_seq owner to postgres;

create sequence foods_seq;

alter sequence foods_seq owner to postgres;

create sequence consumed_foods_seq;

alter sequence consumed_foods_seq owner to postgres;

create table if not exists users
(
    id bigint default nextval('users_seq'::regclass) not null
        constraint users_pkey
            primary key,
    email varchar(50) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    password varchar(50) not null,
    role varchar(50) not null,
    username varchar(30) not null
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique,
    first_name varchar(30),
    last_name varchar(30),
    daily_norm_calories numeric(7,2)
);

alter table users owner to postgres;

create table if not exists biometrics
(
    id bigint default nextval('biometrics_seq'::regclass) not null
        constraint biometrics_pkey
            primary key,
    age numeric(4,2) not null,
    height numeric(5,2) not null,
    lifestyle varchar(20) not null,
    sex varchar(10) not null,
    weight numeric(5,2) not null,
    user_id bigint
        constraint fkh08q65mp6khiyqir861hqod1r
            references users,
    constraint biometrics_id_user_id_key
        unique (id, user_id)
);

alter table biometrics owner to postgres;

create table if not exists days
(
    id bigserial not null
        constraint days_pkey
            primary key,
    date date not null,
    calories_consumed numeric(7,2) default 0,
    user_id bigint
        constraint fkabs3l4rw11qk2t7fj61rhliy
            references users,
    exceeded_calories numeric(7,2) default 0,
    is_daily_norm_exceeded boolean default false,
    constraint uklxyc94ywrtvnmx6kpymvtnxs0
        unique (date, user_id)
);

alter table days owner to postgres;

create table if not exists consumed_foods
(
    id bigint default nextval('consumed_foods_seq'::regclass) not null
        constraint consumed_foods_pkey
            primary key,
    amount numeric(7,2),
    name varchar(50) not null,
    time time not null,
    total_calories numeric(7,2),
    day_id bigint
        constraint fkr9etekoduct4xmo3udf4sadi3
            references days
            on delete cascade
);

alter table consumed_foods owner to postgres;

create table if not exists foods
(
    id bigint default nextval('foods_seq'::regclass) not null
        constraint food_pkey
            primary key,
    calories numeric(7,2) not null,
    name varchar(50),
    user_id bigint
        constraint fkonuv85jhrw21c6y3c9cg0ep6o
            references users,
    constraint uko2nkfcauy9in8r3f3ieqfavjq
        unique (name, user_id)
);

alter table foods owner to postgres;

