create table if not exists users
(
    id bigint default nextval('user_id_seq'::regclass) not null
        constraint users_pkey
            primary key,
    active boolean not null,
    email varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    password varchar(255) not null,
    role varchar(255) not null,
    username varchar(255) not null
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique,
    first_name varchar(100),
    last_name varchar(100),
    daily_norm_calories numeric(19,2)
);

alter table users owner to postgres;

create table if not exists biometrics
(
    id bigint default nextval('biometrics_id_seq'::regclass) not null
        constraint biometrics_pkey
            primary key,
    age numeric(19,2) not null,
    height numeric(19,2) not null,
    lifestyle varchar(255) not null,
    sex varchar(255) not null,
    weight numeric(19,2) not null,
    user_id bigint
        constraint fkh08q65mp6khiyqir861hqod1r
            references users
);

alter table biometrics owner to postgres;

create table if not exists days
(
    id bigserial not null
        constraint days_pkey
            primary key,
    date date not null,
    calories_consumed numeric(19,2) default 0,
    user_id bigint
        constraint fkabs3l4rw11qk2t7fj61rhliy
            references users,
    exceeded_calories numeric(19,2) default 0,
    is_daily_norm_exceeded boolean default false,
    constraint uklxyc94ywrtvnmx6kpymvtnxs0
        unique (date, user_id)
);

alter table days owner to postgres;

create table if not exists consumed_foods
(
    id bigint not null
        constraint consumed_foods_pkey
            primary key,
    amount numeric(19,2),
    name varchar(255) not null,
    time time not null,
    total_calories numeric(19,2),
    day_id bigint
        constraint fkr9etekoduct4xmo3udf4sadi3
            references days
            on delete cascade
);

alter table consumed_foods owner to postgres;

create table if not exists foods
(
    id bigint not null
        constraint food_pkey
            primary key,
    calories numeric(19,2) not null,
    name varchar(255),
    user_id bigint
        constraint fkonuv85jhrw21c6y3c9cg0ep6o
            references users,
    constraint uko2nkfcauy9in8r3f3ieqfavjq
        unique (name, user_id)
);

alter table foods owner to postgres;

